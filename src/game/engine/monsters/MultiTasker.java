package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role role, int energy) {
		super(name, description, role, energy); // normalSpeedTurns is set to 0 by default.
	}
	
	public int getNormalSpeedTurns() { return normalSpeedTurns; }
	public void setNormalSpeedTurns(int normalSpeedTurns) { this.normalSpeedTurns = normalSpeedTurns; }
	
	@Override
	public void move(int distance) {
		if (normalSpeedTurns > 0) {
            super.move(distance);
            normalSpeedTurns--;
        }
		else super.move(distance / 2);
	}
	@Override
	public void setEnergy(int energy){
		int change = energy - this.getEnergy();
        super.setEnergy(this.getEnergy() + change + 200);
	}
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		setNormalSpeedTurns(2);
	}
}