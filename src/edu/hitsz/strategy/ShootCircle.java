package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ShootCircle implements Strategy{
    @Override
    public List<BaseBullet> shoot(AbstractAircraft obj) {
        List<BaseBullet> res = new LinkedList<>();

        int x = obj.getLocationX();
        int y = obj.getLocationY() + obj.getDirection()*2;
        int spdX = 0;
        int spdY = obj.getSpeedY();
        int shootNum = 20;
        int radius = 20;
        int radius_spd = 5;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++)
        {
            if (obj instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (int) (radius * Math.sin(2 * Math.PI / shootNum * i)), y - (int) (radius * Math.cos(2 * Math.PI / shootNum * i)), spdX + (int) (radius_spd * Math.sin(2 * Math.PI / shootNum * i)), spdY - (int) (radius_spd * Math.cos(2 * Math.PI / shootNum * i)), obj.getPower());
            }
            else {
                bullet = new EnemyBullet(x + (int) (radius * Math.sin(2 * Math.PI / shootNum * i)), y - (int) (radius * Math.cos(2 * Math.PI / shootNum * i)), spdX + (int) (radius_spd * Math.sin(2 * Math.PI / shootNum * i)), spdY - (int) (radius_spd * Math.cos(2 * Math.PI / shootNum * i)), obj.getPower());
            }
            res.add(bullet);
        }
        return res;
    }
}
