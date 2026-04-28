package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		// activated is set to false by default.
	}
	
	public Role getRole() { return role; }
    public int getEnergy() { return energy; }
    
	public boolean isActivated() { return activated; }
	public void setActivated(boolean activated) { this.activated = activated; }

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		
	}
}