package model;
import java.util.Random;
public enum Choice {
	SCISSORS, PAPER, ROCK, LIZARD, SPOCK;
	public static Choice rand() {
		return values()[new Random().nextInt(values().length)];
	}
}