package board;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import square.Square;
import square.SquareType;
import token.TokenType;

/**
 * La clase Movement representa un movimiento. Su proposito es comprobar
 * movimientos validos y almacenar movimientos. Proporciona metodos para
 * comprobar movimientos validos y acceder a las casillas de los movimientos.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Board
 */
public class Movement {

	/**
	 * Variable que guarda la casilla de origen
	 */
	private Square squareO;

	/**
	 * Variable que guarda la casilla de destino
	 */
	private Square squareD;

	/**
	 * Variable que guarda un mapa de movimientos validos posibles
	 */
	private SortedMap<Integer, Movement> moValid = new TreeMap<>();

	/**
	 * Constructor de Movement sin parametros
	 */
	public Movement() {
	}

	/**
	 * Constructor de Movement con un parametro
	 *
	 * @param squareO
	 */
	public Movement(Square squareO) {
		this.squareO = squareO;
	}

	/**
	 * Constructor de Movement con dos parametros
	 * 
	 * @param squareO
	 * @param squareD
	 */
	public Movement(Square squareO, Square squareD) {
		this.squareO = squareO;
		this.squareD = squareD;

	}

	/**
	 * Metodo que devuelve una lista de movimientos validos para una determinada
	 * ficha
	 * 
	 * @param BOARD
	 * @param x
	 * @param y
	 * @return Lista de movimientos validos
	 */
	public SortedMap<Integer, Movement> movementList(Square[][] BOARD, int x, int y) {
		int index = 0;
		boolean stay;
		squareO = BOARD[x][y];

		stay = true;
		for (int i = x - 1; i >= 0 && stay; i--) {
			if (BOARD[i][y].returnToken().isEmpty() && (BOARD[i][y].getType() == SquareType.Normal
					|| BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[i][y]));
				index++;
			} else if (BOARD[i][y].returnToken().isEmpty() && BOARD[i][y].getType() == SquareType.Throne) {
				stay = true;
			} else {
				stay = false;
			}
		}
		stay = true;
		for (int i = x + 1; i <= 10 && stay; i++) {
			if (BOARD[i][y].returnToken().isEmpty() && (BOARD[i][y].getType() == SquareType.Normal
					|| BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[i][y]));
				index++;
			} else if (BOARD[i][y].returnToken().isEmpty() && BOARD[i][y].getType() == SquareType.Throne) {
				stay = true;
			} else {
				stay = false;
			}
		}
		stay = true;
		for (int i = y - 1; i >= 0 && stay; i--) {
			if (BOARD[x][i].returnToken().isEmpty() && (BOARD[x][i].getType() == SquareType.Normal
					|| BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[x][i]));
				index++;
			} else if (BOARD[x][i].returnToken().isEmpty() && BOARD[x][i].getType() == SquareType.Throne) {
				stay = true;
			} else {
				stay = false;
			}
		}
		stay = true;
		for (int i = y + 1; i <= 10 && stay; i++) {
			if (BOARD[x][i].returnToken().isEmpty() && (BOARD[x][i].getType() == SquareType.Normal
					|| BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[x][i]));
				index++;
			} else if (BOARD[x][i].returnToken().isEmpty() && BOARD[x][i].getType() == SquareType.Throne) {
				stay = true;
			} else {
				stay = false;
			}
		}
		return moValid;
	}

	/**
	 * Obtiene la casilla de destino
	 * 
	 * @return casilla de destino
	 */
	public Square getSquareD() {
		return squareD;
	}

	/**
	 * Obtiene la casilla de origen
	 * 
	 * @return casilla de origen
	 */
	public Square getSquareO() {
		return squareO;
	}

	/**
	 * Devuelve el valor hash code para Movimiento
	 */
	@Override
	public int hashCode() {
		return Objects.hash(squareD, squareO);
	}

	/**
	 * Compara el objeto especificado con este Movimiento para ver si son iguales
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		return Objects.equals(squareD, other.squareD) && Objects.equals(squareO, other.squareO);
	}

}
