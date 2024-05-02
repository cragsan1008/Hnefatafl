package player;

import java.util.Map;
import java.util.Scanner;

import board.Board;
import board.Movement;
import console.ConsoleInput;
import square.Position;
import token.Token;

public abstract class  Player {

	protected String rol;
	protected Map<Position, ? extends Token> fichas;
	protected Board board;
	protected static final Scanner keyboard = new Scanner(System.in);
	protected static final ConsoleInput console = new ConsoleInput(keyboard);
	
	Player(String rol){
		this.rol= rol;
	}
	
	public String getRol() {
		return rol;
	}

	public abstract Position confirmMovePiece();
}
