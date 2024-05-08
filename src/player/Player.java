package player;

import board.Board;
import board.Movement;
import square.Square;

public abstract class  Player {

	/**
	 * Variable que guarda el rol del jugador
	 */
	protected String rol;
	/**
	 * Variable que guarda el tablero
	 */
	protected Board board;
	/**
	 * Variable que guarda el Array que forma el tablero 
	 */
    protected Square[][] BOARD;
    /**
     * Variable que guarda si el jugador a ganado
     */
	protected boolean winner;
	/**
	 * Variable que guarda el numero de vences que el jugador a ganado
	 */
	protected int numWinned;
	
	Player(String rol, Board board){
		this.rol= rol;
		this.board=board;
		numWinned=0;
		BOARD = this.board.getBOARD();
		winner = false;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void win() {
		numWinned++;
		setWinner(true);
	}

	public int getNumWinned() {
		return numWinned;
	}
	
	public void changeToAttacker() {
		this.setRol("Attacker");
	}
	
	public void changeToDefender() {
		this.setRol("Defender");
	}

	private void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el el booleano de si el jugador es ganador.
	 * @return winner
	 */
	public boolean isWinner() {
		return winner;
	}

	private void setWinner(boolean winner) {
		this.winner = winner;
	}

	public abstract Movement confirmMovePiece();
}
