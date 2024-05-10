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

/**
 * La clase Game representa una partida. Su proposito es empezar las partidas y
 * reiniciar la partida. Proporciona metodos para empezar la partida y reiniciar
 * la partida.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Board
 */
public class Game {

	/**
	 * Variable que guarda el tablero
	 */
	private Board board;
	/**
	 * Variable que guarda el jugador 1
	 */
	private Player playerOne;
	/**
	 * Variable que guarda el jugador 2
	 */
	private Player playerTwo;
		

	/**
	 * Constructor del juego donde nos dan opciones tal como elegir los jugadores y
	 * sus roles
	 */
	Game() {
		int time;
		Scanner keyboard = new Scanner(System.in);
		ConsoleInput console = new ConsoleInput(keyboard);
		board = new Board();
		System.out.println("Elige:\n1.1 vs 1\n2.1 vs IA\n3.IA vs IA");
		switch (console.readIntInRange(1, 3)) {
		case 1 -> {
			System.out.println("Elige rol de el jugador 1, el dos recibira el rol contrario:\n1.Defensor\n2.Atacante");
			switch (console.readIntInRange(1, 2)) {
			case 1 -> {
				playerOne = new Person("Defender", board);
				playerTwo = new Person("Attacker", board);
			}
			case 2 -> {
				playerOne = new Person("Attacker", board);
				playerTwo = new Person("Defender", board);
			}
			}
		}
		case 2 -> {
			System.out.println("Elige rol de el jugador, la IA recibira el rol contrario:\n1.Defensor\n2.Atacante");
			switch (console.readIntInRange(1, 2)) {
			case 1 -> {
				playerOne = new Person("Defender", board);
				System.out.println("Escribe la velocidad de la IA en milisegundos:");
				playerTwo = new AI("Attacker", board, console.readIntInRange(0, 2000));
			}
			case 2 -> {
				playerOne = new Person("Attacker", board);
				System.out.println("Escribe la velocidad de la IA en milisegundos:");
				playerTwo = new AI("Defender", board, console.readIntInRange(0, 2000));
			}
			}
		}
		case 3 -> {
			System.out.println("Elige rol de la IA1, la IA2 recibira el rol contrario:\n1.Defensor\n2.Atacante");
			switch (console.readIntInRange(1, 2)) {
			case 1 -> {
				System.out.println("Escribe la velocidad de la IA en milisegundos:");
				time = console.readIntInRange(0, 2000);
				playerOne = new AI("Defender", board, time);
				playerTwo = new AI("Attacker", board, time);
			}
			case 2 -> {
				System.out.println("Escribe la velocidad de la IA en milisegundos:");
				time = console.readIntInRange(0, 2000);
				playerOne = new AI("Attacker", board, time);
				playerTwo = new AI("Defender", board, time);
			}
			}
		}
		default -> {
			System.out.println("Elección invalida");
			new Game();
			return;
		}

		}
	}

	/**
	 * Metodo que define los turnos en la partida y empieza esta
	 */
	public void start() {
		boolean unfineshed;
		int initial = 0;
		unfineshed = true;
		while (unfineshed) {
			if (initial == 0) {
				board.draw();
				initial++;
			}
			unfineshed = checkBoard();
			if (unfineshed) {
				System.out.println("Turno del jugador 1:");
				board.move(playerOne);
				unfineshed = checkBoard();
			}
			if (unfineshed) {
				System.out.println("Turno del jugador 2:");
				board.move(playerTwo);
			}
			if (playerOne.isWinner()) {
				System.out.printf("Gana el jugador 1 \nHa ganado %d %s\n", playerOne.getNumWinned(),
						playerOne.getNumWinned() > 1 ? "partidas" : "partida");
			} else if (playerTwo.isWinner()) {
				System.out.printf("Gana el jugador 2 \nHa ganado %d %s\n", playerTwo.getNumWinned(),
						playerTwo.getNumWinned() > 1 ? "partidas" : "partida");

			}
		}
		restart();
	}

	/**
	 * Metodo que reinicia el Tablero y permite cambiar de rol a los jugadores
	 */
	private void restart() {
		Scanner keyboard = new Scanner(System.in);
		ConsoleInput console = new ConsoleInput(keyboard);
		board = new Board();
		System.out.println("Elige: \n1.Elegir Roles \n2.Mismos roles \n3.No jugar más");
		switch (console.readIntInRange(1, 3)) {
		case 1 -> {
			System.out.println("Elige rol de el jugador 1, el dos recibira el rol contrario:\n1.Defensor\n2.Atacante");
			switch (console.readIntInRange(1, 2)) {
			case 1 -> {
				playerOne.changeToDefender();
				playerTwo.changeToAttacker();
			}
			case 2 -> {
				playerOne.changeToAttacker();
				playerTwo.changeToDefender();
			}
			}
		}
		case 2 -> {
			System.out.println("Sin cambio de roles");
		}
		case 3 -> {
			return;
		}
		default -> {
			System.out.println("Elección invalida");
			restart();
			return;
		}
		}
		board = new Board();
		playerOne.resetWin();
		playerTwo.resetWin();
		start();

	}

	/**
	 * Metodo que comprueba el tablero para saber si la partida ha acabado
	 * 
	 * @return booleano con el estado de la partida
	 */
	private boolean checkBoard() {
		int ind1, ind2;
		Movement move = new Movement();
		SortedMap<Integer, SortedMap<Integer, Movement>> moveList1, moveList2;
		moveList1 = new TreeMap<>();
		moveList2 = new TreeMap<>();
		ind1 = 0;
		ind2 = 0;
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				if (board.getBOARD()[i][j].returnToken().isPresent()) {
					if (!(move.movementList(board.getBOARD(), i, j).isEmpty())) {
						if (board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Defender"
								&& playerOne.getRol() == "Defender") {
							moveList1.put(ind1, move.movementList(board.getBOARD(), i, j));
							ind1++;
						} else if (board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Attacker"
								&& playerOne.getRol() == "Attacker") {
							moveList1.put(ind1, move.movementList(board.getBOARD(), i, j));
							ind1++;
						}
						if (board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Defender"
								&& playerTwo.getRol() == "Defender") {
							moveList2.put(ind2, move.movementList(board.getBOARD(), i, j));
							ind2++;
						} else if (board.getBOARD()[i][j].returnToken().get().getType().getRol() == "Attacker"
								&& playerTwo.getRol() == "Attacker") {
							moveList2.put(ind2, move.movementList(board.getBOARD(), i, j));
							ind2++;
						}
					}

				}
				if (i == 10 && j == 10) {
					if (moveList1.isEmpty()) {
						playerTwo.win();
						return false;
					}
					if (moveList2.isEmpty()) {
						playerOne.win();
						return false;
					}
				}
			}
		}
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {

				if (board.getBOARD()[i][j].returnToken().isPresent()) {
					if (board.getBOARD()[i][j].returnToken().get().getType() == TokenType.King
							&& board.getBOARD()[i][j].getType() != SquareType.Corner) {
						return true;
					} else if (board.getBOARD()[i][j].returnToken().get().getType() == TokenType.King
							&& board.getBOARD()[i][j].getType() == SquareType.Corner) {
						if (playerOne.getRol() == "Defender") {
							playerOne.win();
							return false;
						} else if (playerTwo.getRol() == "Defender") {
							playerTwo.win();
							return false;

						}
						return false;
					}

				}
			}

		}
		if (playerOne.getRol() == "Attacker") {
			playerOne.win();
			return false;

		} else if (playerTwo.getRol() == "Attacker") {
			playerTwo.win();
			return false;

		}
		return false;
	}

}
