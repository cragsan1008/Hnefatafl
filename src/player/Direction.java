package player;

public enum Direction {

	Right('+','x'),
	Left('-','x'),
	Up('-','y'),
	Down('+','y');
	
	private final char symbol;
	private final char axis;

	Direction(char symbol, char axis){
		this.symbol = symbol;
		this.axis = axis;
	}

	public char getSymbol() {
		return symbol;
	}

	public char getAxis() {
		return axis;
	}
	
	
}
