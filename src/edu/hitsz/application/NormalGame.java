package edu.hitsz.application;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class NormalGame extends Game{
    NormalGame()
    {
        super();
        propGenerator_elite = new PropGenerator(0.3, 0.2, 0.2, 0.0, 1);
        propGenerator_super = new PropGenerator(0.2, 0.2, 0.4, 0.0, 1);
        propGenerator_boss = new PropGenerator(0.3, 0.1, 0.2, 0.2, 3);
        super.setDifficulty(Difficulty.NORMAL);
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));;
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        } // load background easy

        no_boss = false;

        System.out.println("游戏开始，难度设置为NORMAL，此时道具掉落概率为：（血包掉落概率，火力补给掉落概率，炸弹掉落概率，超级火力补给掉落概率）");
        System.out.println("精英敌机（掉落一个以内道具）：" + 0.3 + " " + 0.2 + " " + 0.2 + " " + 0.0);
        System.out.println("超级精英敌机（掉落一个以内道具）：" + 0.2 + " " + 0.2 + " " + 0.4 + " " + 0.0);
        System.out.println("boss敌机（掉落三个以内道具）：" + 0.3 + " " + 0.1 + " " + 0.2 + " " + 0.2);
    }

    @Override
    public void UpdateDifficulty()
    {
        cycleDuration = (int) (1. * baseCycleDuration / (1 + Math.log(time) / 50)); // hero and enemy cycle
        double eliteAppearRate = baseEliteAppearRate * (1 + Math.log(time) / 30); // elite rate
        enemyGenerator.setEliteAppearRate(eliteAppearRate);
        double nowEnemyBuffRate = enemyGenerator.getEnemyBuffRate() + 0.02;
        enemyGenerator.setEnemyBuffRate(nowEnemyBuffRate);
        System.out.println("难度升级！当前敌我循环周期为 " + cycleDuration + " 当前精英敌机出现概率为 " + eliteAppearRate + " 当前敌机基础属性倍率为 " + nowEnemyBuffRate);
    }
}
