package clasesMod;

/**
 * El enum SquareType contiene los tipos posibles de casilla. Se utiliza para
 * almacenar información independiente de cada tipo de casilla. Proporciona
 * métodos para acceder a la información del tipo.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Square
 */
public enum SquareType {

	/**
	 * El enum Throne es la casilla central del tablero (unicode-Color fondo negro,
	 * unicode-Color negro).
	 */
	Throne("\u001B[40m", "\u001B[30m") {

	},

	/**
	 * El enum Corner son las casillas de las esquinas del tablero (unicode-Color
	 * fondo negro, unicode-Color negro).
	 */
	Corner("\u001B[40m", "\u001B[30m") {

	},

	/**
	 * El enum Normal son las casillas que no son ni las esquinas ni la casilla
	 * central (unicode-Color fondo verde, unicode-Color verde).
	 */
	Normal("\u001B[42m", "\u001B[32m") {

	};

	/**
	 * Color de la casilla.
	 */
	private final String color;

	/**
	 * Color auxiliar para dibujar casillas vacias.
	 */
	private final String color2;

	/**
	 * Constructor de SquareType.
	 * 
	 * @param color  color de la casilla
	 * @param color2 color auxiliar para casillas vacias
	 */
	SquareType(String color, String color2) {
		this.color = color;
		this.color2 = color2;
	}

	/**
	 * Obtiene el color de la casilla.
	 * 
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Obtiene el color auxiliar de la casilla.
	 * 
	 * @return color auxiliar
	 */
	public String getColor2() {
		return color2;
	}

}
