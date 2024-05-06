package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.Context;
import edu.hitsz.strategy.ShootOne;
import edu.hitsz.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    // Singleton
    private static volatile HeroAircraft craft;

    // Get singleton instance
    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp)
    {
        if(craft == null)
        {
            synchronized (HeroAircraft.class) {
                if(craft == null) {
                    craft = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }
            }
        }
        return craft;
    }

    private Context heroShooter = new Context(new ShootOne());

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setDirection(-1);
        this.setPower(30);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void setShootMode(Strategy mode)
    {
        heroShooter.setStrategy(mode);
    }
    @Override
    public List<BaseBullet> shoot() {
        return heroShooter.executeStrategy(this);
    }

}
