package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

public class FireBuffProp extends BaseProp{

    public FireBuffProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time,
                    int FireBuff) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
        this.FireBuff = FireBuff;
    }

    private int FireBuff;

    public void changeFireBuff(int FireBuff) {
        this.FireBuff = FireBuff;
    }

    public int GetFireBuff() {
        return FireBuff;
    }

    @Override
    public void effect(HeroAircraft obj) {
        System.out.println("FireSupply active!");
        obj.setShootNum(FireBuff);
        vanish();
    }

}
