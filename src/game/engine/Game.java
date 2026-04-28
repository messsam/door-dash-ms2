package game.engine;

import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;

import game.engine.monsters.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player, opponent, current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER? Role.LAUGHER: Role.SCARER);
		this.current = player;
		
        allMonsters.remove(this.player);
        allMonsters.remove(this.opponent);
        Board.setStationedMonsters(allMonsters);
        
        this.board.initializeBoard(DataLoader.readCells());
	}
	
    public Board getBoard() { return board; }
    public ArrayList<Monster> getAllMonsters() { return allMonsters; }
    public Monster getPlayer() { return player; }
    public Monster getOpponent() { return opponent; }
    public Monster getCurrent() { return current; }
    public void setCurrent(Monster current) { this.current = current; }
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	
	private Monster getCurrentOpponent() {
		return current == player? opponent: player;	
	}
	
	public void usePowerup() throws OutOfEnergyException {
		if(current.getEnergy() < Constants.POWERUP_COST)
			throw new OutOfEnergyException("Insufficient Energy");
		else {
			current.executePowerupEffect(getCurrentOpponent());
			current.alterEnergy(-500);
		}
	}
	
	public void playTurn() throws InvalidMoveException {
		if(current.isFrozen()) current.setFrozen(false);
		else board.moveMonster(current, rollDice(), getCurrentOpponent());
		switchTurn();
	}
	private void switchTurn(){
		current = getCurrentOpponent();
	}
	private int rollDice() {
		return (int) (Math.random()*6)+1;
	}
	
	private boolean checkWinCondition(Monster monster) {
		return monster.getPosition() == 99 && monster.getEnergy() >= 1000;
	}
	public Monster getWinner(){
		if (checkWinCondition(player)) return player;
		if (checkWinCondition(opponent)) return opponent;
		return null;
	}
}