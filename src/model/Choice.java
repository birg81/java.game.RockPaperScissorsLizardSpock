package model;
import java.util.Random;
public enum Choice {
	SCISSORS, PAPER, ROCK, LIZARD, SPOCK;
	public static Choice rand(boolean isExtra) {
		return values()[new Random().nextInt(isExtra ? values().length : 3)];
	}
}
