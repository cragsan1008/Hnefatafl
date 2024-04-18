package board;

import square.Square;

public class Board {
	
	private final Square[][] BOARD = new Square[11][11]; 
	
	public Board(){
		for (int i= 0; i< BOARD.length; i++){
			for (int j= 0; j< BOARD[i].length; j++){
				this.BOARD[i][j]=new Square(i , j);	
			}
		}
	}
	
	public void draw() {
		for (int i= 0; i< BOARD.length; i++){
			
			if(i == 0) {
				System.out.println("    1    2   3   4    5   6   7    8   9   10  11");
			}
			
			for (int j= -1; j< BOARD[i].length; j++){
				if(j != -1) {
					System.out.printf("%s",BOARD[i][j].draw());
					}
				else if(j == -1) {
					if(i==10) {
						System.out.printf("%s%-3s","\u001B[1m", i+1);
					}
					else {
						System.out.printf("%s%-3s","\u001B[1m", i+1);
					}
				}
				if(j == 10) {
					if(i==10) {
						System.out.printf("%s%3s","\u001B[1m", i+1);
					}
					else {
						System.out.printf("%s%3s","\u001B[1m", i+1);
					}
				}
				
			}
			if(i == 10) {
				System.out.println("");
				System.out.println("    1    2   3   4    5   6   7    8   9   10  11");
			}
			System.out.println("\n");
		}
	}
	
}
