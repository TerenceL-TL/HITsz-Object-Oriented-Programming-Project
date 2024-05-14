package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.strategy.ShootMulti;
import edu.hitsz.strategy.Strategy;

import java.util.List;

public class FireBuffProp extends BaseProp{

    public FireBuffProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
    }


    @Override
    public void effect(HeroAircraft obj, List<AbstractAircraft> enemyAircrafts)
    {
        musicThread = new MusicThread("src/videos/get_supply.wav");
        musicThread.start();
        System.out.println("FireSupply active!");
        obj.setShootMode(new ShootMulti());
        vanish();
    }

}
