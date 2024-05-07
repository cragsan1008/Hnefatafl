package game;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
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
						playerOne = new Person("Defender", board);
						playerTwo = new Person("Attacker", board);
					}
					case 2 ->{
						playerOne = new Person("Attacker", board);
						playerTwo = new Person("Defender", board);
					}
				}
			}
			case 2 ->{
				System.out.println("Elige rol de el jugador, la IA recibira el rol contrario:\n1.Defensor\n2.Atacante");
				switch(console.readIntInRange(1, 2)) {
					case 1 ->{
						playerOne = new Person("Defender", board);
						playerTwo = new AI("Attacker", board);
					}
					case 2 ->{
						playerOne = new Person("Attacker", board);
						playerTwo = new AI("Defender", board);
					}
				}
			}
			case 3 ->{
				System.out.println("Elige rol de la IA1, la IA2 recibira el rol contrario:\n1.Defensor\n2.Atacante");
				switch(console.readIntInRange(1, 2)) {
					case 1 ->{
						playerOne = new AI("Defender", board);
						playerTwo = new AI("Attacker", board);
					}
					case 2 ->{
						playerOne = new AI("Attacker", board);
						playerTwo = new AI("Defender", board);
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
			unfineshed = checkBoard();
			if(unfineshed) {
				System.out.println("Turno del jugador 1:");
				board.move(playerOne);
				unfineshed = checkBoard();
			}
			if(unfineshed) {
				System.out.println("Turno del jugador 2:");
				board.move(playerTwo);
			}
			unfineshed = checkBoard();
			if(playerOne.isWinner()) {
				System.out.println("Gana el jugador 1");
			}
			else if(playerTwo.isWinner()) {
				System.out.println("Gana el jugador 2");

			}
		}
	}
	
	private boolean checkBoard() {
		int ind1, ind2;
		Movement move = new Movement();
		SortedMap<Integer, SortedMap<Integer, Movement>> moveList1, moveList2, moveList;
		moveList1 = new TreeMap<>();
		moveList2 = new TreeMap<>();
		ind1 = 0;
		ind2 = 0;
		for(int i = 0 ; i <= 10 ; i++) {
			for(int j = 0; j <= 10 ; j++) {
				if(board.getBOARD()[i][j].returnToken().isPresent()) {
					if(!(move.movementList(board.getBOARD(), i, j).isEmpty())) {
						if(board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Defender" && playerOne.getRol() == "Defender") {
							moveList1.put( ind1, move.movementList(board.getBOARD(), i, j));
							ind1++;
						}
						else if(board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Attacker" && playerOne.getRol() == "Attacker") {
							moveList1.put( ind1, move.movementList(board.getBOARD(), i, j));
							ind1++;
						}
						if(board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Defender" && playerTwo.getRol() == "Defender") {
							moveList2.put( ind2, move.movementList(board.getBOARD(), i, j));
							ind2++;
						}
						else if(board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Attacker" && playerTwo.getRol() == "Attacker") {
							moveList2.put( ind2, move.movementList(board.getBOARD(), i, j));
							ind2++;
						}
					}
				}
				if(board.getBOARD()[i][j].returnToken().isPresent()) {
					if(board.getBOARD()[i][j].returnToken().get().getType() == TokenType.King && board.getBOARD()[i][j].getType() != SquareType.Corner) {
						return true;
					}
					else if(board.getBOARD()[i][j].returnToken().get().getType() == TokenType.King && board.getBOARD()[i][j].getType() == SquareType.Corner) {
						if(playerOne.getRol() == "Defender") {
							playerOne.win();
						}
						else if (playerTwo.getRol() == "Defender") {
							playerTwo.win();
						}
						return false;
					}

				}
			}
			
		}
		if(moveList1.isEmpty()) {
			playerTwo.win();
			return false;
		}
		if(moveList2.isEmpty()) {
			playerOne.win();
			return false;
		}
		if(playerOne.getRol() == "Attacker") {
			playerOne.win();
		}
		else if (playerTwo.getRol() == "Attacker") {
			playerTwo.win();
		}
		return false;
	}
	
	public Board getBoard() {
		return board;
		
	}
	
}
