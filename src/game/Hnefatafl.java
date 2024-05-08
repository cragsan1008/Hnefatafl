package game;

import board.Board;

/**
 * La clase Game representa una partida. Su proposito es empezar las partidas y reiniciar la partida.
 * Proporciona metodos para empezar la partida y reiniciar la partida.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Board
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
