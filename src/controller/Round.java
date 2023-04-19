package controller;
import model.Choice;
import model.Status;
public class Round {
	private static int played = 0, win = 0, draw = 0, lose = 0;
	private final Choice human, robot;
	private final Status status;
	public Round(Choice human, Choice robot) {
		this.human = human;
		this.robot = robot;
		played++;
		status = human == robot
			? Status.DRAW
			: human.ordinal() == (robot.ordinal() + 1) % Choice.values().length
				|| human.ordinal() == (robot.ordinal() + 3) % Choice.values().length
					? Status.LOSE
					: Status.WIN;
		switch (status) {
			case WIN -> win++;
			case DRAW -> draw++;
			default -> lose++;
		}
	}
	public Choice getHuman() {
		return human;
	}
	public Choice getRobot() {
		return robot;
	}
	public static int matches() {
		return played;
	}
	public static int wins() {
		return win;
	}
	public static int draws() {
		return draw;
	}
	public static int loses() {
		return lose;
	}
	public Status status() {
		return status;
	}
	@Override
	public String toString() {
		return "H: %s vs R: %s\tPL: %d, W: %d (%.0f), D: %d (%.0f), L: %d (%.0f)"
			.formatted(
				human.toString().toLowerCase(),
				robot.toString().toLowerCase(),
				played,
				win, (double) 100 * win / played,
				draw, (double) 100 * draw / played,
				lose, (double) 100 * lose / played);
	}
}