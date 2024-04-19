package token;

/**
 * El enum TokenType contiene los tipos posibles de fichas.
 * Se utiliza para almacenar información independiente de cada tipo de ficha.
 * Proporciona métodos para acceder a la información del tipo.
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Token
 */
public enum TokenType {
	
	/**
	 * El enum Defender es una ficha normal del bando defensor (unicode-Color blanco, char-Torre ajedrez).
	 */
	Defender("\u001B[37m", '♜'),
	
	/**
	 * El enum Attacker es una ficha normal del bando atacante (unicode-Color azul, char-Torre ajedrez).
	 */
	Attacker("\u001B[34m", '♜'),
	
	/**
	 * El enum Defender es la ficha más importante del bando defensor (unicode-Color blanco, char-Rey ajedrez).
	 */
	King("\u001B[31m", '♚');
	
	
	/**
	 * Color de la ficha.
	 */
	private final String color;
	
	/**
	 * Forma de la ficha.
	 */
	private final char shape;
	
	/**
	 * Constructor de TokenType.
	 * 
	 * @param color color de la ficha
	 * @param shape forma de la ficha
	 */
	TokenType(String color, char shape) {
		this.color=color;
		this.shape=shape;
	}

	/**
	 * Obtiene el color de la ficha.
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Obtiene la forma de la casilla.
	 * @return forma
	 */
	public char getShape() {
		return shape;

	}
}
