package square;

public enum SquareType {
	Throne("BLACK_BACKGROUND"){
		
	}, 
	Corner("BLACK_BACKGROUND"){
		
	}, 
	Normal("GREEN_BACKGROUND"){
		
	};

	private final String color;

	SquareType(String color) {
		this.color= color;
	}
}
