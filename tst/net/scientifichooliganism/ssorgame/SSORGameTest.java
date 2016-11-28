package net.scientifichooliganism.ssorgame;

import java.io.InputStreamReader;

public class SSORGameTest {

	public static void main (String [] args) {
		try {
			SSORGame game = new SSORGame();

			if (game.getPoints() == 100) {
				System.out.println("===============================================================================");
				System.out.println("game.getPoints(): " + game.getPoints());
				System.out.println("starting game...");
				game.startGame();

//				try {
//					Thread.currentThread().wait(30000);
//				}
//				catch (IllegalMonitorStateException imse) {
//					imse.printStackTrace();
//				}
				System.out.println("press enter to stop game");
				new InputStreamReader(System.in).read();

				System.out.println("stopping game...");
				game.stopGame();
				System.out.println("game.getPoints(): " + game.getPoints());
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}