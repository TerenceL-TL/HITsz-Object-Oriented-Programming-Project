package edu.hitsz.factory.Enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements EnemyFactory{
    static {
        speedX = 5;
        speedY = 5;
        hp = 60;
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
    public AbstractAircraft createEnemy(double buffrate)
    {
        return new EliteEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(speedX * (Math.random() < 0.5 ? -1 : 1) * buffrate),
                (int)(speedY * buffrate),
                (int)(hp * buffrate));
    }
}
