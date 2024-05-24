package player;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import square.Square;
import square.SquareType;
import token.Token;
import token.TokenType;

/**
 * La clase AI representa a un jugador manejado por la Inteligencia Articifical.
 * Su proposito es contener los metodos para calcular el movimiento.
 * 
 * @author César
 * @version 2.0
 * @since 1.0
 * @see Player
 */
public class AI extends Player {

	private int time;

	/**
	 * ^ Constructor de la clase AI
	 * 
	 * @param rol
	 * @param board
	 */
	public AI(String rol, Board board, int time) {
		super(rol, board);
		this.time = time;
	}

	/**
	 * Metodo heredado de padre, contiene todos los movimientos posibles del
	 * jugador, los cuales manda a otro metodo para obtener uno de estos
	 * 
	 * @return Movimiento elegido por la IA
	 * @see Player#confirmMovePiece()
	 */
	@Override
	public Movement confirmMovePiece() {
		this.board = board.returnSelf();
		Movement move;
		Optional<Token> token;
		SortedMap<Integer, Movement> moveOptions = new TreeMap<>();
		SortedMap<Integer, Movement> moves;
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
					if ((token.get().getType() == TokenType.King && rol.equals("Defender"))
							|| (token.get().getType() == TokenType.Defender && rol.equals("Defender"))
							|| (token.get().getType() == TokenType.Attacker && rol.equals("Attacker"))) {
						move = new Movement(BOARD[i][j]);
						moves = move.movementList(BOARD, i, j);
						moveOptions.putAll(moves);
					}
				}

			}
		}
		if (rol.equals("Attacker")) {
			bestMove = chooseBestMoveAttack(moveOptions);
		} else {
			bestMove = chooseBestMoveDefend(moveOptions);

		}
		return bestMove;
	}

	/**
	 * Metodo que opera sobre la lista de movimientos pasada eligiendo un movimiento
	 * a traves de una comparación o lanza una excepción
	 * 
	 * @param moves
	 * @return Movimiento elegido
	 */
	private Movement chooseBestMoveAttack(SortedMap<Integer, Movement> moves) {

		Movement move = moves.values().stream()
				.max(Comparator.comparingInt(this::killingConditionKing).thenComparing(this::getCloseKing)
						.thenComparing(this::killingCondition).thenComparing(this::getClose)
						.thenComparing(this::randomMoveLastResource))
				.orElseThrow(() -> new IllegalStateException("Sin movimientos validos"));

		return move;
	}

	private Movement chooseBestMoveDefend(SortedMap<Integer, Movement> moves) {

		Movement move = moves.values().stream()
				.max(Comparator.comparingInt(this::escapeCondition).thenComparing(this::goBorder)
						.thenComparing(this::killingCondition).thenComparing(this::getClose)
						.thenComparing(this::randomMoveLastResource))
				.orElseThrow(() -> new IllegalStateException("Sin movimientos validos"));

		return move;
	}

	/**
	 * Metodo que devuelve un Random
	 * 
	 * @param move
	 * @return Numero random
	 */
	private int getClose(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x, y, w, z, counter;
		counter = 0;
		w = o.getPosition().getX();
		z = o.getPosition().getY();
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (x != 0) {
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (BOARD[x - 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x - 1][y].returnToken().get().getType() != TokenType.King) {
						counter++;
					}
				}
			}
		}
		if (x != 10) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (BOARD[x + 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x + 1][y].returnToken().get().getType() != TokenType.King) {
						counter++;
					}
				}
			}
		}
		if (y != 0) {
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (BOARD[x][y - 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x][y - 1].returnToken().get().getType() != TokenType.King) {
						counter++;
					}
				}
			}
		}
		if (y != 10) {
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (BOARD[x][y + 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x][y + 1].returnToken().get().getType() != TokenType.King) {
						counter++;
					}
				}
			}
		}
		return counter;

	}

	private int getCloseKing(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x, y, w, z, counter;
		counter = 0;
		w = o.getPosition().getX();
		z = o.getPosition().getY();
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (x != 0) {
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (BOARD[x - 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x - 1][y].returnToken().get().getType() == TokenType.King
							&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker) {
						counter++;
					}
				}
			}
		}
		if (x != 10) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (BOARD[x + 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x + 1][y].returnToken().get().getType() == TokenType.King
							&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker) {
						counter++;
					}
				}
			}
		}
		if (y != 0) {
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (BOARD[x][y - 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x][y - 1].returnToken().get().getType() == TokenType.King
							&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker) {
						counter++;
					}
				}
			}
		}
		if (y != 10) {
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (BOARD[x][y + 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()) {
					if (BOARD[x][y + 1].returnToken().get().getType() == TokenType.King
							&& BOARD[w][z].returnToken().get().getType() == TokenType.Attacker) {
						counter++;
					}
				}
			}
		}
		return counter;

	}

	private int killingCondition(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x, y, w, z, counter;
		counter = 0;
		w = o.getPosition().getX();
		z = o.getPosition().getY();
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (y != 0) {
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (y - 2 != -1) {
					if (BOARD[x][y - 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()
							&& (BOARD[x][y - 2].returnToken().isPresent()
									|| BOARD[x][y - 2].getType() != SquareType.Normal)) {
						if (BOARD[x][y - 2].returnToken().isPresent()) {
							if (BOARD[x][y - 2].returnToken().get().getType() == BOARD[w][z].returnToken().get()
									.getType() || BOARD[x][y - 2].getType() != SquareType.Normal) {
								if (BOARD[x][y - 1].returnToken().get().getType() != TokenType.King) {

									counter++;

								}

							}
						} else if (BOARD[x][y - 2].getType() != SquareType.Normal
								&& BOARD[x][y - 1].returnToken().get().getType() != TokenType.King) {
							counter++;
						}
					}
				}
			}
		}
		if (y != 10) {
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (y + 2 != 11) {
					if (BOARD[x][y + 1].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()
							&& (BOARD[x][y + 2].returnToken().isPresent()
									|| BOARD[x][y + 2].getType() != SquareType.Normal)) {
						if (BOARD[x][y + 2].returnToken().isPresent()) {
							if (BOARD[x][y + 2].returnToken().get().getType() == BOARD[w][z].returnToken().get()
									.getType() || BOARD[x][y + 2].getType() != SquareType.Normal) {
								if (BOARD[x][y + 1].returnToken().get().getType() != TokenType.King) {
									counter++;

								}
							}
						} else if (BOARD[x][y + 2].getType() != SquareType.Normal
								&& BOARD[x][y + 1].returnToken().get().getType() != TokenType.King) {
							counter++;
						}
					}
				}
			}
		}
		if (x != 0) {
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (x - 2 != -1) {
					if (BOARD[x - 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()
							&& (BOARD[x - 2][y].returnToken().isPresent()
									|| BOARD[x - 2][y].getType() != SquareType.Normal)) {
						if (BOARD[x - 2][y].returnToken().isPresent()) {
							if (BOARD[x - 2][y].returnToken().get().getType() == BOARD[w][z].returnToken().get()
									.getType() || BOARD[x - 2][y].getType() != SquareType.Normal) {
								if (BOARD[x - 1][y].returnToken().get().getType() != TokenType.King) {
									counter++;

								}
							}
						} else if (BOARD[x - 2][y].getType() != SquareType.Normal
								&& BOARD[x - 1][y].returnToken().get().getType() != TokenType.King) {
							counter++;
						}

					}
				}
			}
		}

		if (x != 10) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (x + 2 != 11) {
					if (BOARD[x + 1][y].returnToken().get().getType() != BOARD[w][z].returnToken().get().getType()
							&& (BOARD[x + 2][y].returnToken().isPresent()
									|| BOARD[x + 2][y].getType() != SquareType.Normal)) {
						if (BOARD[x + 2][y].returnToken().isPresent()) {
							if (BOARD[x + 2][y].returnToken().get().getType() == BOARD[w][z].returnToken().get()
									.getType() || BOARD[x + 2][y].getType() != SquareType.Normal) {
								if (BOARD[x + 1][y].returnToken().get().getType() != TokenType.King) {
									counter++;

								}
							}
						} else if (BOARD[x + 2][y].getType() != SquareType.Normal
								&& BOARD[x + 1][y].returnToken().get().getType() != TokenType.King) {
							counter++;
						}
					}
				}
			}
		}
		return counter;
	}

	private int escapeCondition(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x, y, w, z, counter;
		counter = 0;
		w = o.getPosition().getX();
		z = o.getPosition().getY();
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (BOARD[x][y].getType() == SquareType.Corner && BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			counter++;
		}
		return counter;
	}

	private int goBorder(Movement move) {
		Square d = move.getSquareD();
		Square o = move.getSquareO();
		int x, y, w, z;
		w = o.getPosition().getX();
		z = o.getPosition().getY();
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (BOARD[w][z].returnToken().get().getType() == TokenType.King) {
			if (x == 10 || x == 0 || y == 10 || x == 0) {
				for (int i = 0; i < 11; i++) {
					if (x == i) {
						if (BOARD[x][y].returnToken().isPresent()) {
							return 0;
						}
					}
				}
				for (int j = 0; j < 11; j++) {
					if (y == j) {
						if (BOARD[x][y].returnToken().isPresent()) {
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
		int x, y, counter;
		counter = 0;
		x = d.getPosition().getX();
		y = d.getPosition().getY();
		if (x - 2 >= 0 && y + 2 <= 10 && x + 2 <= 10 && y - 2 >= 0) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (BOARD[x + 1][y].returnToken().get().getType() == TokenType.King) {
					counter = killKing(x + 1, y);
				}
			}
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (BOARD[x - 1][y].returnToken().get().getType() == TokenType.King) {
					counter = killKing(x - 1, y);
				}
			}
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (BOARD[x][y + 1].returnToken().get().getType() == TokenType.King) {
					counter = killKing(x, y + 1);
				}
			}
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (BOARD[x][y - 1].returnToken().get().getType() == TokenType.King) {
					counter = killKing(x, y - 1);
				}
			}
		}
		return counter;
	}

	private int killKing(int x, int y) {
		int counter;
		counter = 0;
		if ((BOARD[x - 1][y].returnToken().isPresent()
				&& BOARD[x - 1][y].returnToken().get().getType() == TokenType.Attacker)
				|| BOARD[x - 1][y].getType() != SquareType.Normal) {
			if ((BOARD[x + 1][y].returnToken().isPresent()
					&& BOARD[x + 1][y].returnToken().get().getType() == TokenType.Attacker)
					|| BOARD[x + 1][y].getType() != SquareType.Normal) {
				if ((BOARD[x][y - 1].returnToken().isPresent()
						&& BOARD[x][y - 1].returnToken().get().getType() == TokenType.Attacker)
						|| BOARD[x][y - 1].getType() != SquareType.Normal) {
					if ((BOARD[x][y + 1].returnToken().isPresent()
							&& BOARD[x][y + 1].returnToken().get().getType() == TokenType.Attacker)
							|| BOARD[x][y + 1].getType() != SquareType.Normal) {
						counter++;
					}
				}
			}
		}
		return counter;
	}

	private int randomMoveLastResource(Movement move) {

		return (int) (Math.random() * 100);
	}

}
