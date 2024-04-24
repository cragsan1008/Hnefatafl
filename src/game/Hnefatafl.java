package game;

import java.util.Scanner;

import board.Board;
import console.ConsoleInput;

public class Hnefatafl {

	public void start(){
		Scanner keyboard = new Scanner(System.in);
		ConsoleInput console = new ConsoleInput(keyboard);
		Game game = new Game();
		Board board;
		board = game.getBoard();
		board.draw();

		board.move(console);
		board.draw();

		board.move(console);
		board.draw();

		board.move(console);

		board.draw();
	}
	
	public static void main(String[] args) {
		new Hnefatafl().start();
	}

}
