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

	/**
	 * Atributo que almacena el tiempo que la AI tarda en realizar otro movimiento
	 */
	private int time;

	/**
	 * Atributo que almacena lista de movimientos realizados, en desuso
	 */
	@SuppressWarnings("unused")
	private Queue<Movement> lastMoves;
	/**
	 * Atributo que almacena el maximod de movimientos de la lista de movimientos
	 * realizados, en desuso
	 */
	@SuppressWarnings("unused")
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

	/**
	 * Metodo que devuelve el mejor movimiento segun los criterios para el atacante
	 * 
	 * @param moves
	 * @return movimiento para atacante
	 * @see #confirmMovePiece()
	 */
	private Optional<Movement> chooseBestMoveAttack(List<Movement> moves) {
		return moves.stream().max(Comparator.comparingInt(this::getCloseKing).thenComparing(this::killingCondition)
				.thenComparing(this::getClose).thenComparing(this::randomMove));
	}

	/**
	 * Metodo que devuelve el mejor ataque segun los criterios para el defensor
	 * 
	 * @param moves
	 * @return movimiento para defensor
	 * @see #confirmMovePiece()
	 */
	private Optional<Movement> chooseBestMoveDefend(List<Movement> moves) {
		return moves.stream()
				.max(Comparator.comparingInt(this::escapeCondition).thenComparing(this::saveKing)
						.thenComparing(this::goBorder).thenComparing(this::killingCondition)
						.thenComparing(this::getClose).thenComparing(this::randomMove));
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento ayuda al rey a no ser rodeado por atacantes
	 * 
	 * @param move
	 * @return
	 */
	private int saveKing(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;
		int rivals = 0;
		TokenType originalType = BOARD[w][z].returnToken().get().getType();

		if (originalType == TokenType.King) {
			if (w > 0 && isAttacker(w - 1, z)) {
				rivals++;
			}
			if (w < 10 && isAttacker(w + 1, z)) {
				rivals++;
			}
			if (z > 0 && isAttacker(w, z - 1)) {
				rivals++;
			}
			if (z < 10 && isAttacker(w, z + 1)) {
				rivals++;
			}
		}

		if (rivals > 0) {
			if (x > 0 && !isAttacker(x - 1, y)) {
				counter++;
			}
			if (x < 10 && !isAttacker(x + 1, y)) {
				counter++;
			}
			if (y > 0 && !isAttacker(x, y - 1)) {
				counter++;
			}
			if (y < 10 && !isAttacker(x, y + 1)) {
				counter++;
			}
		}

		return counter;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento tiene algun oponente al lado
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveAttack(List)
	 * @see #chooseBestMoveDefend(List)
	 */
	private int getClose(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;
		TokenType originalType = BOARD[w][z].returnToken().get().getType();

		if (x > 0 && isOpponent(x - 1, y, originalType)) {
			counter++;
		}
		if (x < 10 && isOpponent(x + 1, y, originalType)) {
			counter++;

		}
		if (y > 0 && isOpponent(x, y - 1, originalType)) {
			counter++;
		}
		if (y < 10 && isOpponent(x, y + 1, originalType)) {
			counter++;
		}
		return counter;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento tiene algun rey al lado
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveAttack(List)
	 */
	private int getCloseKing(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;

		if (x > 0 && isKing(x - 1, y, w, z)) {
			counter++;
		}
		if (x < 10 && isKing(x + 1, y, w, z)) {
			counter++;
		}
		if (y > 0 && isKing(x, y - 1, w, z)) {
			counter++;
		}
		if (y < 10 && isKing(x, y + 1, w, z)) {
			counter++;
		}

		return counter;
	}

	/**
	 * Metodo que devuelve un booleano de un movimiento dependiendo de si el
	 * movimiento tiene un rival a algun lado
	 * 
	 * @param x
	 * @param y
	 * @param originalType
	 * @return booleano
	 * @see #getClose(Movement)
	 */
	private boolean isOpponent(int x, int y, TokenType originalType) {
		return BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() != originalType
				&& BOARD[x][y].returnToken().get().getType() != TokenType.King;
	}

	/**
	 * Metodo que devuelve un booleano de un movimiento dependiendo de si el
	 * movimiento tiene un atacante
	 * 
	 * @param x
	 * @param y
	 * @return booleano
	 * @see #saveKing(Movement)
	 */
	private boolean isAttacker(int x, int y) {
		return BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() == TokenType.Attacker;
	}

	/**
	 * 
	 * Metodo que devuelve un booleano de un movimiento dependiendo de si el
	 * movimiento tiene un rey a algun lado
	 *
	 * @param x
	 * @param y
	 * @param w
	 * @param z
	 * @return booleano
	 * @see #getCloseKing(Movement)
	 */
	private boolean isKing(int x, int y, int w, int z) {
		return BOARD[x][y].returnToken().isPresent() && BOARD[x][y].returnToken().get().getType() == TokenType.King
				&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento mata
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveAttack(List)
	 * @see #chooseBestMoveDefend(List)
	 */
	private int killingCondition(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;
		TokenType originalType = BOARD[w][z].returnToken().get().getType();

		if (x >= 0 && y > 1 && isKillingMove(originalType, x, y - 1, x, y - 2))
			counter++;
		if (x <= 10 && y < 9 && isKillingMove(originalType, x, y + 1, x, y + 2))
			counter++;
		if (x > 1 && y >= 0 && isKillingMove(originalType, x - 1, y, x - 2, y))
			counter++;
		if (x < 9 && y <= 10 && isKillingMove(originalType, x + 1, y, x + 2, y))
			counter++;

		return counter;
	}

	/**
	 * Metodo que devuelve un booleano de un movimiento dependiendo de si el
	 * movimiento mata con el movimiento
	 * 
	 * @param originalType
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return booleano
	 * @see #killingCondition(Movement)
	 */
	private boolean isKillingMove(TokenType originalType, int x1, int y1, int x2, int y2) {
		return BOARD[x1][y1].returnToken().isPresent() && BOARD[x2][y2].returnToken().isPresent()
				&& BOARD[x1][y1].returnToken().get().getType() != BOARD[x2][y2].returnToken().get().getType()
				&& BOARD[x2][y2].returnToken().get().getType() == originalType;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento permite al rey llegar a una esquina
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveDefend(List)
	 */
	private int escapeCondition(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();

		if (BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			if (BOARD[x][y].getType() == SquareType.Corner) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento dependiendo de si el
	 * movimiento mueve el rey a un lado sin fichas
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveDefend(List)
	 */
	private int goBorder(Movement move) {
		boolean stay;
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x = d.getPosition().getX();
		int y = d.getPosition().getY();
		int w = o.getPosition().getX();
		int z = o.getPosition().getY();
		int counter = 0;

		if (BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			if (y == 0 || y == 10) {
				stay = true;
				for (int i = x - 1; i >= 0 && stay; i--) {
					if (BOARD[i][y].returnToken().isEmpty()) {
						if (i == 0) {
							counter++;
						}
					} else {
						stay = false;
					}
				}
				stay = true;
				for (int i = x + 1; i <= 10 && stay; i++) {
					if (BOARD[i][y].returnToken().isEmpty()) {
						if (i == 10) {
							counter++;
						}
					} else {
						stay = false;
					}
				}
			}
			if (x == 0 || x == 10) {
				stay = true;
				for (int i = y - 1; i >= 0 && stay; i--) {
					if (BOARD[x][i].returnToken().isEmpty()) {
						if (i == 0) {
							counter++;
						}
					} else {
						stay = false;
					}
				}
				stay = true;
				for (int i = y + 1; i <= 10 && stay; i++) {
					if (BOARD[x][i].returnToken().isEmpty()) {
						if (i == 10) {
							counter++;
						}
					} else {
						stay = false;
					}
				}
			}
		}
		return counter;
	}

	/**
	 * Metodo que devuelve un numero que califica un movimiento aleatoriamente
	 * 
	 * @param move
	 * @return numero calificatorio de movimiento
	 * @see #chooseBestMoveAttack(List)
	 * @see #chooseBestMoveDefend(List)
	 * 
	 */
	private int randomMove(Movement move) {
		return (int) (Math.random() * 100);
	}

}