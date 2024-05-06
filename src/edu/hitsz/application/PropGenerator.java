package edu.hitsz.application;

import edu.hitsz.factory.Prop.*;
import edu.hitsz.props.BaseProp;

import java.util.LinkedList;
import java.util.List;

public class PropGenerator {
    private double HP_rate, Fire_rate, Bomb_rate, SuperFire_rate;
    private int times;

    public PropGenerator(double HP_rate, double Fire_rate, double Bomb_rate, double SuperFire_rate, int times)
    {
        if (HP_rate + Fire_rate + Bomb_rate + SuperFire_rate > 1.0)
        {
            throw new RuntimeException("Error prop rate!");
        }
        this.HP_rate = HP_rate;
        this.Fire_rate = Fire_rate;
        this.Bomb_rate = Bomb_rate;
        this.SuperFire_rate = SuperFire_rate;
        this.times = times;
    }

    public List<BaseProp> createProp(int locationX, int locationY, int speedX, int speedY)
    {
        List<BaseProp> res = new LinkedList<>();
//		System.out.println(dice);
        PropFactory propFactory;
        for (int i = 0; i < times; i++) {
            double dice = Math.random();
            if (dice < HP_rate) {
                propFactory = new HealPropFactory();
            } else if (dice < HP_rate + Fire_rate) {
                propFactory = new FireBuffPropFactory();
            } else if (dice < HP_rate + Fire_rate + Bomb_rate) {
                propFactory = new BombPropFactory();
            } else if (dice < HP_rate + Fire_rate + Bomb_rate + SuperFire_rate){
                propFactory = new SuperFireBuffPropFactory();
            } else {
                continue;
            }
            res.add(propFactory.createProp(locationX + (i * 2 - times + 1) * 20, locationY, speedX, speedY));
        }
        return res;
    }

}
