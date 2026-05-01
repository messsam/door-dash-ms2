package game.engine.dataloader;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import game.engine.Role;
import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;
import game.engine.exceptions.InvalidCSVFormat;

public class DataLoader {
	private static final String CARDS_FILE_NAME = "cards.csv";
	private static final String CELLS_FILE_NAME = "cells.csv";
	private static final String MONSTERS_FILE_NAME = "monsters.csv";
	
	@SuppressWarnings("resource")
	public static ArrayList<Card> readCards() throws IOException {
		ArrayList<Card> cards = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(CARDS_FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) continue;
				String[] data = line.split(",");
				if (data.length != 4 && data.length != 5) {
					System.out.println(data.length);
					throw new InvalidCSVFormat(line);
				}
				switch (data[0]) {
					case "ENERGYSTEAL":
						cards.add(new EnergyStealCard(data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4])));
						break;
					case "STARTOVER":
						cards.add(new StartOverCard(data[1], data[2], Integer.parseInt(data[3]), Boolean.parseBoolean(data[4])));
						break;
					case "CONFUSION":
						cards.add(new ConfusionCard(data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4])));
						break;
					case "SHIELD":
						cards.add(new ShieldCard(data[1], data[2], Integer.parseInt(data[3])));
						break;
					case "SWAPPER":
						cards.add(new SwapperCard(data[1], data[2], Integer.parseInt(data[3])));
						break;
					default:
						throw new InvalidCSVFormat("Unknown card type: " + data[0]);
				}
			}
		}
		return cards;
	}
	
	@SuppressWarnings("resource")
	public static ArrayList<Cell> readCells() throws IOException {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		try (BufferedReader reader = new BufferedReader(new FileReader(CELLS_FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) continue;
				String[] data = line.split(",");
				if (data.length == 3)
					cells.add(new DoorCell(data[0], Role.valueOf(data[1]), Integer.parseInt(data[2])));
				else if (data.length == 2) {
					int effect = Integer.parseInt(data[1]);
					if (effect > 0) cells.add(new ConveyorBelt(data[0], effect));
					else cells.add(new ContaminationSock(data[0], effect));
				}
				else throw new InvalidCSVFormat(line);
			}
		}
		return cells;
	}
	
	@SuppressWarnings("resource")
	public static ArrayList<Monster> readMonsters() throws IOException {
		ArrayList<Monster> monsters = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(MONSTERS_FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) continue;
				String[] data = line.split(",");
				if (data.length != 5)
					throw new InvalidCSVFormat(line);
				switch (data[0]) {
					case "DASHER":
						monsters.add(new Dasher(data[1], data[2], Role.valueOf(data[3]), Integer.parseInt(data[4])));
						break;
					case "DYNAMO":
						monsters.add(new Dynamo(data[1], data[2], Role.valueOf(data[3]), Integer.parseInt(data[4])));
						break;
					case "MULTITASKER":
						monsters.add(new MultiTasker(data[1], data[2], Role.valueOf(data[3]), Integer.parseInt(data[4])));
						break;
					case "SCHEMER":
						monsters.add(new Schemer(data[1], data[2], Role.valueOf(data[3]), Integer.parseInt(data[4])));
						break;
					default:
						throw new InvalidCSVFormat("Unknown monster type: " + data[0]);
				}
			}
		}
		return monsters;
	}
}