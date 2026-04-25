package game.engine.cells;

import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;

public class ContaminationSock extends TransportCell implements CanisterModifier {
	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		
	}
}