package player;

import java.util.Optional;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import console.ConsoleInput;
import square.Position;
import token.Token;

/**
 * La clase Person representa a un jugador manejado por una persona. Su
 * proposito es contener los metodos para pedir al usuario el movimiento.
 * 
 * @author César
 * @version 2.0
 * @since 1.0
 * @see Player
 */
public class Person extends Player {

	/**
	 * Constructor de la clase Person
	 * 
	 * @param rol
	 * @param Board
	 */
	public Person(String rol, Board Board) {
		super(rol, Board);
	}

	/**
	 * Metodo heredado de padre encargado de pedir al usuario un movimiento, aqui el
	 * usuario introduce la primera parte del movimineto la casilla de origen
	 * 
	 * @see Player#confirmMovePiece()
	 * @return movimiento
	 */
	@Override
	public Movement confirmMovePiece() {
		int x, y;
		Movement move;
		Position p;
		Optional<Token> opTok;
		SortedMap<Integer, Movement> moveList;
		moveList = new TreeMap<>();
		Scanner keyboard = new Scanner(System.in);
		ConsoleInput console = new ConsoleInput(keyboard);

		System.out.println("Elige posicion de ficha a mover(primero x, luego y):");
		x = console.readIntInRange(0, 10);
		y = console.readIntInRange(0, 10);
		opTok = BOARD[x][y].returnToken();
		if (opTok.isEmpty()) {
			System.out.println("Casilla vacia");
			return confirmMovePiece();
		} else {
			if (opTok.get().getType().getRol() != getRol()) {
				System.out.println("Ficha del rival");
				return confirmMovePiece();
			} else {
				move = new Movement(BOARD[x][y]);
				moveList = move.movementList(BOARD, x, y);

				if (!moveList.isEmpty()) {
					board.drawPosibleMove(moveList);
					p = secondMove(x, y, moveList);
					return new Movement(BOARD[x][y], BOARD[p.getX()][p.getY()]);
				} else {
					System.out.println("Ficha sin movimientos validos");
					return confirmMovePiece();
				}
			}
		}
	}

	/**
	 * 
	 * Metodo encargado de pedir al usuario una posicion, aqui el usuario introduce
	 * la segunda parte del movimineto la casilla de destino
	 * 
	 * @return posicion
	 */
	private Position secondMove(int x, int y, SortedMap<Integer, Movement> moveList) {
		boolean valid;
		int x2, y2;
		Scanner keyboard = new Scanner(System.in);
		ConsoleInput console = new ConsoleInput(keyboard);

		x2 = console.readIntInRange(0, 10);
		y2 = console.readIntInRange(0, 10);

		valid = moveConfirm(x, y, x2, y2, moveList);

		if (valid) {
			return new Position(x2, y2);
		} else {
			System.out.println("Casilla de destino no valida");
			return secondMove(x, y, moveList);
		}

	}

	/**
	 * Metodo encargado de confirmar si un movimiento es valido
	 * 
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param moveList
	 * @return True o false dependiendo de si es valido o no
	 */
	private boolean moveConfirm(int x, int y, int x2, int y2, SortedMap<Integer, Movement> moveList) {
		for (Movement movement : moveList.values()) {
			if (movement.equals(new Movement(BOARD[x][y], BOARD[x2][y2]))) {
				return true;
			}
		}
		return false;
	}

}
