package edu.hitsz.aircraft;

import edu.hitsz.Listener.BoomListener;
import edu.hitsz.application.Main;
import edu.hitsz.application.Menu;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.Context;
import edu.hitsz.strategy.ShootCircle;
import edu.hitsz.strategy.ShootMulti;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft {
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setDirection(1);
        setPower(30);
        Menu.musicThread.Halt();
        musicThread = new MusicThread("src/videos/bgm_boss.wav");
        musicThread.start();
    }

    private Context enemyShooter = new Context(new ShootCircle());

    private int shootCD = 0;
    private static final int MAXSHOOTCD = 3;
    public static MusicThread musicThread;


    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        shootCD++;
        if(shootCD < MAXSHOOTCD)
        {
            return new LinkedList<>();
        }
        else {
            shootCD = 0;
            return enemyShooter.executeStrategy(this);
        }
    }

    @Override
    public void vanish() {
        super.vanish();
        musicThread.Halt();
        Menu.musicThread = new MusicThread("src/videos/bgm.wav");
        Menu.musicThread.setIsLoop(true);
        Menu.musicThread.start();
    }
    @Override
    public void BoomActivate() {
        if(notValid()) return;
        decreaseHp(60);
    }

}
