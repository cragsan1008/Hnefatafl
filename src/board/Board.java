package board;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import console.ConsoleInput;
import player.Player;
import square.Position;
import square.Square;
import square.SquareType;
import token.Token;
import token.TokenType;

/**
 * La clase Board representa un tablero. Su proposito es contruir y almacenar el
 * tablero, permitiendonos recojer la informacion sobre las casillas en un mismo
 * objeto. Proporciona metodos para la creacion del tablero y pintado de este.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Square
 */

public class Board {

	/**
	 * Array de casillas que conforman el tablero.
	 */
	private final Square[][] BOARD = new Square[11][11];
	/**
	 * Constante que almacena el valor para representar el texto en negrita.
	 */
	private static final String BOLD = "\u001B[1m";

	private static final String RESET = "\u001b[0m";

	private static final String LAST = "\u001B[43m";

	private static final String POSIBILITY = "\u001B[44m";
	private static final String POSIBILITY2 = "\u001B[34m";

	/**
	 * Constructor de la clase Board, no necesita parametros.
	 */
	public Board() {
		for (int i = 0; i < BOARD.length; i++) {
			for (int j = 0; j < BOARD[i].length; j++) {
				this.BOARD[i][j] = new Square(i, j);
			}
		}
	}

	/**
	 * Metodo que sirve para dibujar el tablero con todos sus componentes como las
	 * fichas.
	 * 
	 * @see Square
	 */
	public void draw() {
		for (int i = 0; i < BOARD.length; i++) {

			if (i == 0) {
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}

			for (int j = -1; j < BOARD[i].length; j++) {
				if (j != -1) {
					System.out.printf("%s", BOARD[i][j].draw());
				} else if (j == -1) {
					if (i == 10) {
						System.out.printf("%s%-3s", BOLD, i);
					} else {
						System.out.printf("%s%-3s", BOLD, i);
					}
				}
				if (j == 10) {
					if (i == 10) {
						System.out.printf("%s%3s", BOLD, i);
					} else {
						System.out.printf("%s%3s", BOLD, i);
					}
				}

			}
			if (i == 10) {
				System.out.println("");
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}
			System.out.println("\n");
		}
	}

	public void move(Player player) {
		int x, y;
		Optional<Token> opTok;
		Position position;
		Movement movement;
		SortedMap<Integer, Movement> moveList;
		moveList = new TreeMap<>();

		System.out.println("Elige posicion de ficha a mover(primero x, luego y):");
		position = player.confirmMovePiece();
		x = position.getX();
		y = position.getY();
		opTok = BOARD[x][y].returnToken();
		if (opTok.isEmpty()) {
			System.out.println("Casilla vacia");
			move(player);
		} else {
			if (opTok.get().getType().getRol() != player.getRol()) {
				System.out.println("Ficha del rival");
				move(player);
			} else {
				movement = new Movement(BOARD[x][y]);
				moveList = movement.movementList(BOARD, x, y);

				if (!moveList.isEmpty()) {
					moveFinisher(player, opTok, x, y, moveList);
				} else {
					System.out.println("Ficha sin movimientos validos");
					move(player);
				}
			}
		}
	}

	private void moveFinisher(Player player, Optional<Token> opTok, int x, int y,
			SortedMap<Integer, Movement> moveList) {
		boolean valid;
		int x2, y2;
		Position position;
		drawPosibleMove(moveList);
		System.out.println("Elige posicion a mover(primero x, luego y):");
		position = player.confirmMovePiece();
		x2 = position.getX();
		y2 = position.getY();
		valid = moveConfirm(x, y, x2, y2, moveList);
		if (valid) {
			BOARD[x2][y2].setToken(opTok.get());
			BOARD[x][y].returnToken().get().setSquare(BOARD[x2][y2]);
			BOARD[x][y].setToken(null);
			checkMoveKill(x2, y2);
			drawLast(x, y, x2, y2, moveList);
		} else {
			moveFinisher(player, opTok, x, y, moveList);
		}

	}

	private boolean moveConfirm(int x, int y, int x2, int y2, SortedMap<Integer, Movement> moveList) {
		for (Movement movement : moveList.values()) {
			if (movement.equals(new Movement(BOARD[x][y], BOARD[x2][y2]))) {
				return true;
			}
		}
		return false;
	}

	private Movement moveExtract(int x, int y, int x2, int y2, SortedMap<Integer, Movement> moveList) {
		for (Movement movement : moveList.values()) {
			if (movement.equals(new Movement(BOARD[x][y], BOARD[x2][y2]))) {
				return movement;
			}
		}
		return null;
	}

