package edu.hitsz.scoreboard;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ScoreBoard implements ScoreBoardDAO{
	private List<Score> scoreList;
	public ScoreBoard() {
		scoreList = new LinkedList<Score>();
	}
	public void add(Score score) {
		if (scoreList.isEmpty()) {
			scoreList.add(score);
			return;
		}
		int pos = 0;
		while (scoreList.get(pos).getScore() > score.getScore()) {
			pos++;
		}
		scoreList.add(pos, score);
	}
	public void remove(int id) {
		scoreList.remove(id);
	}
	public void writeToFile() {
		File file = new File("scoreboard.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("排行榜文件创建成功");
			}
			catch (Exception e) {
				System.out.println("排行榜文件创建失败");
			}
		}
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(scoreList);
			System.out.println("排行榜文件读取成功");
		}
		catch (Exception e) {
			System.out.println("排行榜文件读取失败，无法保存分数");
		}
		finally {
			try {
				objectOutputStream.close();
				fileOutputStream.close();
			}
			catch (Exception e) {
				System.out.println("文件流关闭失败");
			}
		}
	}
	public void readFromFile() {
		File file = new File("scoreboard.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("排行榜文件创建成功");
			}
			catch (Exception e) {
				System.out.println("排行榜文件创建失败");
			}
		}
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			scoreList = (List<Score>) objectInputStream.readObject();
			System.out.println("排行榜文件读取成功");
		}
		catch (Exception e) {
			System.out.println("排行榜文件读取失败，无法获得分数");
			System.out.println(e.getMessage());
		}
		finally {
			try {
				objectInputStream.close(); 
				fileInputStream.close();
			}
			catch (Exception e) {
				System.out.println("文件流关闭失败");
			}
		}
	}
	public void display() {
		System.out.println("*******************************");
		System.out.println("*          SCOREBOARD         *");
		System.out.println("*******************************");
		for (int i = 0; i < scoreList.size(); i++) {
			System.out.println("No." + (i + 1) + "\tusername:" + scoreList.get(i).getUsername() + "\tscore:" + scoreList.get(i).getScore() + "\tdate:" + scoreList.get(i).getDate());
		}
	}
}
