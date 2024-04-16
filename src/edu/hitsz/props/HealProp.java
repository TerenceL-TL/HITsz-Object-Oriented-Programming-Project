package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

public class HealProp extends BaseProp {

    public HealProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time,
                    int HealHp) {
        super(locationX, locationY,
                speedX, speedY,
                imme_effect, effect_time);
        this.HealHp = HealHp;
    }

    private int HealHp;

    public void changeHealHp(int HealHp) {
        this.HealHp = HealHp;
    }

    public int GetHealHp() {
        return HealHp;
    }

    @Override
    public void effect(HeroAircraft obj) {
        System.out.println("Heal active!");
        obj.increaseHp(HealHp);
        vanish();
    }
}
