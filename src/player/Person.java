package player;

import board.Movement;

public class Person extends Player {

	protected Person(String rol) {
		super(rol);
	}

	@Override
	public Movement confirmMovePiece() {
		return null;
	}

}
