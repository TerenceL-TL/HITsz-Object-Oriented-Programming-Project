package edu.hitsz.factory.Enemy;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    AbstractAircraft createEnemy();
}
