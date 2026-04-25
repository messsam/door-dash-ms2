package game.engine;

import static java.util.Collections.shuffle;
import java.util.ArrayList;

import game.engine.cells.*;
import game.engine.cards.Card;
import game.engine.monsters.Monster;
import game.engine.exceptions.InvalidMoveException;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		cards = new ArrayList<Card>();
		originalCards = readCards;
		setCardsByRarity();
		reloadCards();
	}
	
	public Cell[][] getBoardCells() { return boardCells; }
	public static ArrayList<Card> getOriginalCards() { return originalCards; }
	
	public static ArrayList<Card> getCards() { return cards; }
	public static void setCards(ArrayList<Card> cards) { Board.cards = cards; }
	
	public static ArrayList<Monster> getStationedMonsters() { return stationedMonsters; }
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}
	
	private int[] indexToRowCol(int index) {
		int[] rowCol = new int[2];
		if (index < 10)
			rowCol[1] = index;
		else {
			int major = index / 10, minor = index % 10;
			rowCol[0] = major;
			rowCol[1] = major % 2 == 0? minor: Math.abs(minor - 9);
		}
		return rowCol;
	}
	
	private Cell getCell(int index) {
		int[] rowCol = indexToRowCol(index);
		return boardCells[rowCol[0]][rowCol[1]];
	}
	private void setCell(int index, Cell cell) {
		int[] rowCol = indexToRowCol(index);
		boardCells[rowCol[0]][rowCol[1]] = cell;
	}
	
	public void initializeBoard(ArrayList<Cell> specialCells) {
		int i = 1, conveyor = 0, sock = 0;
		for (Cell currentCell : specialCells) {
			if (currentCell instanceof DoorCell) {
				setCell(i, currentCell);
				i += 2;
			}
			else if (currentCell instanceof ConveyorBelt)
				setCell(Constants.CONVEYOR_CELL_INDICES[conveyor++], currentCell);
			else if (currentCell instanceof ContaminationSock)
				setCell(Constants.SOCK_CELL_INDICES[sock++], currentCell);
		}
		for (i = 0; i < Constants.CARD_CELL_INDICES.length; i++)
			setCell(Constants.CARD_CELL_INDICES[i], new CardCell("Card cell"));
		i = 0;
		for (Monster currentMonster : stationedMonsters) {
			currentMonster.setPosition(Constants.MONSTER_CELL_INDICES[i]);
			setCell(Constants.MONSTER_CELL_INDICES[i++], new MonsterCell(currentMonster.getName(), currentMonster));
		}
		for (Cell[] currentRow : boardCells)
			for (Cell currentCell : currentRow)
				if (currentCell == null)
					currentCell = new Cell("Normal cell");
	}
	private void setCardsByRarity() {
		ArrayList<Card> expandedCards = new ArrayList<Card>();
		for (Card card : originalCards) {
			int rariry = card.getRarity();
			for (int i = 0; i < rariry; i++)
				expandedCards.add(card);
		}
		originalCards = expandedCards;
	}
	
	public static void reloadCards() {
		for (Card card : originalCards)
			cards.add(card);
		shuffle(cards);
	}
	public static Card drawCard() {
		if (cards.isEmpty()) reloadCards();
		return cards.remove(0);
	}
	
	public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException {
		if (currentMonster.getPosition() + roll == opponentMonster.getPosition())
			throw new InvalidMoveException();
		getCell(currentMonster.getPosition()).setMonster(null);
		currentMonster.move(roll);
		getCell(currentMonster.getPosition()).onLand(currentMonster, opponentMonster);
		if (currentMonster.isConfused()) {
			currentMonster.decrementConfusion();
			opponentMonster.decrementConfusion();
		}
		updateMonsterPositions(currentMonster, opponentMonster);
	}
	private void updateMonsterPositions(Monster player, Monster opponent) {
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			Cell currentCell = getCell(i);
			if (currentCell.isOccupied())
				currentCell.setMonster(null);
		}
		getCell(player.getPosition()).setMonster(player);
		getCell(opponent.getPosition()).setMonster(opponent);
	}
}