package token;

public enum TokenType {
	Defender("\u001B[37m", '♜'),Attacker("\u001B[34m", '♜'),King("\u001B[31m", '♚');
	
	private final String color;
	private final char shape;
	
	TokenType(String color, char shape) {
		this.color=color;
		this.shape=shape;
	}

	public String getColor() {
		return color;
	}

	public char getShape() {
		return shape;

	}
}
