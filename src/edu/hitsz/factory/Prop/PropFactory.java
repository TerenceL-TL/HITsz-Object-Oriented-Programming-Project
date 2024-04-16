package edu.hitsz.factory.Prop;

import edu.hitsz.props.BaseProp;

public interface PropFactory {
    public abstract BaseProp createProp(int spawnX, int spawnY, int speedX, int speedY);
}
