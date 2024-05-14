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
				Game game = new Game();
				game.setDifficulty(Game.Difficulty.EASY);
				try {
					ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));;
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
					System.exit(-1);
				}
				Main.cardPanel.add(game);
				Main.cardLayout.last(Main.cardPanel);
				game.action();
			}
		});
		normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new Game();
				game.setDifficulty(Game.Difficulty.NORMAL);
				try {
					ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));;
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
					System.exit(-1);
				}
				Main.cardPanel.add(game);
				Main.cardLayout.last(Main.cardPanel);
				game.action();
			}
		});
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new Game();
				game.setDifficulty(Game.Difficulty.HARD);
				try {
					ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));;
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
					System.exit(-1);
				}
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
