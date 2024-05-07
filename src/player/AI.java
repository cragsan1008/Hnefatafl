package player;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import board.Board;
import board.Movement;
import square.Position;
import square.Square;
import token.Token;
import token.TokenType;

public class AI extends Player {


    public AI(String rol, Board Board) {
        super(rol, Board);
    }

    @Override
    public Position confirmMovePiece() {
        return simulateAI();
    }

    private Position simulateAI() {
        Square[][] originalBoard = cloneBoard(BOARD);
        try {
            SortedMap<Integer, SortedMap<Integer, Movement>> listOfMoveList = new TreeMap<>();
            for (int i = 0; i < BOARD.length; i++) {
                for (int j = 0; j < BOARD[i].length; j++) {
                    Optional<Token> token = BOARD[i][j].returnToken();
                    if (token.isPresent() && token.get().getType().getRol().equals(rol)) {
                        Movement move = new Movement(BOARD[i][j]);
                        listOfMoveList.put(i * BOARD.length + j, move.movementList(BOARD, i, j));
                    }
                }
            }
            Movement move = findBetter(listOfMoveList);
            return move;
        } finally {
            restoreBoard(BOARD, originalBoard);
        }
    }

    private Movement findBetter(SortedMap<Integer, SortedMap<Integer, Movement>> listOfMove) {
        return listOfMove.values().stream()
                .flatMap(moveMap -> moveMap.values().stream())
                .max(Comparator.comparingInt(move -> evaluateMove(move, 2))) // Evaluate up to two moves ahead
                .orElse(this::random); // Handle case where no moves are possible
    }

    private Movement random(SortedMap<Integer, SortedMap<Integer, Movement>> listOfMove) {
        return listOfMove.values().stream()
                .flatMap(moveMap -> moveMap.values().stream()).random // Evaluate up to two moves ahead
                .orElse(); // Handle case where no moves are possible
    }
    
    private int evaluateMove(Movement move, int depth) {
        if (depth == 0) {
            return checkMoveKill(move.getSquareO().getPosition().getX(), move.getSquareO().getPosition().getY(),
                                 move.getSquareD().getPosition().getX(), move.getSquareD().getPosition().getY());
        } else {
            simulateMove(move);
            int score = -evaluateOpponentMoves();
            undoMove(move);
            return score;
        }
    }

    private void simulateMove(Movement move) {
        Token token = move.getSquareO().returnToken().get();
        move.getSquareD().setToken(token);
        move.getSquareO().setToken(null);
    }

    private void undoMove(Movement move) {
        move.getSquareO().setToken(move.getSquareD().returnToken().get());
        move.getSquareD().setToken(null);
    }

    private int evaluateOpponentMoves() {
        // Logic to evaluate opponent moves. Placeholder for the demonstration.
        return 0;
    }

    private int checkMoveKill(int x, int y, int nx, int ny) {
        int captures = 0;
        int dx, dy, nx2, ny2 ;
        Optional<Token> currentToken, neighborToken, nextNeighborToken 
        // Directions to check (north, south, east, west)
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            dx = dir[0];
            dy = dir[1];
            nx2 = nx + dx;
            ny2 = ny + dy;

            // Check board boundaries
            if (nx2 >= 0 && nx2 < BOARD.length && ny2 >= 0 && ny2 < BOARD[nx2].length) {
                currentToken = BOARD[x][y].returnToken();
                neighborToken = BOARD[nx][ny].returnToken();
               nextNeighborToken = BOARD[nx2][ny2].returnToken();

                // Check that tokens exist and are of different types
                if (currentToken.isPresent() && neighborToken.isPresent() && nextNeighborToken.isPresent() &&
                    neighborToken.get().getType() != currentToken.get().getType() &&
                    nextNeighborToken.get().getType() == currentToken.get().getType()) {

                    // Check that the central token is not the king if the current token is a defender
                    if (!(currentToken.get().getType() == TokenType.Defender && neighborToken.get().getType() == TokenType.King)) {
                        captures++;  // Simulate "kill" action
                    }
                }
            }
        }
        return captures;
    }

    // Helper method to clone the board
    private Square[][] cloneBoard(Square[][] original) {
        Square[][] clone = new Square[original.length][];
        for (int i = 0; i < original.length; i++) {
            clone[i] = new Square[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                clone[i][j] = new Square(original[i][j]);
            }
        }
        return clone;
    }

    // Helper method to restore the board
    private void restoreBoard(Square[][] original, Square[][] restore) {
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                original[i][j].setToken(restore[i][j].returnToken().orElse(null));
            }
        }
    }
}
