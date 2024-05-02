package game;

import java.util.Scanner;

import board.Board;
import console.ConsoleInput;

public class Hnefatafl {

	public void start(){
		Game game = new Game();
		Board board;
		
		game.start();
	}
	
	public static void main(String[] args) {
		new Hnefatafl().start();
	}

}
