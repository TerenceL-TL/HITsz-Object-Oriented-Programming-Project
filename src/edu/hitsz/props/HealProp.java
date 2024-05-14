package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

import java.util.List;

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
    public void effect(HeroAircraft obj, List<AbstractAircraft> enemyAircrafts)
    {
        musicThread = new MusicThread("src/videos/get_supply.wav");
        musicThread.start();
        System.out.println("Heal active!");
        obj.increaseHp(HealHp);
        vanish();
    }
}
