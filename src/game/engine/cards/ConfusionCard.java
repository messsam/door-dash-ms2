package game.engine.cards;
import game.engine.*;
import game.engine.monsters.Monster;

public class ConfusionCard extends Card {
	private int duration;
	
	public ConfusionCard(String name, String description, int rarity, int duration) {
		super(name, description, rarity, false);
		this.duration = duration;
	}
	
	public int getDuration() { return duration; }
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		Role r1 = player.getRole();
		Role r2 = opponent.getRole();
		player.setRole(r2);
		opponent.setRole(r1);
		player.setConfusionTurns(getDuration());
		opponent.setConfusionTurns(getDuration());
	}
}