package game.engine.cells;

import game.engine.monsters.Monster;

public class Cell {
	private String name;
	private Monster monster; 
	
	public Cell(String name) {
		this.name = name; // monster is set to null by default.
	}
	
	public String getName() { return name; }
	
	public Monster getMonster() { return monster; }
	public void setMonster(Monster monster) { this.monster = monster; }
	
	public boolean isOccupied() { return monster != null; }
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		setMonster(landingMonster);
	}
}