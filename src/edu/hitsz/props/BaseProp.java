package edu.hitsz.props;

import edu.hitsz.Listener.BoomListener;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProp extends AbstractFlyingObject {
    public BaseProp(int locationX, int locationY,
                    int speedX, int speedY,
                    boolean imme_effect, double effect_time) {
        super(locationX, locationY, speedX, speedY);
        this.imme_effect = imme_effect;
        this.effect_time = effect_time;
    }
    private List<BoomListener> listenerList = new ArrayList<>();

    private final boolean imme_effect;
    private double effect_time;
    public MusicThread musicThread;


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

    abstract public void effect(HeroAircraft obj, List<AbstractAircraft> enemyAircrafts);

    public void addListener(BoomListener listener) {
        listenerList.add(listener);
    }
    public void removeListener(BoomListener listener) {
        listenerList.remove(listener);
    }
    public void PropNotifyAll() {
        for (BoomListener listener : listenerList) {
            listener.BoomActivate();
        }
    }
}
