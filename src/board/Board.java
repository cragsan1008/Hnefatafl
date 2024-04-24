package board;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import console.ConsoleInput;
import square.Square;
import token.Token;

/**
 * La clase Board representa un tablero. Su proposito es contruir y almacenar el tablero, 
 * permitiendonos recojer la informacion sobre las casillas en un mismo objeto.
 * Proporciona metodos para la creacion del tablero y pintado de este.
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Square
 */

public class Board {
	
	
	/**
	 * Array de casillas que conforman el tablero.
	 */
	private final Square[][] BOARD = new Square[11][11];
	/**
	 * Constante que almacena el valor para representar el texto en negrita.
	 */
	private static final String BOLD = "\u001B[1m";
	
	/**
	* Constructor de la clase Board, no necesita parametros.
	*/
	public Board(){
		for (int i= 0; i< BOARD.length; i++){
			for (int j= 0; j< BOARD[i].length; j++){
				this.BOARD[i][j]=new Square(i , j);	
			}
		}
	}
	
	/**
	 * Metodo que sirve para dibujar el tablero con todos sus componentes como las fichas.
	 * 
	 * @see Square
	 */
	public void draw(){
		for (int i= 0; i< BOARD.length; i++){
			
			if(i == 0) {
				System.out.printf("%s    1    2   3   4    5   6   7    8   9   10  11\n", BOLD);
			}
			
			for (int j= -1; j< BOARD[i].length; j++){
				if(j != -1) {
					System.out.printf("%s",BOARD[i][j].draw());
					}
				else if(j == -1) {
					if(i==10) {
						System.out.printf("%s%-3s", BOLD, i+1);
					}
					else {
						System.out.printf("%s%-3s", BOLD, i+1);
					}
				}
				if(j == 10) {
					if(i==10) {
						System.out.printf("%s%3s", BOLD, i+1);
					}
					else {
						System.out.printf("%s%3s", BOLD, i+1);
					}
				}
				
			}
			if(i == 10) {
				System.out.println("");
				System.out.printf("%s    1    2   3   4    5   6   7    8   9   10  11\n", BOLD);
			}
			System.out.println("\n");
		}
	}
	
	public void move(ConsoleInput console){
		int x , y, x2, y2;
		boolean valid;
		Optional<Token> opTok;
		Movement movement;	
		SortedMap<Integer ,Movement> moveList;
		moveList = new TreeMap<>();
		
		System.out.println("Elige posicion de ficha a mover:");
		x = console.readIntInRange(0, 11);
		y = console.readIntInRange(0, 11);
		opTok = BOARD[x][y].returnToken();

		
		if(opTok.isEmpty()){
			System.out.println("Casilla vacia");
			move(console);
		}
		else {
			movement = new Movement(BOARD[x][y]);
			moveList = movement.validateMove(BOARD, x , y);
			System.out.println("Elige posicion a mover:");
			x2 = console.readIntInRange(0, 11);
			y2 = console.readIntInRange(0, 11);
			valid = moveConfirm(x, y, x2, y2, moveList);
			if (valid){
				BOARD[x][y].setToken(null);
				BOARD[x2][y2].setToken(opTok.get());
			}
		}
	}
	
	private boolean moveConfirm(int x , int y, int x2, int y2, SortedMap<Integer ,Movement> moveList) {
		for (Movement movement : moveList.values()) {
	        if (movement.equals(new Movement(BOARD[x][y], BOARD[x2][y2]))) {
	            return true;
	        }
	    }
	    return false;
	}
	
}





















