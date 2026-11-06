package view;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import model.Choice;
import controller.Round;
public class WinApp {
	private boolean isExtra = false;
	private String playerName;
	private Round round;
	private int t;
	private Timer timer;
	private final JFrame win = new JFrame();
	private final Container ctx = win.getContentPane();
	private final JPanel
		headerPane = new JPanel(),
		footerPane = new JPanel(),
		masterPane = new JPanel();
	private void initialize() {
		win.setTitle("Rock Paper Scissors Advance");
		win.setResizable(false);
		win.setSize(835, 482);
		win.setLocationRelativeTo(null);
		win.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/favicon.png")));
		win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ctx.setLayout(new BorderLayout());
		ctx.add(headerPane, BorderLayout.NORTH);
		ctx.add(masterPane, BorderLayout.CENTER);
		ctx.add(footerPane, BorderLayout.SOUTH);
		WelcomeCtx();
		win.setVisible(true);
	}
	private void erase(int w, int h) {
		win.setSize(w, h);
		headerPane.removeAll();
		headerPane.revalidate();
		headerPane.repaint();
		masterPane.removeAll();
		masterPane.revalidate();
		masterPane.repaint();
		footerPane.removeAll();
		footerPane.revalidate();
		footerPane.repaint();
	}
	private void WelcomeCtx() {
		erase(300, 130);
		JLabel title = new JLabel("Insert your name");
		headerPane.add(title);
		JTextField choicePlayer = new JTextField();
		// choicePlayer.setPreferredSize(new Dimension(280, 30));
		masterPane.setLayout(new GridLayout(1, 1));
		masterPane.add(choicePlayer);
		JButton start = new JButton("Enter");
		JComboBox<String> modeCombo = new JComboBox<>(new String[] {"Regular", "Extra"});
		start.addActionListener(_ -> {
			playerName = !choicePlayer.getText().isBlank() ? choicePlayer.getText().strip() : "player";
			System.out.println(playerName);
			isExtra = "Extra".equals(modeCombo.getSelectedItem());
			startPlayCtx();
		});
		footerPane.setLayout(new GridLayout(1, 2));
		footerPane.add(start);
		footerPane.add(modeCombo);
	}
	private void startPlayCtx() {
		erase(400, 300);
		win.setTitle("Player: %s".formatted(playerName));
		t = 10;
		JLabel countPane = new JLabel("%d".formatted(t));
		timer = new Timer(1000, _ -> {
			if (t > 1)
				countPane.setText("%d".formatted(--t));
			else {
				round = new Round(Choice.rand(isExtra), Choice.rand(isExtra));
				resultPlayCtx(round);
			}
		});
		headerPane.add(new JLabel("Human vs Robot"));
		footerPane.add(new JLabel("%s".formatted(round != null ? round : "prima partita")));
		JPanel choicePane = new JPanel(new GridLayout(2, 3));
		masterPane.setLayout(new BorderLayout());
		Arrays.stream(Choice.values())
			.limit(isExtra ? Choice.values().length : 3)
			.forEach(c -> {
				JButton btn = new JButton(
					new ImageIcon(
						new ImageIcon(
							getClass().getResource("/img/%s.png".formatted(c.name().toLowerCase()))
						).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)
					)
				);
				btn.addActionListener(_ -> {
					if (timer.isRunning())
						timer.stop();
					round = new Round(c, Choice.rand(isExtra));
					resultPlayCtx(round);
				});
				choicePane.add(btn);
			});
		final ImageIcon img = new ImageIcon(getClass().getResource("/img/gear.png"));
		img.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		final JButton btn = new JButton(img);
		btn.addActionListener(_ -> {
			round = new Round(Choice.rand(isExtra), Choice.rand(isExtra));
			if (timer.isRunning())
				timer.stop();
			resultPlayCtx(round);
		});
		choicePane.add(btn);
		masterPane.add(choicePane, BorderLayout.CENTER);
		countPane.setPreferredSize(new Dimension(70, 50));
		countPane.setFont(new Font("Arial", Font.BOLD, 62));
		countPane.setHorizontalAlignment(SwingConstants.CENTER);
		masterPane.add(countPane, BorderLayout.EAST);
		timer.start();
	}
	private void resultPlayCtx(Round round) {
		erase(400, 200);
		if (timer.isRunning())
			timer.stop();
		System.out.println(round);
		headerPane.add(new JLabel("%s you have %s".formatted(
			playerName,
			round.status().toString().toLowerCase()
		)));
		masterPane.setLayout(new GridLayout(1, 5));
		for (final String figure : new String[]{
			"human",
			round.getHuman().toString().toLowerCase(),
			"vs",
			round.getRobot().toString().toLowerCase(),
			"robot"
		})
			masterPane.add(new JLabel(new ImageIcon(
				getClass().getResource("/img/%s.png".formatted(figure))
			)));
		JButton playAgain = new JButton("Play Again");
		playAgain.addActionListener(_ -> startPlayCtx());
		footerPane.setLayout(new GridLayout(1, 1));
		footerPane.add(playAgain);
	}
	public WinApp() {
		initialize();
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new WinApp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
