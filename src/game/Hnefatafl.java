package game;

/**
 * La clase Hnefatafl representa el juego en si. Su proposito es contener el
 * main y es desde donde se ejecuta el juego.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Game
 */
public class Hnefatafl {

	/**
	 * Metodo que crea la partida y la inicia
	 */
	public void start() {
		Game game = new Game();

		game.start();
	}

	/**
	 * Main de la clase Hnefatafl
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Hnefatafl().start();
	}

}
