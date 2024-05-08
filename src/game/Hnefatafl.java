package game;

/**
 * La clase Hnefatafl representa el juego en si. Su proposito es contener el main y es desde donde se ejecuta el juego.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Game
 */
public class Hnefatafl {

	public void start(){
		Game game = new Game();
		
		game.start();
	}
	
	public static void main(String[] args) {
		new Hnefatafl().start();
	}

}
