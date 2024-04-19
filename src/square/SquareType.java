package square;

public enum SquareType {
	Throne("\u001B[40m", "\u001B[30m"){
		
	}, 
	Corner("\u001B[40m", "\u001B[30m"){
		
	}, 
	Normal("\u001B[42m", "\u001B[32m"){

		
	};

	private final String color;

	private final String color2;
	
	SquareType(String color, String color2) {
		this.color = color;
		this.color2 = color2;
	}

	public String getColor() {
		return color;
	}
	
	public String getColor2() {
		return color2;
	}
	
}
