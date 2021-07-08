package semana13.game;

import java.io.Serializable;

public class GameDice implements Serializable{
	
	
	private static final long serialVersionUID = -2854209193221372785L;
	
	private int attempts = 3;
	private int dice1;
	private int dice2;
	private int sum;
	
	public void InitGame() {
		dice1 = throwDice(1, 6);
		dice2 = throwDice(1, 6);
		sum = dice1 + dice2;
		System.out.println(sum);
	}
	
	public boolean playGame(int guess) {
		if(attempts > 0) {
			attempts--;
			return guess == sum;
		}
		return false;
	}
	
	public int getAttempts() {
		return attempts;
	}
	
	public boolean existAttempts() {
		return attempts > 0;
	}
	
	private int throwDice(int min, int max){
		return (int) (Math.random()*(max + 1 - min)) + min;
	}

	@Override
	public String toString() {
		return "GameDice Continue\n"
				+ "Attempts=" + attempts 
				+"\nGood Lucky\n";
	}
	
	
}
