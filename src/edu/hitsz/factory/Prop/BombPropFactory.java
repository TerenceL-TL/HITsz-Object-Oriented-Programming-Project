package edu.hitsz.factory.Prop;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.BombProp;
import edu.hitsz.props.HealProp;

public class BombPropFactory implements PropFactory{
    @Override
    public BaseProp createProp(int spawnX, int spawnY, int speedX, int speedY) {
        return new BombProp(spawnX, spawnY, speedX, speedY, true, 0);
    }
}
