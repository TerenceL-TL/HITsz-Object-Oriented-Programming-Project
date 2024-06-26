package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.factory.Enemy.EliteEnemyFactory;
import edu.hitsz.factory.Enemy.EnemyFactory;
import edu.hitsz.factory.Enemy.MobEnemyFactory;

public class MobGenerator {
    private static volatile MobGenerator instance;
    private MobGenerator()
    {

    }
    double eliteAppearRate = 0.2;
    void setEliteAppearRate(double rate) {eliteAppearRate = rate;}
    double getEliteAppearRate() {return eliteAppearRate;}
    double enemyBuffRate = 1;
    void setEnemyBuffRate(double rate) {enemyBuffRate = rate;}
    double getEnemyBuffRate() {return enemyBuffRate;}

    public static MobGenerator getInstance() {
        if(instance == null)
        {
            synchronized (HeroAircraft.class) {
                if(instance == null) {
                    instance = new MobGenerator();
                }
            }
        }
        return instance;
    }

    public AbstractAircraft createEnemy()
    {
        double dice = Math.random();
        EnemyFactory enemyFactory;
        /**
         * 精英敌机出现概率
         */
        if (dice < eliteAppearRate) {
            enemyFactory = new EliteEnemyFactory();
        }
        else {
            enemyFactory = new MobEnemyFactory();
        }
        return enemyFactory.createEnemy(enemyBuffRate);
    }

}
