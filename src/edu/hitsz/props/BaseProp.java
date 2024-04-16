package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class BaseProp extends AbstractFlyingObject {
    public BaseProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time) {
        super(locationX, locationY, speedX, speedY);
        this.imme_effect = imme_effect;
        this.effect_time = effect_time;
    }

    private final boolean imme_effect;
    private double effect_time;

    public boolean isImme_effect() {
        return imme_effect;
    }

    public double get_effect_time() {
        return effect_time;
    }

    public void change_effect_time(double effect_time) {
        this.effect_time = effect_time;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public abstract void effect(HeroAircraft obj);
}
