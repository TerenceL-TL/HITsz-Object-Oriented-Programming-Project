package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private static HeroAircraft heroAircraft;
    private static int hp = 1000;

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance(100, 100, 0, 0, hp);
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("test decreaseHp method")
    @ParameterizedTest
    @CsvSource({"10", "20", "30"})
    void decreaseHp(int dehp) {
        heroAircraft.decreaseHp(dehp);
        hp = hp - dehp;
        assertEquals(hp,heroAircraft.getHp());

    }
    @DisplayName("test crash method")
    @org.junit.jupiter.api.Test
    void crash() {
        int x = heroAircraft.getLocationX();
        int y = heroAircraft.getLocationY();
        BaseBullet bullet = new EnemyBullet(x,y,0,0,30);
        assertTrue(heroAircraft.crash(bullet));
    }

    @DisplayName("test setLocation method")
    @org.junit.jupiter.api.Test
    void setLocation() {
        int newX = 200;
        int newY = 200;
        heroAircraft.setLocation(200, 200);
        assertTrue(heroAircraft.getLocationX() == newX && heroAircraft.getLocationY() == newY);
    }

}