package game.engine.cells;
import game.engine.*;
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
    }

    public Role getRole() { return role; }
    public int getEnergy() { return energy; }
    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

	@Override
    public void modifyCanisterEnergy(Monster monster, int canisterValue) {
        if (this.getRole() == monster.getRole())
        	monster.alterEnergy(canisterValue);
        else
        	monster.alterEnergy(-canisterValue);
    }
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		if (isActivated()) return;
		
		boolean flag = false;
		int energyFirst = landingMonster.getEnergy();
		modifyCanisterEnergy(landingMonster, energy);
		int energyEnd = landingMonster.getEnergy();
		
		if (energyFirst == energyEnd) return;
		else if (energyFirst != energyEnd) flag = true;
		for (Monster stationedMonster : Board.getStationedMonsters()) {
			if (stationedMonster.getRole() == landingMonster.getRole()) {
				energyFirst = stationedMonster.getEnergy();
				modifyCanisterEnergy(stationedMonster, energy);
				energyEnd = stationedMonster.getEnergy();
				if(energyFirst != energyEnd) flag = true;	
			}
		}
		this.setActivated(flag);
	}
}