package token;

/**
 * El enum TokenType contiene los tipos posibles de fichas. Se utiliza para
 * almacenar información independiente de cada tipo de ficha. Proporciona
 * métodos para acceder a la información del tipo.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Token
 */
public enum TokenType {

	/**
	 * El enum Defender es una ficha normal del bando defensor unicodeColor blanco,
	 * charTorre ajedrez.
	 */
	Defender("\u001B[37m", '\u265C', "Defender"),

	/**
	 * El enum Attacker es una ficha normal del bando atacante unicodeColor azul,
	 * charTorre ajedrez.
	 */
	Attacker("\u001B[34m", '\u265C', "Attacker"),

	/**
	 * El enum Defender es la ficha más importante del bando defensor unicodeColor
	 * blanco, charRey ajedrez.
	 */
	King("\u001B[31m", '\u265A', "Defender");

	/**
	 * Color de la ficha.
	 */
	private final String color;

	/**
	 * Forma de la ficha.
	 */
	private final char shape;

	/**
	 * Rol de la ficha
	 */
	private final String rol;

	/**
	 * Constructor de TokenType.
	 * 
	 * @param color color de la ficha
	 * @param shape forma de la ficha
	 */
	TokenType(String color, char shape, String rol) {
		this.color = color;
		this.shape = shape;
		this.rol = rol;
	}

	/**
	 * Obtiene el color del tipo de ficha.
	 * 
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Obtiene la forma del tipo de ficha.
	 * 
	 * @return forma
	 */
	public char getShape() {
		return shape;

	}

	/**
	 * Obtiene rol del tipo de ficha
	 * 
	 * @return rol
	 */
	public String getRol() {
		return rol;
	}
}
