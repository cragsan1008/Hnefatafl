package player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import board.Board;
import board.Movement;
import square.Square;
import square.SquareType;
import token.Token;
import token.TokenType;

/**
 * La clase AI representa a un jugador manejado por la Inteligencia Artificial.
 * Su propósito es contener los métodos para calcular el movimiento.
 * 
 * @author César
 * @version 2.0
 * @since 1.0
 * @see Player
 */
public class AI extends Player {

	private int time;
	private Queue<Movement> lastMoves;
	private static final int MAX_HISTORY = 10;

	/**
	 * Constructor de la clase AI
	 * 
	 * @param rol
	 * @param board
	 * @param time
	 */
	public AI(String rol, Board board, int time) {
		super(rol, board);
		this.time = time;
		this.lastMoves = new LinkedList<>();
	}

	/**
	 * Método heredado de padre, contiene todos los movimientos posibles del
	 * jugador, los cuales manda a otro método para obtener uno de estos
	 * 
	 * @return Movimiento elegido por la IA
	 * @see Player#confirmMovePiece()
	 */
	@Override
	public Movement confirmMovePiece() {
		this.board = board.returnSelf();
		Movement move;
		Optional<Token> token;
		List<Movement> moveOptions = new ArrayList<>();
		List<Movement> moves;
		Movement bestMove;
		this.BOARD = board.getBOARD();

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < BOARD.length; i++) {
			for (int j = 0; j < BOARD[i].length; j++) {
				token = BOARD[i][j].returnToken();
				if (token.isPresent()) {
					Token currentToken = token.get();
					if (currentToken.getType() == TokenType.King && rol.equals("Defender")
							|| currentToken.getType() == TokenType.Defender && rol.equals("Defender")
							|| currentToken.getType() == TokenType.Attacker && rol.equals("Attacker")) {

						move = new Movement(BOARD[i][j]);
						moves = move.movementListIA(BOARD, i, j);
						moveOptions.addAll(moves);
					}
				}
			}
		}

		if (rol.equals("Attacker")) {
			bestMove = chooseBestMoveAttack(moveOptions).get();
		} else {
			bestMove = chooseBestMoveDefend(moveOptions).get();
		}

//		// Evitar movimientos repetidos
//		if (lastMoves.contains(bestMove)) {
//			moveOptions.removeIf(lastMoves::contains);
//			if (rol.equals("Attacker")) {
//				bestMove = chooseBestMoveAttack(moveOptions).get();
//			} else {
//				bestMove = chooseBestMoveDefend(moveOptions).get();
//			}
//		}
//
//		// Actualizar el historial de movimientos
//		if (lastMoves.size() >= MAX_HISTORY) {
//			lastMoves.poll();
//		}
//		lastMoves.add(bestMove);

		return bestMove;
	}

	private Optional<Movement> chooseBestMoveAttack(List<Movement> moves) {
		return moves.stream().max(Comparator.comparingInt(this::killingConditionKing).thenComparing(this::getCloseKing)
				.thenComparing(this::killingCondition).thenComparing(this::getClose).thenComparing(this::randomMove));
	}

	private Optional<Movement> chooseBestMoveDefend(List<Movement> moves) {
		return moves.stream().max(Comparator.comparingInt(this::escapeCondition).thenComparing(this::goBorder)
				.thenComparing(this::killingCondition).thenComparing(this::getClose).thenComparing(this::randomMove));
	}

	private int getClose(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;

		counter += countOpponentsAround(x, y, w, z);

		return counter;
	}

	private int getCloseKing(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;

		counter += countKingsAround(x, y, w, z);

		return counter;
	}

	private int countOpponentsAround(int x, int y, int w, int z) {
		int counter = 0;
		TokenType originalType = BOARD[w][z].returnToken().get().getType();

		if (x > 0 && isOpponent(x - 1, y, originalType))
			counter++;
		if (x < 10 && isOpponent(x + 1, y, originalType))
			counter++;
		if (y > 0 && isOpponent(x, y - 1, originalType))
			counter++;
		if (y < 10 && isOpponent(x, y + 1, originalType))
			counter++;

		return counter;
	}

	private int countKingsAround(int x, int y, int w, int z) {
		int counter = 0;

		if (x > 0 && isKing(x - 1, y, w, z))
			counter++;
		if (x < 10 && isKing(x + 1, y, w, z))
			counter++;
		if (y > 0 && isKing(x, y - 1, w, z))
			counter++;
		if (y < 10 && isKing(x, y + 1, w, z))
			counter++;

		return counter;
	}

	private boolean isOpponent(int x, int y, TokenType originalType) {
		return BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() != originalType
				&& BOARD[x][y].returnToken().get().getType() != TokenType.King;
	}

	private boolean isKing(int x, int y, int w, int z) {
		return BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() == TokenType.King
				&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker;
	}

	private int killingCondition(Movement move) {
		Square d = move.getSquareD();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int counter = 0;

		counter += checkKillingMove(x, y);

		return counter;
	}

	private int checkKillingMove(int x, int y) {
		int counter = 0;

		if (x >= 0 && y > 1 && isKillingMove(x, y - 1, x, y - 2))
			counter++;
		if (x <= 10 && y < 9 && isKillingMove(x, y + 1, x, y + 2))
			counter++;
		if (x > 1 && y >= 0 && isKillingMove(x - 1, y, x - 2, y))
			counter++;
		if (x < 9 && y <= 10 && isKillingMove(x + 1, y, x + 2, y))
			counter++;

		return counter;
	}

	private boolean isKillingMove(int x1, int y1, int x2, int y2) {
		return BOARD[x1][y1].returnToken().isPresent() && BOARD[x2][y2].returnToken().isPresent()
				&& BOARD[x1][y1].returnToken().get().getType() != BOARD[x2][y2].returnToken().get().getType();
	}

	private int escapeCondition(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();

		if (BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			if (BOARD[x][y].getType() == SquareType.Throne) {
				return 1;
			}
		}
		return 0;
	}

	private int goBorder(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();

		if (BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			if ((x == 0 || x == 10) && (y == 0 || y == 10)) {
				for (int i = 0; i < 11; i++) {
					for (int j = 0; i < 11; i++) {
						if ((x == i || y == i) && BOARD[i][j].returnToken().isPresent()) {
							return 0;
						}
					}
				}
			}
		}
		return 1;
	}

	private int killingConditionKing(Movement move) {
		Square d = move.getSquareD();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int counter = 0;

		if (x >= 0 && y > 1) {
			counter += checkKillKing(x, y - 1);
		}
		if (x <= 10 && y < 9) {
			counter += checkKillKing(x, y + 1);
		}
		if (y >= 0 && x > 1) {
			counter += checkKillKing(x - 1, y);
		}
		if (y <= 10 && x < 9) {
			counter += checkKillKing(x + 1, y);
		}

		return counter;
	}

	private int checkKillKing(int x, int y) {
		int counter = 0;
		if (BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() == TokenType.King) {
			counter = 1;
		}
		return counter;
	}

	private int randomMove(Movement move) {
		return (int) (Math.random() * 100);
	}

}