package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import clasesMod.Board;
import clasesMod.Person;
import clasesMod.Square;

class MovementTest {

	@ParameterizedTest
	@CsvSource({ "Attacker, 3, 0 , 3, 10, A", "Defender, 0, 3 , 10, 3, D", "Attacker, 3, 3 , 3, 7, A",
			"Defender, 3, 3 , 7, 3, D", "Attacker, 5, 0 , 5, 10, A", "Defender, 5, 10 , 5, 0, D",
			"Attacker, 0, 5 , 10, 5, A", "Defender, 10, 5 , 0, 5, D", "Attacker, 5, 3 , 5, 7, A",
			"Defender, 7, 5 , 2, 5, D", "Defender, 0, 5 , 0, 10, K", "Defender, 5, 0 , 10, 0, K",
			"Defender, 5, 0 , 5, 10, K", "Defender, 0, 5 , 10, 5, K","Defender, 3, 3 , 3, 7, K",
			"Defender, 3, 3 , 7, 3, K"})
	void moveTest(String rol, int x, int y, int x2, int y2, String ficha) {
		Board board = BoardCustom(x, y, ficha);
		Board expectedBoard = BoardCustom(x2, y2, "no");
		Square[][] b;
		Person person = new Person(rol, board);
		b = board.getBOARD();
		expectedBoard.getBOARD()[x2][y2].setToken(b[x][y].returnToken().get());
		board.moveCustom(person, x, y, x2, y2);

		assertEquals(expectedBoard, board);

	}

	@ParameterizedTest
	@CsvSource({ "Attacker, 5, 4 , 5, 6, A, 5, 6", "Defender, 2, 5 , 3, 5, D, 3, 5", "Attacker, 5, 3 , 5, 6, A, 5, 4",
	"Defender, 2, 5 , 6, 5, D, 4, 5", "Defender, 5, 3 , 5, 6, A, 2, 2", "Attacker, 2, 5 , 6, 3, A, 2, 2",
	"Defender, 0, 5 ,0, 10, D, 2, 2", "Attacker, 2, 5 , 5, 5, A, 2, 2", "Defender, 5, 4 , 5, 6, K, 5, 6",
	"Defender, 2, 5 , 3, 5, K, 3, 5", "Defender, 5, 3 , 5, 6, K, 5, 4", "Defender, 2, 5 , 6, 5, K, 4, 5",
	"Defender, 2, 5 , 6, 3, K, 2, 2", "Attacker, 5, 3 , 5, 6, K, 2, 2" })
	void moveWrongTest(String rol, int x, int y, int x2, int y2, String ficha, int x3, int y3) {
		Board board = BoardCustom(x, y, x3, y3, ficha);
		Board expectedBoard = BoardCustom(x, y, x3, y3, ficha);
		Square[][] b;
		Person person = new Person(rol, board);
		b = board.getBOARD();
		expectedBoard.getBOARD()[x2][y2].setToken(b[x][y].returnToken().get());
		board.moveCustom(person, x, y, x2, y2);

		assertEquals(expectedBoard, board);
		assertThrows(IllegalArgumentException.class, () -> board.moveCustom(person, x, y, x2, y2));

	}

	public Board BoardCustom(int x, int y, String ficha) {
		Square[][] board = new Square[11][11];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i == x && j == y) {
					board[i][j] = new Square(i, j, ficha);

				} else {
					board[i][j] = new Square(i, j, "no");
				}
			}
		}
		return new Board(board);
	}

	public Board BoardCustom(int x, int y, int x2, int y2, String ficha) {
		Square[][] board = new Square[11][11];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i == x && j == y) {
					board[i][j] = new Square(i, j, ficha);

				}else if (i == x2 && j == y2) {
					board[i][j] = new Square(i, j, ficha);

				}
				 else {
					board[i][j] = new Square(i, j, "no");
				}
			}
		}
		return new Board(board);
	}

}
