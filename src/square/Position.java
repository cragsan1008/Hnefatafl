package square;

import java.util.Objects;

/**
 * La clase Position representa la posición de una casilla. Almacena la
 * información sobre la posición. Proporciona métodos para acceder a la
 * información de la posición.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Square
 */
public class Position implements Comparable<Position> {

	/**
	 * Uno de los atributos de la clase Position que representa el eje x
	 */
	private int x;

	/**
	 * Uno de los atributos de la clase Position que representa el eje y
	 */
	private int y;

	/**
	 * Constructor de Position
	 * 
	 * @param x representa el eje x
	 * @param y representa el eje y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Obtiene el eje x
	 * 
	 * @return eje x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Obtiene el eje y
	 * 
	 * @return eje y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Devuelve el valor hash code para Posicion
	 * 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * Compara el objeto especificado con esta Posicion para ver si son iguales
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compara la Posicion especificado con esta Posicion para ver si son iguales
	 */
	@Override
	public int compareTo(Position other) {
		int cmp = Integer.compare(this.x, other.x);
		if (cmp != 0) {
			return cmp;
		}
		return Integer.compare(this.y, other.y);
	}

}
