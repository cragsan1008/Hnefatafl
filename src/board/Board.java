package board;

import static board.Colors.BLACK_BACKGROUND;
import static board.Colors.GREEN_BACKGROUND;
import static board.Colors.RESET;

import java.util.Map;

import square.Position;
import square.Square;
import token.Token;

public class Board {
	
	private Square[][] board = new Square[11][11]; 
	private Map<Position, Token> atack;
	private Map<Position, Token> defend;
	
	private void initiateAtack(){
	
//		for(int i =0; i<board.size; i++) {
//			atack.put(new(board.getY, board.getX ), new());
//		}
	}
	
	private void initiateDefend(){
		
//		defend.put(new(3,6), new());
		
	}
	
	public void draw() {
		for (int i= 0; i< board.length; i++){
			for (int j= 0; j< board[i].length; j++){
				if( (i == 0 && j == 0)||(i == 0 && j == 10)||(i == 10 && j == 0)||(i == 10 && j == 10)) {
					System.out.printf("|%s   %s| ", BLACK_BACKGROUND, RESET);
				}
				else {
					System.out.printf("|%s   %s| ", GREEN_BACKGROUND, RESET);
				}
				
			}
			System.out.printf("\n\n");
		}
	}
	public void show() {
		draw();
	}
	
	public static void main() {
		new Board().show();
	}
}
