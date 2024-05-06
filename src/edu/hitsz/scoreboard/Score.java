package edu.hitsz.scoreboard;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {
	private String username;
	private int score;
	private Date date;
	
	public Score(String username, int score, Date date) {
		this.username = username;
		this.score = score;
		this.date = date;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return "Score{" +
				"username='" + username + '\'' +
				", score=" + score +
				", date=" + date +
				'}';
	}
}