	private void checkMoveKill(int x, int y) {

		// Tengo que tener en cuenta si la ficha es la 1 o la 0 y 9, 10 no compruebe la
		// -1 y la 11
		// fallo aposta para que me salte el error

		if (x != 10) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {// && BOARD[x+1][y] != BOARD[9][y] && BOARD[x+1][y] !=
															// BOARD[10][y](No creo que sea necesario ya que con x10
															// nunca va a tener un x11 el fallo en este caso puede ser
															// que llame al BOARD[11][y] y de fallo por salirse del
															// limite del array
				if (x + 2 != 11) {
					if (BOARD[x + 1][y].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& BOARD[x + 2][y].returnToken().isPresent()) {
						if (BOARD[x + 2][y].returnToken().get().getType() == BOARD[x][y].returnToken().get()
								.getType()) {
							if (BOARD[x + 1][y].returnToken().get().getType() == TokenType.King
									&& BOARD[x][y].returnToken().get().getType() == TokenType.Attacker) {
								if (BOARD[x + 1][y + 1].returnToken().isPresent()
										&& BOARD[x + 1][y - 1].returnToken().isPresent()) {
									if (BOARD[x + 1][y + 1].returnToken().get().getType() == BOARD[x + 1][y - 1]
											.returnToken().get().getType()
											&& BOARD[x + 1][y - 1].returnToken().get().getType() == BOARD[x][y]
													.returnToken().get().getType()) {
										kill(x + 1, y);
									}
								} else if (BOARD[x + 1][y - 1].returnToken().isPresent()
										&& BOARD[x + 1][y + 1].getType() == SquareType.Throne) {
									if (BOARD[x + 1][y - 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x + 1, y);

									}
								} else if (BOARD[x + 1][y + 1].returnToken().isPresent()
										&& BOARD[x + 1][y - 1].getType() == SquareType.Throne) {
									if (BOARD[x + 1][y + 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x + 1, y);
									}
								}
							} else {
								kill(x + 1, y);

							}

						}
					}
				}
			}
		}

