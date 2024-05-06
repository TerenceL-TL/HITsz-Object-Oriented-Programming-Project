package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ShootMulti;
import edu.hitsz.strategy.Strategy;

public class FireBuffProp extends BaseProp{

    public FireBuffProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
    }


    @Override
    public void effect(HeroAircraft obj) {
        System.out.println("FireSupply active!");
        obj.setShootMode(new ShootMulti());
        vanish();
    }

}
