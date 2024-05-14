package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.strategy.ShootCircle;
import edu.hitsz.strategy.ShootMulti;

import java.util.List;

public class SuperFireSupplyProp extends BaseProp{
    public SuperFireSupplyProp(int locationX, int locationY,
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
        System.out.println("SuperFireSupply active!");
        obj.setShootMode(new ShootCircle());
        vanish();
    }
}
