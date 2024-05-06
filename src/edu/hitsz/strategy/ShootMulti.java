package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ShootMulti implements Strategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft obj) {
        List<BaseBullet> res = new LinkedList<>();
        int x = obj.getLocationX();
        int y = obj.getLocationY() + obj.getDirection()*2;
        int spdX = 0;
        int spdY = obj.getSpeedY() + obj.getDirection()*5;
        BaseBullet bullet;
        int shootNum = 3;
        for (int i = 0; i < shootNum; i++)
        {
            if (obj instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, spdX + (i * 2 - shootNum + 1), spdY, obj.getPower());
            }
            else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, spdX + (i * 2 - shootNum + 1), spdY, obj.getPower());
            }
            res.add(bullet);
        }
        return res;
    }
}
