package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public abstract class Monster implements Comparable<Monster> {
	private String name, description;
	private Role originalRole; // For confusion card.
	private Role role;
	private int energy;
	private int position;
	private int confusionTurns;
	private boolean frozen, shielded;
	
	public Monster(String name, String description, Role originalRole, int energy) {
		super();
        setEnergy(energy);
        this.name = name;
        this.description = description;
        this.originalRole = this.role = originalRole;
        // position and confusionTurns are set to 0 by default.
        // frozen and shielded are set to false by default.
	}

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Role getOriginalRole() { return originalRole; }
    
	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }
	
    public int getEnergy() { return energy; }
	public void setEnergy(int energy) { this.energy = Math.max(Constants.MIN_ENERGY, energy); }
	
    public int getPosition() { return position; }
	public void setPosition(int position) { this.position = position % Constants.BOARD_SIZE; }
	
    public int getConfusionTurns() { return confusionTurns; }
	public void setConfusionTurns(int confusionTurns) { this.confusionTurns = confusionTurns; }
	
    public boolean isFrozen() { return frozen; }
	public void setFrozen(boolean frozen) { this.frozen = frozen; }
	
    public boolean isShielded() { return shielded; }
	public void setShielded(boolean shielded) { this.shielded = shielded; }
	
	public boolean isConfused() { return confusionTurns != 0; }
	
	public void move(int distance) {
		this.setPosition(this.getPosition() + distance);
	}
	
	public final void alterEnergy(int energy) {
		if (this.isShielded()==true && energy < 0){ 
			this.setShielded(false);}
		else {
			this.setEnergy(this.getEnergy()+energy);
		}
			
	}
	
	public void decrementConfusion() {
		if (this.confusionTurns > 0) this.confusionTurns--;
		if (this.confusionTurns == 0) this.role = this.originalRole;
	}
	
	@Override
	public int compareTo(Monster other) { return this.position - other.position; }
	
	public abstract void executePowerupEffect(Monster opponentMonster);
}