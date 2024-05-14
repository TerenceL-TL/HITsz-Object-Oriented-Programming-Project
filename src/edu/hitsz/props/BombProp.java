package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

import java.util.List;

public class BombProp extends BaseProp {
    public BombProp(int locationX, int locationY,
                        int speedX, int speedY,
                        boolean imme_effect, double effect_time) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
    }

    @Override
    public void effect(HeroAircraft obj, List<AbstractAircraft> enemyAircrafts) {
        System.out.println("BombSupply active!");
        musicThread = new MusicThread("src/videos/bomb_explosion.wav");
        musicThread.start();
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid()) {
                // 已被其他子弹击毁的敌机，不再检测
                // 避免多个子弹重复击毁同一敌机的判定
                continue;
            }
            enemyAircraft.decreaseHp(60);
        }
        System.out.println("Boom!");
        vanish();
    }
}