package edu.hitsz.factory.Prop;

import edu.hitsz.props.BaseProp;

public interface PropFactory {
    BaseProp createProp(int spawnX, int spawnY, int speedX, int speedY);
}
