package edu.hitsz.factory.Enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory{

    static {
        speedX = 0;
        speedY = 10;
        hp = 30;
    } // easy mode settings as default

    private static int speedX;
    public static void setSpeedX(int _speedX)
    {
        speedX = _speedX;
    }
    private static int speedY;
    public static void setSpeedY(int _speedY)
    {
        speedY = _speedY;
    }
    private static int hp;
    public static void setHp(int _hp)
    {
        hp = _hp;
    }
    @Override
    public AbstractAircraft createEnemy()
    {
        return new MobEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                speedY,
                hp);
    }
}
