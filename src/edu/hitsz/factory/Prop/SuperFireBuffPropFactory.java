package edu.hitsz.factory.Prop;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.FireBuffProp;
import edu.hitsz.props.SuperFireSupplyProp;

public class SuperFireBuffPropFactory implements PropFactory {
    public BaseProp createProp(int spawnX, int spawnY, int speedX, int speedY) {
        return new SuperFireSupplyProp(spawnX, spawnY, speedX, speedY, true, 0);
    }
}
