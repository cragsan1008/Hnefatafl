package game;

public class Hnefatafl {

	public void start(){
		Game game = new Game();
		game.getBoard().draw();
	}
	
	public static void main(String[] args) {
		new Hnefatafl().start();
	}

}
