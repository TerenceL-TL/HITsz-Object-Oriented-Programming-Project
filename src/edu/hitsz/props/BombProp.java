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
        PropNotifyAll();
        System.out.println("Boom!");
        vanish();
    }
}