package player;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import game.Game;
import token.Token;
/**
 * La clase Hnefatafl representa el juego en si. Su proposito es contener el main y es desde donde se ejecuta el juego.
 * 
 * @author César
 * @version 1.0
 * @since 1.0
 * @see Game
 */
public class AI extends Player {

    public AI(String rol, Board board) {
        super(rol, board);
    }

    @Override
    public Movement confirmMovePiece() {
        return simulateAI();
    }

    private Movement simulateAI() {
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

    private Movement chooseBestMove(SortedMap<Integer, Movement> moves) {
        return moves.values().stream()
                .max(Comparator.comparingInt(this::evaluateMove))
                .orElseThrow(() -> new IllegalStateException("No valid moves available"));
    }

    private int evaluateMove(Movement move) {
        // A simple evaluation function that might prioritize moves based on capturing tokens, etc.
        // For now, it just returns a random value or could analyze the board to provide a score.
        return (int) (Math.random() * 100);  // Placeholder: Random evaluation for demonstration
    }

}
