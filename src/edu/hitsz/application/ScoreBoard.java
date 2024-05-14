package edu.hitsz.application;

import edu.hitsz.scoreboard.Score;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoard {
	private JPanel root;
	private JPanel scoreboard;
	private JPanel buttons;
	private JScrollPane tableScrollPane;
	private JTable scoretable;
	private JLabel headerlabel;
	private JButton deletebutton;
	private JButton returnbutton;
	private JPanel difficultypanel;
	private JLabel difficultyDisplay;
	private String difficulty;
	private String[] columnName = {"排名", "玩家名", "分数", "时间"};
	private DefaultTableModel model;
	private edu.hitsz.scoreboard.ScoreBoard scoreBoard;
	
	public ScoreBoard() {
		model = new DefaultTableModel(null, columnName){
			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		
		scoretable.setModel(model);
		tableScrollPane.setViewportView(scoretable);
		deletebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = scoretable.getSelectedRow();
				int result = JOptionPane.showConfirmDialog(deletebutton,
						"是否确定中删除？");
				if (JOptionPane.YES_OPTION == result && row != -1) {
					scoreBoard.remove(row);
					scoreBoard.writeToFile();
					model = new DefaultTableModel(null, columnName){
						@Override
						public boolean isCellEditable(int row, int col){
							return false;
						}
					};
					setTableData(scoreBoard);
					scoretable.setModel(model);
					tableScrollPane.setViewportView(scoretable);
				}
			}
		});
		returnbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Menu.musicThread = new MusicThread("src/videos/bgm.wav");
				Menu.musicThread.setIsLoop(true);
				Menu.musicThread.start();
				Main.cardLayout.first(Main.cardPanel);
			}
		});
	}
	
	public JPanel getMainPanel() {
		return root;
	}
	
	public void setDifficulty(Game.Difficulty difficulty) {
		this.difficulty = "游戏难度为：" + difficulty.toString();
		difficultyDisplay.setText(this.difficulty);
//		System.out.println(this.difficulty);
	}
	public void setTableData(edu.hitsz.scoreboard.ScoreBoard scoreBoard) {
		int index = 1;
		this.scoreBoard = scoreBoard;
		for (Score score : scoreBoard.getScoreList()) {
			String[] data = {Integer.toString(index), score.getUsername(), Integer.toString(score.getScore()), score.getDate().toString()};
			model.addRow(data);
			index = index + 1;
		}
	}
}
