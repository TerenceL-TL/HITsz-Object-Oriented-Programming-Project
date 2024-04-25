package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft{
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 20;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    @Override
    public List<BaseBullet> shoot() {

        List<BaseBullet> res = new LinkedList<>();
        BaseBullet bullet;

        int x = this.getLocationX();
        int y = this.getLocationY();
        double speed = 10;

        for(int i=0; i<shootNum / 2; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹环形分散


            int speedX = 2*i-shootNum/2+1;
            int speedY = (int)(direction*Math.sqrt(speed*speed - speedX*speedX));
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        direction = - direction;
        for(int i=0; i<shootNum / 2; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹环形分散


            int speedX = 2*i-shootNum/2+1;
            int speedY = (int)(direction*Math.sqrt(speed*speed - speedX*speedX));
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }

        return res;
    }
}
