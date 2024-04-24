package square;

import java.util.Objects;
import java.util.Optional;

import board.Board;
import token.Token;
import token.TokenType;

/**
 * La clase Square representa cada casilla de tablero.
 * Se utiliza para almacenar información sobre la casilla y puede almacenar una ficha.
 * Proporciona métodos para acceder a la información de la casilla y dibujar la casilla.
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
	private final SquareType type;
	
	/**
	 * Constante que almacena valor para borrar los colores
	 */
	private static final String RESET= "\u001b[0m";
	
	/**
	 * Constructor de la clase Square. Dependiendo de los parametros 
	 * pasados crea ficha y la almacena, también toma un valor u otro en SquareType 
	 * @param x Parte de la clase Position
	 * @param y Parte de la clase Position
	 */
	public Square(int x, int y){
		this.POSITION = new Position(x ,y);
		
		//Atacantes
		if (x == 0 && y >= 3 && y <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(x == 10 && y >= 3 && y <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(y == 0 && x >= 3 && x <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(y == 10 && x >= 3 && x <= 7) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(x == 1 && y == 5) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(x == 9 && y == 5) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(y == 1 && x == 5 ) {
			this.token = new Token(this, TokenType.Attacker);
		}
		else if(y == 9 && x == 5 ) {
			this.token = new Token(this, TokenType.Attacker);
		}
		
		//Defensores
		else if(x == 5 && y == 5) {
			this.token = new Token(this, TokenType.King);
		}
		else if(x >= 3 && x <= 7 && y == 5 && !(x == 5 && y == 5)) {
			this.token = new Token(this, TokenType.Defender);
		}
		else if(x >=4 && x <= 6 && y == 4) {
			this.token = new Token(this, TokenType.Defender);
		}
		else if(x >=4 && x <= 6 && y == 6) {
			this.token = new Token(this, TokenType.Defender);
		}
		else if(x >=4 && x <= 6 && y == 4) {
			this.token = new Token(this, TokenType.Defender);
		}
		else if(x == 5 && y == 3) {
			this.token = new Token(this, TokenType.Defender);
		}
		else if(x == 5 && y == 7) {
			this.token = new Token(this, TokenType.Defender);
		}
		
		if( (x == 0 && y == 0)||(x == 0 && y == 10)||(x == 10 && y == 0)||(x == 10 && y == 10)) {
			this.type = SquareType.Corner;
		}
		else if (x == 5 && y == 5) {
			this.type = SquareType.Throne;
		}
		else {
			this.type = SquareType.Normal;
		}
	}

	/**
	 * Obtiene la posicion de la casilla.
	 * @return Posición
	 */
	public Position getPosition() {
		return POSITION;
	}
	
	/**
	 * Obtiene un Optional de la ficha de la casilla
	 * @return Optional<Token> de token
	 */
	public Optional<Token> returnToken(){
		Optional<Token> optToken;
		optToken = Optional.ofNullable(token);
		return optToken;
	}

	/**
	 * Obtiene el tipo de la casilla
	 * @return tipo
	 */
	public SquareType getType() {
		return type;
	}
	
	/**
	 * Obtiene el String de la representación de la casilla
	 * @return Dibujo de la casilla
	 */
	public String draw() {
		if(returnToken().isEmpty()) {
			return String.format("%s%s ♜ %s|", type.getColor(), type.getColor2(), RESET);
		}
		else {
			return String.format("%s %s %s|", type.getColor(), token.draw(), RESET);
		}
	}
	
	public void move(){
		
	}
	
	public void kill() {
		token = null;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(POSITION, token, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Square))
			return false;
		Square other = (Square) obj;
		return Objects.equals(POSITION, other.POSITION) && Objects.equals(token, other.token) && type == other.type;
	}
	
}
