package clasesMod;

import java.util.Objects;
import java.util.Optional;

/**
 * La clase Token representa cada ficha del ajedrez Vikingo. Se utiliza para
 * almacenar información sobre la ficha y puede almacenar la casilla en la que
 * se encuentra. Proporciona métodos para acceder a la información de la ficha y
 * dibujar la ficha.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Square
 */
public class Token {

	/**
	 * Atributo en el que se almacena la casilla en la que se encuentra la ficha.
	 */
	private Square square;

	/**
	 * Atributo en el que se almacena el tipo de la ficha.
	 */
	private TokenType type;

	/**
	 * Constructor de la clase Token.
	 * 
	 * @param square Casilla en la que se encuentra la ficha
	 * @param type   Tipo de la ficha
	 */
	public Token(Square square, TokenType type) {
		this.square = square;
		this.type = type;
	}

	/**
	 * Obtiene un Optional de la casilla de la ficha
	 * 
	 * @return Optional de casilla
	 */
	public Optional<Square> returnSquare() {
		Optional<Square> optSquare;
		optSquare = Optional.ofNullable(square);
		return optSquare;
	}

	/**
	 * Obtiene el tipo de la ficha
	 * 
	 * @return tipo
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Devuelve el String de la representación grafica de la ficha
	 * 
	 * @return Dibujo de la ficha
	 */
	public String draw() {
		return String.format("%s%s", this.getType().getColor(), this.getType().getShape());

	}

	/**
	 * Establece la casilla
	 * 
	 * @param square
	 */
	public void setSquare(Square square) {
		this.square = square;
	}

	@Override
	public int hashCode() {
		return Objects.hash(square, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(square, other.square) && type == other.type;
	}

}
