package board;

import java.util.Optional;
import java.util.SortedMap;

import player.Player;
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
 * @version 2.0
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
	/**
	 * Constante que almacena el valor para resetear los estilos apliacados al
	 * texto.
	 */
	private static final String RESET = "\u001b[0m";
	/**
	 * Constante que almacena el valor para representar el fondo en color Amarillo.
	 */
	private static final String LAST = "\u001B[43m";
	/**
	 * Constante que almacena el valor para representar el texto en color Amarillo.
	 */
	private static final String HIDE = "\u001B[33m";
	/**
	 * Constante que almacena el valor para representar el fondo en color Azul.
	 */
	private static final String POSIBILITY = "\u001B[44m";
	/**
	 * Constante que almacena el valor para representar el texto en color Azul.
	 */
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
	 * @see Square#draw()
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

	/**
	 * Metodo que obtiene un Movimiento y mueve la ficha según dicho movimiento y
	 * llama a otro metodo para realizar comprobaciones y dibujar
	 * 
	 * @version 2.0
	 * @since 1.0
	 * @param player
	 * @see Player#confirmMovePiece()
	 */
	public void move(Player player) {
		Optional<Token> opTok;
		Movement m;
		int x, y, x2, y2;

		m = player.confirmMovePiece();
		x = m.getSquareO().getPosition().getX();
		y = m.getSquareO().getPosition().getY();
		x2 = m.getSquareD().getPosition().getX();
		y2 = m.getSquareD().getPosition().getY();

		BOARD[x][y].returnToken().get().setSquare(BOARD[x2][y2]);
		opTok = BOARD[x][y].returnToken();
		BOARD[x2][y2].setToken(opTok.get());
		BOARD[x][y].setToken(null);

		checkMoveKill(x2, y2);
		drawLast(m);
	}

	/**
	 * Metodo que realiza comprobaciones si el movimiento realizado mata y llama a
	 * otro metodo para que mate en caso de que asi sea, llama a otro metodo si se
	 * trata del rey las comprobaciones
	 * 
	 * @version 2.0
	 * @since 1.0
	 * @param x
	 * @param y
	 * @see #kill(int, int)
	 * @see #killKing(int, int)
	 */
	private void checkMoveKill(int x, int y) {
		if (x - 1 >= 0 && y + 1 <= 10 && x + 1 <= 10 && y - 1 >= 0) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (BOARD[x + 1][y].returnToken().get().getType() == TokenType.King) {
					killKing(x + 1, y);
				}
			}
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (BOARD[x - 1][y].returnToken().get().getType() == TokenType.King) {
					killKing(x - 1, y);
				}
			}
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (BOARD[x][y + 1].returnToken().get().getType() == TokenType.King) {
					killKing(x, y + 1);
				}
			}
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (BOARD[x][y - 1].returnToken().get().getType() == TokenType.King) {
					killKing(x, y - 1);
				}
			}
		}
		if (y != 0) {
			if (BOARD[x][y - 1].returnToken().isPresent()) {
				if (y - 2 != -1) {
					if (BOARD[x][y - 1].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& (BOARD[x][y - 2].returnToken().isPresent()
									|| BOARD[x][y - 2].getType() != SquareType.Normal)) {
						if (BOARD[x][y - 2].returnToken().isPresent()) {

							if (BOARD[x][y - 2].returnToken().get().getType() == BOARD[x][y].returnToken().get()
									.getType() || BOARD[x][y - 2].getType() != SquareType.Normal) {
								if (BOARD[x][y - 1].returnToken().get().getType() != TokenType.King) {

									kill(x, y - 1);

								}

							}
						}
						else if(BOARD[x][y-2].getType() != SquareType.Normal && BOARD[x][y - 1].returnToken().get().getType() != TokenType.King) {
							kill(x, y -1);
						}
					}
				}
			}
		}
		if (y != 10) {
			if (BOARD[x][y + 1].returnToken().isPresent()) {
				if (y + 2 != 11) {
					if (BOARD[x][y + 1].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& (BOARD[x][y + 2].returnToken().isPresent()
									|| BOARD[x][y + 2].getType() != SquareType.Normal)) {
						if (BOARD[x][y + 2].returnToken().isPresent()) {
							if (BOARD[x][y + 2].returnToken().get().getType() == BOARD[x][y].returnToken().get()
									.getType() || BOARD[x][y + 2].getType() != SquareType.Normal) {
								if (BOARD[x][y + 1].returnToken().get().getType() != TokenType.King) {
									kill(x, y + 1);

								}
							}
						}
						else if(BOARD[x][y+2].getType() != SquareType.Normal && BOARD[x][y + 1].returnToken().get().getType() != TokenType.King) {
							kill(x, y+1);
						}
					}
				}
			}
		}
		if (x != 0) {
			if (BOARD[x - 1][y].returnToken().isPresent()) {
				if (x - 2 != -1) {
					if (BOARD[x - 1][y].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& (BOARD[x - 2][y].returnToken().isPresent() || BOARD[x - 2][y].getType() != SquareType.Normal)) {
						if (BOARD[x - 2][y].returnToken().isPresent()) {
							if (BOARD[x - 2][y].returnToken().get().getType() == BOARD[x][y].returnToken().get()
									.getType() || BOARD[x - 2][y].getType() != SquareType.Normal) {
								if (BOARD[x - 1][y].returnToken().get().getType() != TokenType.King) {
									kill(x - 1, y);

								}
							}
						}
						else if(BOARD[x - 2][y].getType() != SquareType.Normal && BOARD[x - 1][y].returnToken().get().getType() != TokenType.King) {
							kill(x - 1, y);
						}

					}
				}
			}
		}

		if (x != 10) {
			if (BOARD[x + 1][y].returnToken().isPresent()) {
				if (x + 2 != 11) {
					if (BOARD[x + 1][y].returnToken().get().getType() != BOARD[x][y].returnToken().get().getType()
							&& (BOARD[x + 2][y].returnToken().isPresent()
									|| BOARD[x + 2][y].getType() != SquareType.Normal)) {
						if (BOARD[x + 2][y].returnToken().isPresent()) {
							if (BOARD[x + 2][y].returnToken().get().getType() == BOARD[x][y].returnToken().get()
									.getType() || BOARD[x + 2][y].getType() != SquareType.Normal) {
								if (BOARD[x + 1][y].returnToken().get().getType() != TokenType.King) {
									kill(x + 1, y);

								}
							}
						}
						else if(BOARD[x + 2][y].getType() != SquareType.Normal && BOARD[x + 1][y].returnToken().get().getType() != TokenType.King) {
							kill(x + 1, y);
						}
					}
				}
			}
		}

	}

	/**
	 * Metodo que compruebe si el rey puede morir por el movimiento realizado, si es
	 * asi, llama a otro metodo para matarlo
	 * 
	 * @version 1.0
	 * @since 2.0
	 * @param x
	 * @param y
	 * @see #checkMoveKill(int, int)
	 */
	private void killKing(int x, int y) {
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
						if (BOARD[x][y + 1].returnToken().isPresent() && BOARD[x][y - 1].returnToken().isPresent())
							kill(x, y);
					}
				}
			}
		}
	}

	/**
	 * Metodo que mata a las fichas
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @param z
	 * @param w
	 * @see #checkMoveKill(int, int)
	 */
	private void kill(int z, int w) {
		BOARD[z][w].returnToken().get().setSquare(null);
		BOARD[z][w].setToken(null);
	}

	/**
	 * Metodo que dibuja el ultimo movimiento de manera resaltada
	 * @version 1.0
	 * @since 2.0
	 * @param move
	 * @see Square#drawColorLess()
	 * @see Square#draw()
	 */
	public void drawLast(Movement move) {
		for (int i = 0; i < BOARD.length; i++) {
			if (i == 0) {
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}

			for (int j = -1; j < BOARD[i].length; j++) {
				if (j != -1) {
					if (move.getSquareD() == BOARD[i][j]) {
						System.out.printf("%s%s%s", LAST, BOARD[i][j].drawColorLess(), RESET);
					} else if (move.getSquareO() == BOARD[i][j]) {
						System.out.printf("%s%s%s%s", LAST, HIDE, BOARD[i][j].drawColorLess(), RESET);
					} else {
						System.out.printf("%s", BOARD[i][j].draw());

					}
				}
				if (j == -1) {
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

	/**
	 * Metodo que dibuja los posibles movimientos de una ficha
	 * @version 1.0
	 * @since 2.0
	 * @param moveList
	 * @see Square#drawColorLess()
	 */
	public void drawPosibleMove(SortedMap<Integer, Movement> moveList) {
		boolean possible;
		for (int i = 0; i < BOARD.length; i++) {
			if (i == 0) {
				System.out.printf("%sx  y0    1   2   3    4   5   6    7   8   9   10 y  x\n", BOLD);
			}

			for (int j = -1; j < BOARD[i].length; j++) {
				possible = false;
				if (j != -1) {
					for (Movement movement : moveList.values()) {
						if (movement.getSquareD() == BOARD[i][j]) {
							possible = true;
						}
					}
					if (possible) {
						System.out.printf("%s%s%s%s", POSIBILITY, POSIBILITY2, BOARD[i][j].drawColorLess(), RESET);
					} else {
						System.out.printf("%s", BOARD[i][j].draw());
					}
				}
				if (j == -1) {
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

	/**
	 * Obtiene el Array bidimensional que conforma el tablero.
	 * 
	 * @return BOARD
	 */
	public Square[][] getBOARD() {
		return BOARD;
	}

}
