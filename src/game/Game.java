package game;

import java.util.Scanner;

import board.Board;
import console.ConsoleInput;
import player.AI;
import player.Person;
import player.Player;
import square.SquareType;
import token.TokenType;

public class Game {

	private Board board;
	private Player playerOne;
	private Player playerTwo;
	Scanner keyboard = new Scanner(System.in);
	ConsoleInput console = new ConsoleInput(keyboard);

	Game(){
		board = new Board();
		System.out.println("Elige:\n1.1 vs 1\n2.1 vs IA\n3.IA vs IA");
		switch(console.readIntInRange(1, 3)) {
			case 1 ->{
				System.out.println("Elige rol de el jugador 1, el dos recibira el rol contrario:\n1.Defensor\n2.Atacante");
				switch(console.readIntInRange(1, 2)) {
					case 1 ->{
						playerOne = new Person("Defender");
						playerTwo = new Person("Attacker");
					}
					case 2 ->{
						playerOne = new Person("Attacker");
						playerTwo = new Person("Defender");
					}
				}
			}
			case 2 ->{
				System.out.println("Elige rol de el jugador, la IA recibira el rol contrario:\n1.Defensor\n2.Atacante");
				switch(console.readIntInRange(1, 2)) {
					case 1 ->{
						playerOne = new Person("Defender");
						playerTwo = new AI("Attacker");
					}
					case 2 ->{
						playerOne = new Person("Attacker");
						playerTwo = new AI("Defender");
					}
				}
			}
			case 3 ->{
				System.out.println("Elige rol de la IA1, la IA2 recibira el rol contrario:\n1.Defensor\n2.Atacante");
				switch(console.readIntInRange(1, 2)) {
					case 1 ->{
						playerOne = new AI("Defender");
						playerTwo = new AI("Attacker");
					}
					case 2 ->{
						playerOne = new AI("Attacker");
						playerTwo = new AI("Defender");
					}
				}
			}
			default ->{
				System.out.println("Elección invalida");
				new Game();
				return;
			}
		
		}
	}
	
	public void start() {
		boolean unfineshed;
		int initial = 0;
		unfineshed = true;
		while (unfineshed) {
			if(initial == 0) {
				board.draw();
				initial++;
			}
			System.out.println("Turno del jugador 1:");
			board.move(playerOne);
			unfineshed = checkBoard();
			if(unfineshed) {
				System.out.println("Turno del jugador 2:");
				board.move(playerTwo);
			}
			unfineshed = checkBoard();
		}
	}
	
	private boolean checkBoard() {
		for(int i = 0 ; i <= 10 ; i++) {
			for(int j = 0; j <= 10 ; j++) {
				if(board.getBOARD()[i][j].returnToken().isPresent()) {
					if(board.getBOARD()[i][j].returnToken().get().getType() == TokenType.King && board.getBOARD()[i][j].getType() != SquareType.Corner) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	public Board getBoard() {
		return board;
		
	}
	
}
