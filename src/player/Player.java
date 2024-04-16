package player;

import java.util.Map;

import board.Board;
import board.Movement;
import square.Position;
import token.Token;

public abstract class  Player {

	protected String rol;
	protected Map<Position, ? extends Token> fichas;
	protected Board board;
	
	Player(String rol){
		this.rol= rol;
		if (rol== "atack") {
//			fichas.putAll();
		}
		else if(rol == "defend"){
//			fichas.putAll();
		}
	}
	
	public abstract Movement confirmMovePiece();
}
