package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() { return cellMonster; }
	
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		if(landingMonster.getRole()==getCellMonster().getRole()){
			landingMonster.executePowerupEffect(opponentMonster);
		}
		else if(landingMonster.getRole()!=getCellMonster().getRole()){
			if (landingMonster.getEnergy() > getCellMonster().getEnergy()) {
			    int LEnergy = landingMonster.getEnergy();
			    int CEnergy = getCellMonster().getEnergy();
			    getCellMonster().setEnergy(LEnergy);        
			    landingMonster.alterEnergy(CEnergy - LEnergy); 
			}
		}
	}
}