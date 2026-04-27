package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		ArrayList<Monster> remainingMonsters = new ArrayList<>(this.allMonsters);
        
        remainingMonsters.remove(this.player);
        remainingMonsters.remove(this.opponent);
        
        Board.setStationedMonsters(remainingMonsters);
        
        
        this.board.initializeBoard(DataLoader.readCells());
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}

	 private Monster getCurrentOpponent(){
		if(current==player){
			return opponent;}
		else{
			return player;
		}		
	 }

	 private int rollDice(){
		int random = (int) (Math.random()*6)+1;
		return random;
	 }

	 void usePowerup() throws OutOfEnergyException{
		if(current.getEnergy()<Constants.POWERUP_COST){
			throw new OutOfEnergyException("Insufficient Energy");
		}
		else{
			current.executePowerupEffect(getCurrentOpponent());
		}
	 }
	
	 void playTurn() throws InvalidMoveException{
		if(current.isFrozen()){
			current.setFrozen(false);

		}
		else{
			board.moveMonster(current, rollDice(), getCurrentOpponent());
		}
		switchTurn();
	 }

	 private void switchTurn(){
		current = getCurrentOpponent();
	 }

	 private boolean checkWinCondition(Monster monster){
		if(monster.getEnergy()>=1000 && monster.getPosition()==99){
			return true;
		}
		return false;
	 }

	 Monster getWinner(){
		if(checkWinCondition(player)){
			return player;
		}
		if(checkWinCondition(opponent)){
			return opponent;
		}
		else{
			return null;
		}
	 }
}