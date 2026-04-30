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
        if (this.getRole() == monster.getRole()) {
            monster.alterEnergy(canisterValue);
        } else {
        	monster.alterEnergy(-canisterValue);
        }
    }
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		if(this.isActivated()==true){
			return;
		}
		else{
			boolean flag=false;
			int energyFirst=0;
			int energyEnd=0;
			energyFirst=landingMonster.getEnergy();
			this.modifyCanisterEnergy(landingMonster,this.getEnergy());
			energyEnd=landingMonster.getEnergy();
			if(energyFirst==energyEnd){return;}
			else if(energyFirst!=energyEnd){flag=true;}
			for(int i=0; i<Board.getStationedMonsters().size();i++){
				Monster stationed=Board.getStationedMonsters().get(i);
				if(stationed.getRole()==landingMonster.getRole()){
				energyFirst=stationed.getEnergy();
				this.modifyCanisterEnergy(stationed,this.getEnergy());
				energyEnd=stationed.getEnergy();
				if(energyFirst!=energyEnd){flag=true;}
					
				}
			}
			if(flag==true){
				this.setActivated(true);
			}
		}
	}
}