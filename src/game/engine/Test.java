package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import game.engine.monsters.Monster;

public class Test {
	public static void main(String[] args) {
		Game game = null;
		try {
			game = new Game(Role.SCARER);
		}
		catch (IOException e) {
			System.out.print("Error.");
		}
		ArrayList<Monster> stationedMonsters = game.getBoard().getStationedMonsters();
		for (Monster monster : stationedMonsters)
			System.out.println(monster.getName());
		System.out.println("\n"+game.getPlayer().getName());
		System.out.println(game.getOpponent().getName());
	}
}