package game.engine.cells;

import game.engine.Constants;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;

public class ContaminationSock extends TransportCell implements CanisterModifier {
	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	}
	
	@Override
	public void transport(Monster monster){
		super.transport(monster);
		modifyCanisterEnergy(monster, -1 * Constants.SLIP_PENALTY);
	}
}