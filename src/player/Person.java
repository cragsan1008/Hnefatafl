package player;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import square.Position;
import token.Token;

public class Person extends Player {

	   public Person(String rol, Board Board) {
	        super(rol, Board);
	    }


	@Override
	public Movement confirmMovePiece() {
		int x, y, x2, y2;
		Movement move;
		Position p;
		Optional<Token> opTok;
		SortedMap<Integer, Movement> moveList;
		moveList = new TreeMap<>();


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
				} 
				else {
					System.out.println("Ficha sin movimientos validos");
					return confirmMovePiece();
				}
			}
		}
	}
	
	private Position secondMove(int x, int y, SortedMap<Integer, Movement> moveList) {
		boolean valid;
		int x2, y2;
		Position position;
				
		x2 = console.readIntInRange(0, 10);
		y2 = console.readIntInRange(0, 10);
		
		valid = moveConfirm(x, y, x2, y2, moveList);
		
		if (valid) {
			return position = new Position(x2, y2);
		} 
		else {
			return secondMove(x, y, moveList);
		}

	}
	
	private boolean moveConfirm(int x, int y, int x2, int y2, SortedMap<Integer, Movement> moveList) {
		for (Movement movement : moveList.values()) {
			if (movement.equals(new Movement(BOARD[x][y], BOARD[x2][y2]))) {
				return true;
			}
		}
		return false;
	}

}
