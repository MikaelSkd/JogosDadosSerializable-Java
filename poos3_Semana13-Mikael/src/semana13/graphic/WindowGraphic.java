package semana13.graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import semana13.game.GameDice;

public class WindowGraphic extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	
	private JLabel lblBet;
	private JTextField txtBet;
	
	private JButton btnNewGame;
	private JButton btnStart;
	private JButton btnClear;
	private JButton btnSave;
	private JButton btnBackupLastGame;
	
	private JTextArea txtGameArea;
	private JScrollPane scrollGame;
	
	private GameDice gameDice;
	
	public WindowGraphic(){
		
		createComponent();
		configWindom();
	}

	private void createComponent() {
		// TODO Auto-generated method stub
		panel = new JPanel();
		
		lblBet = new JLabel("Bet");
		
		txtBet = new JTextField(4);
		txtBet.setEditable(false);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(this);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		btnStart.setEnabled(false);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		
		btnSave = new JButton("Save Game");
		btnSave.addActionListener(this);
		btnSave.setEnabled(false);
		
		btnBackupLastGame = new JButton("Continue");
		btnBackupLastGame.addActionListener(this);
		
		txtGameArea = new JTextArea(30,30);
		txtGameArea.setEditable(false);
		
		scrollGame = new JScrollPane();
		scrollGame.setViewportView(txtGameArea);
		
		
		toAddComponents();
		
	}
	
	private void toAddComponents() {
		panel.add(lblBet);
		panel.add(txtBet);
		panel.add(btnNewGame);
		panel.add(btnStart);
		panel.add(btnClear);
		panel.add(btnSave);
		panel.add(btnBackupLastGame);
		panel.add(scrollGame);
		add(panel);
	}
	
	private void configWindom() {
		// TODO Auto-generated method stub
		setVisible(true);
		setTitle("Week 13 - Game Dice");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void createGame() {
		gameDice = new GameDice();
		gameDice.InitGame();
		txtBet.setEditable(true);
		txtBet.setText(null);
		btnStart.setEnabled(true);
		btnSave.setEnabled(true);
		btnBackupLastGame.setEnabled(true);
		txtGameArea.append("Game Start\n");
	}

	private void playGame() {
		String attempt = txtBet.getText();
		if(attempt.matches("[0-9]+")) {
			if(gameDice.playGame(Integer.parseInt(attempt))) {
				txtGameArea.append("Congratulations! You Win!");
				endGame();
			}else {
				if(gameDice.getAttempts() > 0) {
					txtGameArea.append(String.format("You missed! Your remaining attempts = %d\n", gameDice.getAttempts()));
				}else {
					txtGameArea.append(String.format("Game Over\n"));
					endGame();
				}
			}
		}
	}
	
	private void endGame() {
		txtBet.setText(null);
		btnStart.setEnabled(false);
		btnSave.setEnabled(false);
		btnBackupLastGame.setEnabled(false);
		txtBet.setEditable(false);
		txtBet.setText(null);
		gameDice = null;
	}
	
	private void areaClear() {
		txtGameArea.setText(null);
		txtBet.setText(null);
	}
	
	private void seralizableData(String file, Object obj) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(obj);
			txtGameArea.append("Game Saved!");
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
	}
	
	private Object loadGame(String file) {		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return ois.readObject();
		} catch (IOException|ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("You dont game saved! :(");
			throw new RuntimeException(e);
		}
	}
	
	private void continueGame(GameDice game) {
		txtGameArea.append(game.toString());
		txtBet.setEditable(true);
		txtBet.setText(null);
		btnStart.setEnabled(true);
		btnSave.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnNewGame) {
			createGame();
		}else if(e.getSource() == btnStart) {
			playGame();
		}else if(e.getSource() == btnClear) {
			areaClear();
		}else if(e.getSource() == btnSave) {
			seralizableData("Game.ser", gameDice);
		}else if(e.getSource() == btnBackupLastGame) {
			GameDice game = (GameDice) loadGame("Game.ser");
			continueGame(game);
		}
	}
}
