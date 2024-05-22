package edu.hitsz.application;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class EasyGame extends Game{
    EasyGame()
    {
        super();
        propGenerator_elite = new PropGenerator(0.3, 0.1, 0.1, 0.0, 1);
        propGenerator_super = new PropGenerator(0.3, 0.2, 0.1, 0.0, 1);
        propGenerator_boss = new PropGenerator(0.3, 0.1, 0.2, 0.2, 3);
        super.setDifficulty(Difficulty.EASY);
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));;
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        } // load background easy

        no_boss = true;

        System.out.println("游戏开始，难度设置为EASY，此时道具掉落概率为：（血包掉落概率，火力补给掉落概率，炸弹掉落概率，超级火力补给掉落概率）");
        System.out.println("精英敌机（掉落一个以内道具）：" + 0.3 + " " + 0.1 + " " + 0.1 + " " + 0.0);
        System.out.println("超级精英敌机（掉落一个以内道具）：" + 0.3 + " " + 0.2 + " " + 0.1 + " " + 0.0);
    }

    @Override
    public void UpdateDifficulty()
    {
        // None for Easy
    }
}