		if (x != 0) {
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (x - 2 != -1) {
					if (BOARD[x - 1][y].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& BOARD[x - 2][y].returnToken().isPresent()) {
						if (BOARD[x - 2][y].returnToken().get().getType() == BOARD[x][y].returnToken().get()
								.getType()) {
							if (BOARD[x - 1][y].returnToken().get().getType() == TokenType.King
									&& BOARD[x][y].returnToken().get().getType() == TokenType.Attacker) {
								if (BOARD[x - 1][y + 1].returnToken().isPresent()
										&& BOARD[x - 1][y - 1].returnToken().isPresent()) {
									if (BOARD[x - 1][y + 1].returnToken().get().getType() == BOARD[x - 1][y - 1]
											.returnToken().get().getType()
											&& BOARD[x - 1][y - 1].returnToken().get().getType() == BOARD[x][y]
													.returnToken().get().getType()) {
										kill(x - 1, y);
									}
								} else if (BOARD[x - 1][y - 1].returnToken().isPresent()
										&& BOARD[x - 1][y + 1].getType() == SquareType.Throne) {
									if (BOARD[x - 1][y - 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x - 1, y);

									}
								} else if (BOARD[x - 1][y + 1].returnToken().isPresent()
										&& BOARD[x - 1][y - 1].getType() == SquareType.Throne) {
									if (BOARD[x - 1][y + 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x - 1, y);
									}
								}
							} else {
								kill(x - 1, y);

							}
						}
					}
				}
			}
		}

		if (y != 10) {
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (y + 2 != 11) {
					if (BOARD[x][y + 1].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& BOARD[x][y + 2].returnToken().isPresent()) {
						if (BOARD[x][y + 2].returnToken().get().getType() == BOARD[x][y].returnToken().get()
								.getType()) {
							if (BOARD[x][y + 1].returnToken().get().getType() == TokenType.King
									&& BOARD[x][y].returnToken().get().getType() == TokenType.Attacker) {
								if (BOARD[x - 1][y + 1].returnToken().isPresent()
										&& BOARD[x + 1][y + 1].returnToken().isPresent()) {
									if (BOARD[x - 1][y + 1].returnToken().get().getType() == BOARD[x + 1][y + 1]
											.returnToken().get().getType()
											&& BOARD[x + 1][y + 1].returnToken().get().getType() == BOARD[x][y]
													.returnToken().get().getType()) {
										kill(x, y + 1);
									}
								} else if (BOARD[x - 1][y + 1].returnToken().isPresent()
										&& BOARD[x + 1][y + 1].getType() == SquareType.Throne) {
									if (BOARD[x - 1][y + 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x, y + 1);

									}
								} else if (BOARD[x + 1][y + 1].returnToken().isPresent()
										&& BOARD[x - 1][y + 1].getType() == SquareType.Throne) {
									if (BOARD[x + 1][y + 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x, y + 1);
									}
								}
							} else {
								kill(x, y + 1);

							}
						}
					}
				}
			}
		}

		if (y != 0) {
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (y - 2 != -1) {
					if (BOARD[x][y - 1].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& BOARD[x][y - 2].returnToken().isPresent()) {
						if (BOARD[x][y - 2].returnToken().get().getType() == BOARD[x][y].returnToken().get()
								.getType()) {
							if (BOARD[x][y - 1].returnToken().get().getType() == TokenType.King
									&& BOARD[x][y].returnToken().get().getType() == TokenType.Attacker) {
								if (BOARD[x - 1][y - 1].returnToken().isPresent()
										&& BOARD[x + 1][y - 1].returnToken().isPresent()) {
									if (BOARD[x - 1][y - 1].returnToken().get().getType() == BOARD[x + 1][y - 1]
											.returnToken().get().getType()
											&& BOARD[x + 1][y - 1].returnToken().get().getType() == BOARD[x][y]
													.returnToken().get().getType()) {
										kill(x, y - 1);
									}
								} else if (BOARD[x - 1][y - 1].returnToken().isPresent()
										&& BOARD[x + 1][y - 1].getType() == SquareType.Throne) {
									if (BOARD[x - 1][y - 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x, y - 1);

									}
								} else if (BOARD[x + 1][y - 1].returnToken().isPresent()
										&& BOARD[x - 1][y - 1].getType() == SquareType.Throne) {
									if (BOARD[x + 1][y - 1].returnToken().get().getType() == TokenType.Attacker) {
										kill(x, y - 1);
									}
								}
							} else {
								kill(x, y - 1);

							}

						}
					}
				}
			}
		}
	}

	private void kill(int z, int w) {
		BOARD[z][w].returnToken().get().setSquare(null);
		BOARD[z][w].setToken(null);
	}

	public void drawLast(int x, int y, int x2, int y2, SortedMap<Integer, Movement> moveList) {
		Movement move;
		move = moveExtract(x, y, x2, y2, moveList);
		for (int i = 0; i < BOARD.length; i++) {
			if (i == 0) {
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}

			for (int j = 0; j <= BOARD[i].length; j++) {
				if (move.getSquareD() == BOARD[i][j]) {
					System.out.printf("%s%s%s", LAST, BOARD[i][j].drawColorLess(), RESET);
				} else if (j != -1) {
					System.out.printf("%s", BOARD[i][j].draw());
				} else if (j == -1) {
					if (i == 10) {
						System.out.printf("%s%-3s", BOLD, i);
					} else {
						System.out.printf("%s%-3s", BOLD, i);
					}
				} else if (j == 10) {
					if (i == 10) {
						System.out.printf("%s%3s", BOLD, i);
					} else {
						System.out.printf("%s%3s", BOLD, i);
					}
				}

			}
			if (i == 10) {
				System.out.println("");
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}
			System.out.println("\n");
		}

	}

	public void drawPosibleMove(SortedMap<Integer, Movement> moveList) {
	    // Imprimir encabezado de columna
	    System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
	    
	    for (int i = 0; i < BOARD.length; i++) {
	        // Imprimir número de fila al principio
	        System.out.printf("%s%-3s", BOLD, i);

	        for (int j = 0; j < BOARD[i].length; j++) {
	            boolean isPossibleMove = false;
	            for (Movement movement : moveList.values()) {
	                if (movement.getSquareD() == BOARD[i][j]) {
	                    isPossibleMove = true;
	                    break;
	                }
	            }

	            if (isPossibleMove) {
	                System.out.printf("%s%s%s%s", POSIBILITY, POSIBILITY2, BOARD[i][j].drawColorLess(), RESET);
	            } else {
	                System.out.printf("%s", BOARD[i][j].draw());
	            }
	        }

	        // Imprimir número de fila al final
	        System.out.printf("%s%3s\n", BOLD, i);
	        // Agregar salto de línea entre filas
	        System.out.println();
	    }

	    // Imprimir números de columna al final
	    System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
	}







	public Square[][] getBOARD() {
		return BOARD;
	}

}
