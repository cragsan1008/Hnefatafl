package game;

import board.Board;
import player.Player;

public class Game {

	private Board board;
	private Player playerOne;
	private Player playerTwo;

	Game(){
		board = new Board();
//		playerOne = new Person();
	}
	
	public Board getBoard() {
		return board;
	}
	
}
