package player;

import java.util.Map;
import java.util.Scanner;

import board.Board;
import board.Movement;
import console.ConsoleInput;
import square.Position;
import square.Square;
import token.Token;

public abstract class  Player {

	protected String rol;
	protected Map<Position, ? extends Token> fichas;
	protected Board board;
    protected Square[][] BOARD;
	protected static final Scanner keyboard = new Scanner(System.in);
	protected static final ConsoleInput console = new ConsoleInput(keyboard);
	protected boolean winner;
	
	Player(String rol, Board board){
		this.rol= rol;
		this.board=board;
		BOARD = this.board.getBOARD();
		winner = false;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void win() {
		setWinner(true);
	}

	public boolean isWinner() {
		return winner;
	}

	private void setWinner(boolean winner) {
		this.winner = winner;
	}

	public abstract Movement confirmMovePiece();
}
