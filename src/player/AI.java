package player;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import token.Token;

/**
 * La clase AI representa a un jugador manejado por la Inteligencia Articifical.
 * Su proposito es contener los metodos para calcular el movimiento.
 * 
 * @author César
 * @version 2.0
 * @since 1.0
 * @see Player
 */
public class AI extends Player {

	/**
	 * ^ Constructor de la clase AI
	 * 
	 * @param rol
	 * @param board
	 */
	public AI(String rol, Board board) {
		super(rol, board);
	}

	/**
	 * Metodo heredado de padre, contiene todos los movimientos posibles del
	 * jugador, los cuales manda a otro metodo para obtener uno de estos
	 * 
	 * @return Movimiento elegido por la IA
	 * @see Player#confirmMovePiece()
	 */
	@Override
	public Movement confirmMovePiece() {
		Movement move;
		Optional<Token> token;
		SortedMap<Integer, Movement> moveOptions = new TreeMap<>();
		SortedMap<Integer, Movement> moves;
		for (int i = 0; i < BOARD.length; i++) {
			for (int j = 0; j < BOARD[i].length; j++) {
				token = BOARD[i][j].returnToken();
				if (token.isPresent() && token.get().getType().getRol().equals(rol)) {
					move = new Movement(BOARD[i][j]);
					moves = move.movementList(BOARD, i, j);
					moveOptions.putAll(moves);
				}
			}
		}
		return chooseBestMove(moveOptions);
	}

	/**
	 * Metodo que opera sobre la lista de movimientos pasada eligiendo un movimiento
	 * a traves de una comparación o lanza una excepción
	 * 
	 * @param moves
	 * @return Movimiento elegido
	 */
	private Movement chooseBestMove(SortedMap<Integer, Movement> moves) {
		return moves.values().stream().max(Comparator.comparingInt(this::evaluateMove))
				.orElseThrow(() -> new IllegalStateException("Sin movimientos validos"));
	}

	/**
	 * Metodo que devuelve un Random
	 * 
	 * @param move
	 * @return Numero random
	 */
	private int evaluateMove(Movement move) {

		return (int) (Math.random() * 100);
	}

}
