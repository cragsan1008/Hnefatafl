package board;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import square.Square;
import square.SquareType;
import token.TokenType;

public class Movement {

	private Square squareO;
	private Square squareD;
	private SortedMap<Integer, Movement> moValid = new TreeMap<>();

	public Movement(Square squareO) {
		this.squareO = squareO;
	}

	public Movement(Square squareO, Square squareD) {
		this.squareO = squareO;
		this.squareD = squareD;

	}

	public Movement() {
	}

	public SortedMap<Integer, Movement> movementList(Square[][] BOARD, int x, int y) {
		int index = 0;
		boolean stay;
		squareO = BOARD[x][y];

		stay = true;
		for (int i = x - 1; i >= 0 && stay; i--) {
			if (BOARD[i][y].returnToken().isEmpty() &&( BOARD[i][y].getType() == SquareType.Normal || BOARD[x][y].returnToken().get().getType() == TokenType.King  )) {
				moValid.put(index, new Movement(this.squareO, BOARD[i][y]));
				index++;
			}
			else if(BOARD[i][y].returnToken().isEmpty() && BOARD[i][y].getType() == SquareType.Throne) {
				stay = true;
			}
			else {
				stay = false;
			}
		}
		stay = true;
		for (int i = x + 1; i <= 10 && stay; i++) {
			if (BOARD[i][y].returnToken().isEmpty() &&( BOARD[i][y].getType() == SquareType.Normal || BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[i][y]));
				index++;
			}
			else if(BOARD[i][y].returnToken().isEmpty() && BOARD[i][y].getType() == SquareType.Throne) {
				stay = true;
			} 
			else {
				stay = false;
			}
		}
		stay = true;
		for (int i = y - 1; i >= 0 && stay; i--) {
			if (BOARD[x][i].returnToken().isEmpty() &&( BOARD[x][i].getType() == SquareType.Normal || BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[x][i]));
				index++;
			}
			else if(BOARD[x][i].returnToken().isEmpty() && BOARD[x][i].getType() == SquareType.Throne) {
				stay = true;
			} 
			else {
				stay = false;
			}
		}
		stay = true;
		for (int i = y + 1; i <= 10 && stay; i++) {
			if (BOARD[x][i].returnToken().isEmpty() &&( BOARD[x][i].getType() == SquareType.Normal || BOARD[x][y].returnToken().get().getType() == TokenType.King)) {
				moValid.put(index, new Movement(this.squareO, BOARD[x][i]));
				index++;
			}
			else if(BOARD[x][i].returnToken().isEmpty() && BOARD[x][i].getType() == SquareType.Throne) {
				stay = true;
			}  
			else {
				stay = false;
			}
		}
		return moValid;
	}

	public Square getSquareD() {
		return squareD;
	}
	
	public Square getSquareO() {
		return squareO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(squareD, squareO);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		return Objects.equals(squareD, other.squareD) && Objects.equals(squareO, other.squareO);
	}

//	private Direction moveDirection(){
//		String direction;
//		System.out.println("Elige una direción en la que mover la ficha");
//		direction = console.readString();
//		if(Direction.valueOf(direction)==Direction.Down) {
//			return Direction.Down;
//		}
//		else if(Direction.valueOf(direction)==Direction.Up) {
//			return Direction.Up;
//		}
//		else if(Direction.valueOf(direction)==Direction.Left) {
//			return Direction.Left;
//		}
//		else if(Direction.valueOf(direction)==Direction.Right) {
//			return Direction.Right;
//		}
//		else {
//			System.out.println("No es una direccion");
//			return moveDirection();
//		}
//	}
//
//	private int numMove(Direction direction) {
//		int p;
//		System.out.println("Elige numero de casillas a mover:");
//		p = console.readIntInRange(1, 10);
//		if(direction.getAxis() == 'x') {
//			if( squareO.getPosition().getX() - p < 0 || squareO.getPosition().getX() + p > 11) {
//				System.out.println("Movimiento fuera de rango valido");
//				p = numMove(direction);
//			}
//		}
//		else if(direction.getAxis() == 'y') {
//			if( squareO.getPosition().getY() - p < 0 || squareO.getPosition().getY() + p > 11) {			
//				System.out.println("Movimiento fuera de rango valido");
//				p = numMove(direction);
//			}
//		}
//		return p;
//	}
}
