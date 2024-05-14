package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.Enemy.BossEnemyFactory;
import edu.hitsz.factory.Enemy.SuperEnemyFactory;
import edu.hitsz.props.BaseProp;
import edu.hitsz.props.BombProp;
import edu.hitsz.props.FireBuffProp;
import edu.hitsz.props.HealProp;

import edu.hitsz.factory.Enemy.EliteEnemyFactory;
import edu.hitsz.factory.Enemy.MobEnemyFactory;
import edu.hitsz.factory.Prop.FireBuffPropFactory;
import edu.hitsz.factory.Prop.BombPropFactory;
import edu.hitsz.factory.Prop.HealPropFactory;

import edu.hitsz.scoreboard.Score;
import edu.hitsz.scoreboard.ScoreBoard;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {


    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    private Difficulty difficulty = Difficulty.EASY;
    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;

    private final SuperEnemyFactory superEnemyFactory;
    private final BossEnemyFactory bossEnemyFactory;
    private final PropGenerator propGenerator_elite;
    private final PropGenerator propGenerator_super;
    private final PropGenerator propGenerator_boss;

    MobGenerator enemyGenerator;


    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;
    /**
     * boss limit
     */
    private int score_limit = 200;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    private boolean boss_flag = false;


    /**
     * 精英敌机出现概率
     */
    private double eliteAppearRate = 0.2;
    private double superAppearRate = 0.2;

    private ScoreBoard scoreBoard;

    public Game() {

        //if(mode == easy)
        {
            EliteEnemyFactory.setSpeedX((int)5);
            EliteEnemyFactory.setSpeedY((int)10);
            EliteEnemyFactory.setHp((int)60);

            MobEnemyFactory.setSpeedX((int)5);
            MobEnemyFactory.setSpeedY((int)10);
            MobEnemyFactory.setHp((int)30);
        }

        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 1000);
        heroAircraft.reset();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        superEnemyFactory = new SuperEnemyFactory();
        bossEnemyFactory = new BossEnemyFactory();

        propGenerator_elite = new PropGenerator(0.3, 0.1, 0.1, 0.0, 1);
        propGenerator_super = new PropGenerator(0.3, 0.2, 0.1, 0.0, 1);
        propGenerator_boss = new PropGenerator(0.3, 0.1, 0.2, 0.2, 3);

        enemyGenerator = MobGenerator.getInstance();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        scoreBoard = new ScoreBoard();
        scoreBoard.setDifficulty(difficulty);
        scoreBoard.readFromFile();
    } // set game difficulty and read scoreboard via difficulty

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;


            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);

                // 分数达到阈值产生Boss
                if (score >= score_limit) {
                    enemyAircrafts.add(bossEnemyFactory.createEnemy());
                    score_limit += 1000;
                    boss_flag = true;
                }

                // 周期性产生超级精英敌机
                if (time % 9000 == 0) {
                    enemyAircrafts.add(superEnemyFactory.createEnemy());
                }
                // 新敌机产生

                if (enemyAircrafts.size() < enemyMaxNumber) {
                    enemyAircrafts.add(enemyGenerator.createEnemy());
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                executorService.shutdown();
                gameOverFlag = true;
                MusicThread musicThread = new MusicThread("src/videos/game_over.wav");
                musicThread.start();
                Menu.musicThread.Halt();
                if (boss_flag) BossEnemy.musicThread.Halt();
                System.out.println("Game Over!");
                String username = JOptionPane.showInputDialog(null, "请输入玩家名（默认为Terence）", "Terence");
                Score score1 = new Score(username, score, new Date());
                scoreBoard.add(score1);
                edu.hitsz.application.ScoreBoard scoreBoard1 = new edu.hitsz.application.ScoreBoard();
                scoreBoard1.setDifficulty(difficulty);
                scoreBoard1.setTableData(scoreBoard);
                Main.cardPanel.add(scoreBoard1.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
                scoreBoard.writeToFile();
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        for (AbstractAircraft enemy : enemyAircrafts)
        {
            enemyBullets.addAll(enemy.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                bullet.vanish();
                heroAircraft.decreaseHp(bullet.getPower());
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    MusicThread bulletHitMusicThread = new MusicThread("src/videos/bullet_hit.wav");
                    bulletHitMusicThread.start();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof EliteEnemy) {
                            score += 20;
                            props.addAll(propGenerator_elite.createProp(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), 0, enemyAircraft.getSpeedY()));
                        }
                        else if (enemyAircraft instanceof SuperEnemy) {
                            score += 30;
                            props.addAll(propGenerator_super.createProp(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), 0, enemyAircraft.getSpeedY()));
                        }
                        else if (enemyAircraft instanceof BossEnemy) {
                            score += 100;
                            props.addAll(propGenerator_boss.createProp(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), 0, 10));
                        }
                        else if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.effect(heroAircraft, enemyAircrafts);
                for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                    if (enemyAircraft.notValid()) { // check boom!
                        if (enemyAircraft instanceof EliteEnemy) {
                            score += 20;
                        } else if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        } else if (enemyAircraft instanceof SuperEnemy) {
                            score += 30;
                        } else if (enemyAircraft instanceof BossEnemy) {
                            score += 100;
                        }
                    }
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(BaseProp::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
