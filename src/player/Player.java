package player;

import board.Board;
import board.Movement;
import square.Square;

/**
 * La clase Player representa a un jugador. Su proposito es almacenar datos
 * sobre el jugador.
 * 
 * @author César
 * @version 2.0
 * @since 1.0
 * @see AI
 * @see Person
 */
public abstract class Player {

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
	 * Variable que guarda el numero de veces que el jugador a ganado
	 */
	protected int numWinned;

	/**
	 * Constructor de la clase Player
	 * 
	 * @param rol
	 * @param board
	 */
	protected Player(String rol, Board board) {
		this.rol = rol;
		this.board = board;
		numWinned = 0;
		BOARD = this.board.getBOARD();
		winner = false;
	}

	/**
	 * Obtiene el rol del jugador
	 * 
	 * @return rol del jugador
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Metodo que cambia el atributo winner a true y sube el contador de partidas
	 * ganadas
	 */
	public void win() {
		numWinned++;
		setWinner(true);
	}

	/**
	 * Metodo que cambia el atributo winner a false
	 */
	public void resetWin() {
		setWinner(false);

	}

	/**
	 * Obtiene el numero de partidas ganadas
	 * 
	 * @return numero de partidas ganadas
	 */
	public int getNumWinned() {
		return numWinned;
	}

	/**
	 * Cambia el rol del jugador a atacante
	 */
	public void changeToAttacker() {
		this.setRol("Attacker");
	}

	/**
	 * Cambia el rol del jugador a defensor
	 */
	public void changeToDefender() {
		this.setRol("Defender");
	}

	/**
	 * Establece el rol del jugador
	 * 
	 * @param rol rol del jugador
	 */
	private void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el el booleano de si el jugador es ganador.
	 * 
	 * @return winner
	 */
	public boolean isWinner() {
		return winner;
	}

	/**
	 * Establece si el jugador es el ganador
	 * 
	 * @param winner ganador
	 */
	private void setWinner(boolean winner) {
		this.winner = winner;
	}

	public Board getBoard() {
		return board;
	}

	public void resetBoard(Board board) {
		this.board = board;
		this.BOARD = board.getBOARD();
	}

	/**
	 * Metodo abtracto se implememtea en los hijos.
	 * 
	 * @see Person#confirmMovePiece()
	 * @see AI#confirmMovePiece()
	 */
	public abstract Movement confirmMovePiece();
}
