package token;

import java.util.Optional;

import square.Square;

public class Token {
	
	private Square square;
	private TokenType type;
	
	public Token(Square square, TokenType type){
		this.square = square;
		this.type = type;
	}

	public Square getSquare() {
		return square;
	}

	public Optional<Square> returnSquare() {
		Optional<Square> optSquare;
		optSquare = Optional.ofNullable(square);
		return optSquare;//.orElse(new Square(-1, -1));
	}

	public TokenType getType() {
		return type;
	}
	
	public String draw() {
		
		return String.format("%s%s", this.getType().getColor(), this.getType().getShape());
		
	}
	
}
