package player;

import square.Position;

public class Person extends Player {

	public Person(String rol) {
		super(rol);
	}

	@Override
	public Position confirmMovePiece() {
		System.out.println("Elige posicion de ficha a mover(primero x, luego y):");
		int x, y;
		x = console.readIntInRange(0, 10);
		y = console.readIntInRange(0, 10);
		return new Position(x, y);

	}

}
