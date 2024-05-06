package edu.hitsz.scoreboard;

public interface ScoreBoardDAO {
	public void add(Score score);
	public void remove(int id);
	public void writeToFile();
	public void readFromFile();
	public void display();
}
