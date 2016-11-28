package net.scientifichooliganism.ssorgame;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class SSORGame {
	public static final String SINGLE = "single";
	public static final String TEAM = "team";
	private String id;
	private String gameType;
	private boolean inProgress = false;
	private Vector<String> teams;
	private Vector<String> players;
	private ConcurrentHashMap<String, String> lastTurnPlayers;
	private int points;
	private Thread pointAdder;
	private Logger logger;

	public SSORGame () {
		id = null;
		gameType = SSORGame.SINGLE;
		teams = new Vector<String>();
		players = new Vector<String>();
		points = 100;
		pointAdder = null;
		logger = Logger.getLogger(this.getClass().getName());
	}

	public SSORGame (String idIn) {
		this();
		setId(idIn);
	}

	public SSORGame (String idIn, String type) {
		this(idIn);
		gameType = type;
	}

	public int getPoint(String player) {
		String message = "SSORGame.getPoint(String)";
		int ret = 0;

		if (player == null) {
			throw new IllegalArgumentException(message + "");
		}

		if (player.length() <= 0) {
			throw new IllegalArgumentException(message + "");
		}

		//The rules for COOP and VERSUS are the same,
		//because they are the same game, which is kind of the whole point.
		switch (gameType) {
			case SSORGame.TEAM:
				String team = null;
				//find team

				//players on the same team can receive bonus points if they go in a certain order
				//in addition, all players can receive bonus points for going in a certain order
				lastTurnPlayers.put(team, player);
				break;
			default:
				if (points > 0) {
					points--;
					ret = 1;
				}
		}

		if (points <= 0) {
			stopGame();
		}

		return ret;
	}

	public String getId () {
		return id;
	}

	public void setId (String in) throws IllegalArgumentException {
		String message = "SSORGame.setId(String)";

		if (in == null) {
			throw new IllegalArgumentException(message + "");
		}

		if (in.length() <= 0) {
			throw new IllegalArgumentException(message + "");
		}

		if ((id != null) && (id.length() > 0)) {
			throw new RuntimeException(message + "");
		}

		id = in;
	}

	public String getGameType () {
		return gameType;
	}

	public void setGameType (String in) throws IllegalArgumentException {
		if (in == null) {
			throw new IllegalArgumentException("");
		}

		if (in.length() <= 0) {
			throw new IllegalArgumentException("");
		}

		//validate that in is an acceptable game type

		if (isGameInProgress()) {
			throw new RuntimeException("");
		}

		gameType = in;
	}

	public Vector<String> getTeams () {
		return teams;
	}

	public void addTeam (String in) throws IllegalArgumentException {
		if (in == null) {
			throw new IllegalArgumentException("");
		}

		if (in.length() <= 0) {
			throw new IllegalArgumentException("");
		}
	}

	public Vector<String> getPlayers () {
		return players;
	}

	public void addPlayer (String in) throws IllegalArgumentException {
		if (in == null) {
			throw new IllegalArgumentException("");
		}

		if (in.length() <= 0) {
			throw new IllegalArgumentException("");
		}
	}

	public boolean isGameInProgress () {
		return inProgress;
	}

	public void startGame () {
		if (gameType.equals(SSORGame.TEAM)) {
			//randomize the list of players
			//randomize the list of teams
		}

		//do not start the game until the lists have been randomized
		inProgress = true;

		if (pointAdder == null) {
			pointAdder = new Thread() {
				@Override
				public void run() {
					while (inProgress) {
						try {
							sleep(10000);
						}
						catch (InterruptedException ie) {
							logger.throwing(this.getClass().getName(), "run", ie);
							ie.printStackTrace();
						}

						points += 1;
					}
				}
			};
		}

		try {
			pointAdder.start();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void stopGame () {
		inProgress = false;
	}

	public int getPoints () {
		return points;
	}

	public void setPoints (int in) {
		if (points < 0) {
			throw new IllegalArgumentException("setPoints(int) may not be called while a game is in-progress");
		}

		points = in;
	}
}