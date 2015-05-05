package br.com.rankmatch.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static Match match;

	public static void main(String[] args) {

		readLog("C:\\Users\\Maiara\\Desktop\\amil\\log.txt");
	}

	private static void readLog(String logPath) {
		try {
			FileInputStream fstream = new FileInputStream(logPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));
			String strLine;
			/* read log line by line */
			while ((strLine = br.readLine()) != null) {
				/* parse strLine to obtain what you want */
				String[] splLine = strLine.split("-");
				String s = splLine[1].trim();
				String[] eventWords = s.split(" ");

				switch (identifyEvent(eventWords)) {
				case 0:
					initMatch(eventWords);
					break;
				case 1:
					addKill(eventWords);
					break;
				case 2:
					endMatch(eventWords);
					break;
				default:
					errorExit();
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		showRank();

	}

	private static void initMatch(String[] eventWords) {
		match = new Match(eventWords[2]);
	}

	private static int identifyEvent(String[] eventWords) {

		for (String word : eventWords) {

			if (word.equals("started")) {
				return 0;
			} else if (word.equals("killed")) {
				return 1;
			} else if (word.equals("ended")) {
				return 2;
			}
		}
		return -1;
	}

	private static void addKill(String[] eventWords) {
		boolean hasNewPlayer = true;
		for (Player player : match.listPlayer) {
			if (player.getNickName().equals(eventWords[0])) {
				hasNewPlayer = false;
				player.addKill(eventWords[2], eventWords[4]);
				break;
			}
		}
		if (hasNewPlayer) {
			if (!eventWords[0].equals("<WORLD>")) {
				Player newPlayer = new Player(eventWords[0]);
				newPlayer.addKill(eventWords[2], eventWords[4]);
				match.listPlayer.add(newPlayer);
			}

		}

		addDeath(eventWords[2]);
	}

	private static void addDeath(String playerKilled) {
		boolean hasNewEnemy = true;
		for (Player player : match.listPlayer) {
			if (player.getNickName().equals(playerKilled)) {
				player.addDeath();
				hasNewEnemy = false;
				player.setStreakCount(0);
				break;
			}
		}
		if (hasNewEnemy) {
			Player newPlayer = new Player(playerKilled);
			match.listPlayer.add(newPlayer);

		}

	}

	private static void endMatch(String[] eventWords) {
		// match = null;
	}

	private static void showRank() {
		List<Player> winners = new ArrayList<Player>();
		winners.add(new Player());
		System.out.println("---------------------------------");
		System.out.println("|\tPlayer\tKill\tDeath\t|");
		for (Player player : match.listPlayer) {
			int last = winners.size() - 1;
			if (winners.get(last).getListPlayersKilled().size() < player
					.getListPlayersKilled().size()) {
				winners.remove(last);
				winners.add(player);
			} else if (winners.get(last).getListPlayersKilled().size() == player
					.getListPlayersKilled().size()) {
				winners.add(player);
			}

			System.out.print("|\t");
			System.out.print(player.getNickName());
			System.out.print("\t");
			System.out.print(player.getListPlayersKilled().size());
			System.out.print("\t");
			System.out.print(player.getDeaths());
			System.out.println("\t|");

		}
		System.out.println("---------------------------------");
		System.out.println("*********************************");
		System.out.println("Winners Stats");
		for (Player player : winners) {
			System.out.println("Player Name: " + player.getNickName());
			System.out.println("Streak: " + player.getStreakCount());
		}
	}

	private static void errorExit() {
		System.out.print("I am sorry Dave, i'm afraid i can't do that");
		System.exit(0);

	}

}
