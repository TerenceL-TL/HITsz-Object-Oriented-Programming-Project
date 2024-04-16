package edu.hitsz.factory.Prop;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.FireBuffProp;
import edu.hitsz.props.HealProp;

public class HealPropFactory implements PropFactory{
    @Override
    public BaseProp createProp(int spawnX, int spawnY, int speedX, int speedY) {
        return new HealProp(spawnX, spawnY, speedX, speedY, true, 0, 20);
    }
}
