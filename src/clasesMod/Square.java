package clasesMod;

import java.util.Objects;
import java.util.Optional;



/**
 * La clase Square representa cada casilla de tablero. Se utiliza para almacenar
 * información sobre la casilla y puede almacenar una ficha. Proporciona métodos
 * para acceder a la información de la casilla y dibujar la casilla.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Board
 * @see Token
 */
public class Square {

	/**
	 * Atributo final que representa las coordenadas de la casilla.
	 */
	private final Position POSITION;

	/**
	 * Atributo que puede almacenar una ficha
	 */
	private Token token;

	/**
	 * Atributo final que almacena el tipo de la casilla
	 */
	private final SquareType TYPE;

	/**
	 * Constante que almacena valor para borrar los colores
	 */
	private static final String RESET = "\u001b[0m";

	/**
	 * Constructor de la clase Square. Dependiendo de los parametros pasados crea
	 * ficha y la almacena, también toma un valor u otro en SquareType
	 * 
	 * @param x Parte de la clase Position
	 * @param y Parte de la clase Position
	 */
	public Square(int x, int y) {
		this.POSITION = new Position(x, y);

		// Atacantes
		if (x == 0 && y >= 3 && y <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (x == 10 && y >= 3 && y <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (y == 0 && x >= 3 && x <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (y == 10 && x >= 3 && x <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (x == 1 && y == 5) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (x == 9 && y == 5) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (y == 1 && x == 5) {
			this.token = new Token(this, TokenType.Attacker);
		} else if (y == 9 && x == 5) {
			this.token = new Token(this, TokenType.Attacker);
		}

		// Defensores
		else if (x == 5 && y == 5) {
			this.token = new Token(this, TokenType.King);
		} else if (x >= 3 && x <= 7 && y == 5 && !(x == 5 && y == 5)) {
			this.token = new Token(this, TokenType.Defender);
		} else if (x >= 4 && x <= 6 && y == 4) {
			this.token = new Token(this, TokenType.Defender);
		} else if (x >= 4 && x <= 6 && y == 6) {
			this.token = new Token(this, TokenType.Defender);
		} else if (x >= 4 && x <= 6 && y == 4) {
			this.token = new Token(this, TokenType.Defender);
		} else if (x == 5 && y == 3) {
			this.token = new Token(this, TokenType.Defender);
		} else if (x == 5 && y == 7) {
			this.token = new Token(this, TokenType.Defender);
		}

		if ((x == 0 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 0) || (x == 10 && y == 10)) {
			this.TYPE = SquareType.Corner;
		} else if (x == 5 && y == 5) {
			this.TYPE = SquareType.Throne;
		} else {
			this.TYPE = SquareType.Normal;
		}
	}

	public Square(int x, int y, String custom) {
		this.POSITION = new Position(x, y);

		if (custom.equals("D")) {
			this.token = new Token(this, TokenType.Defender);
		} else if (custom.equals("A")) {
			this.token = new Token(this, TokenType.Attacker);
		}

		else if (custom.equals("K")) {
			this.token = new Token(this, TokenType.King);
		}

		if ((x == 0 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 0) || (x == 10 && y == 10)) {
			this.TYPE = SquareType.Corner;
		} else if (x == 5 && y == 5) {
			this.TYPE = SquareType.Throne;
		} else {
			this.TYPE = SquareType.Normal;
		}
	}

	/**
	 * Constructor de square para clonar casillas
	 * 
	 * @param square
	 */
	public Square(Square square) {
		this.token = square.returnToken().orElse(null);
		this.POSITION = square.POSITION;
		this.TYPE = square.TYPE;
	}

	/**
	 * Obtiene la posicion de la casilla.
	 * 
	 * @return Posición
	 */
	public Position getPosition() {
		return POSITION;
	}

	/**
	 * Obtiene un Optional de la ficha de la casilla
	 * 
	 * @return Optional de token
	 */
	public Optional<Token> returnToken() {
		Optional<Token> optToken;
		optToken = Optional.ofNullable(token);
		return optToken;
	}

	/**
	 * Obtiene el tipo de la casilla
	 * 
	 * @return tipo
	 */
	public SquareType getType() {
		return TYPE;
	}

	/**
	 * Obtiene el String de la representación de la casilla
	 * 
	 * @return Dibujo de la casilla
	 */
	public String draw() {
		if (returnToken().isEmpty()) {
			return String.format("%s%s ♜ %s|", TYPE.getColor(), TYPE.getColor2(), RESET);
		} else {
			return String.format("%s %s %s|", TYPE.getColor(), token.draw(), RESET);
		}
	}

	/**
	 * Obtiene el String de la representación de la casilla sin color
	 * 
	 * @return Dibujo de la casilla
	 */
	public String drawColorLess() {
		if (returnToken().isEmpty()) {
			return String.format(" ♜ %s|", RESET);
		} else {
			return String.format(" %s %s|", token.draw(), RESET);
		}
	}

	/**
	 * Establece la ficha de la casilla
	 * 
	 * @param token
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(POSITION, TYPE, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		return Objects.equals(POSITION, other.POSITION) && TYPE == other.TYPE && Objects.equals(token, other.token);
	}

}
