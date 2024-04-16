package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class BombProp extends BaseProp {
    public BombProp(int locationX, int locationY,
                        int speedX, int speedY,
                        boolean imme_effect, double effect_time) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
    }

    @Override
    public void effect(HeroAircraft obj) {
        System.out.println("BombSupply active!");
        vanish();
    }
}