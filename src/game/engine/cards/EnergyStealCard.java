package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;
import game.engine.monsters.Dasher;

public class EnergyStealCard extends Card implements CanisterModifier {
	private int energy;

	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy = energy;
	}
	
	public int getEnergy() { return energy; }
	
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	}
	@Override
	public void performAction(Monster player, Monster opponent) {
		if (player instanceof Dasher || !opponent.isShielded()) {
			int stolenEnergy = Math.min(energy,  opponent.getEnergy());
			modifyCanisterEnergy(player, stolenEnergy);
			modifyCanisterEnergy(opponent, -1 * stolenEnergy);
		}
		else opponent.setShielded(false);
	}
}