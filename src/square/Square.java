package square;


import java.util.Optional;

import token.Token;
import token.TokenType;

public class Square {
	
	private final Position POSITION;
	private Token token;
	private SquareType type;
	private String color;
	private static final String RESET= "\u001b[0m";
	
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
		this.color = type.getColor();
	}

	public Position getPosition() {
		return POSITION;
	}

	public Token getToken() {
		return token;
	}

	public SquareType getType() {
		return type;
	}

	public String getColor() {
		return color;
	}
	
	public String draw() {
		Optional<Token> optToken;
		optToken = Optional.ofNullable(this.token);
		if(optToken.isEmpty()) {
			return String.format("%s%s ♜ %s|", color, type.getColor2(), RESET);
		}
		else {
			return String.format("%s %s %s|", color, token.draw(), RESET);
		}
	}
	
}
