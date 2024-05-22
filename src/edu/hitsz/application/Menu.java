package edu.hitsz.application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu {
	private JPanel root;
	private JPanel ChooseDifficulty;
	private JPanel ChooseAudio;
	private JLabel title;
	private JButton easy;
	private JRadioButton audio;
	private JButton normal;
	private JButton hard;
	public static MusicThread musicThread;
	
	public Menu() {
		audio.setSelected(true);
		musicThread = new MusicThread("src/videos/bgm.wav");
		musicThread.setIsLoop(true);
		musicThread.start();
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new EasyGame();
				game.setDifficulty(Game.Difficulty.EASY);
				Main.cardPanel.add(game);
				Main.cardLayout.last(Main.cardPanel);
				game.action();
			}
		});
		normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new NormalGame();
				game.setDifficulty(Game.Difficulty.NORMAL);
				Main.cardPanel.add(game);
				Main.cardLayout.last(Main.cardPanel);
				game.action();
			}
		});
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new HardGame();
				game.setDifficulty(Game.Difficulty.HARD);
				Main.cardPanel.add(game);
				Main.cardLayout.last(Main.cardPanel);
				game.action();
			}
		});
		audio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (audio.isSelected()) {
					musicThread = new MusicThread("src/videos/bgm.wav");
					musicThread.setIsLoop(true);
					musicThread.start();
				}
				else {
					musicThread.Halt();
				}
			}
		});
	}
	
	public JPanel getMainPanel() {
		return root;
	}
}
