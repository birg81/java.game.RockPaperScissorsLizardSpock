package model;
import java.util.Random;
public enum Choice {
	SCISSORS, PAPER, ROCK, LIZARD, SPOCK;
	public static Choice rand(boolean isExtra) {
		return Choice.values()[new Random().nextInt(isExtra ? Choice.values().length : 3)];
	}
}