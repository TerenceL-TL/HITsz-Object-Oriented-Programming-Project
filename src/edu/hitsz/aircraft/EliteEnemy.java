package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.Context;
import edu.hitsz.strategy.ShootOne;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft {
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setDirection(1);
        setPower(30);
    }

    /**攻击方式 */
    private Context enemyShooter = new Context(new ShootOne());

    private int shootCD = 0;
    private static final int MAXSHOOTCD = 2;


    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        shootCD++;
        if(shootCD < MAXSHOOTCD)
        {
            return new LinkedList<>();
        }
        else {
            shootCD = 0;
            return enemyShooter.executeStrategy(this);
        }
    }

}

