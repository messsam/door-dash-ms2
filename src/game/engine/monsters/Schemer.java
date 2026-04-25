package game.engine.monsters;

import game.engine.*;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	private int stealEnergyFrom(Monster target){
		if (target.getEnergy() >= Constants.SCHEMER_STEAL) {
			target.setEnergy(target.getEnergy() - Constants.SCHEMER_STEAL);
			return Constants.SCHEMER_STEAL;
		}
		else {
			int stolen=target.getEnergy();
			target.setEnergy(target.getEnergy() - target.getEnergy());
			return stolen;
		}
	}
	@Override
	public void setEnergy(int energy){
		int change = energy - this.getEnergy();
        super.setEnergy(this.getEnergy() + change + 10);
	}
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		int total=0;
		total+=stealEnergyFrom(opponentMonster);
		for (int i=0;i<Board.getStationedMonsters().size();i++){
			total+=stealEnergyFrom((Monster)Board.getStationedMonsters().get(i));
		}
		this.setEnergy(total+getEnergy());
	}
}