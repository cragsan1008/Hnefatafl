package player;

import square.Position;
import board.Movement;

public class Person extends Player {

	public Person(String rol) {
		super(rol);
	}

	@Override
	public Position confirmMovePiece() {
		int x, y;
		x = console.readIntInRange(0, 10);
		y = console.readIntInRange(0, 10);
		return new Position(x, y);

	}

}
