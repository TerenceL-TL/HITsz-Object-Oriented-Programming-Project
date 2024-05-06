package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public interface Strategy {
    List<BaseBullet> shoot(AbstractAircraft obj);
}
