import java.util.*;
import java.awt.Graphics;
import java.awt.Image;

public class ZombieProducer {
    final static int MAXSTAGE = 2;
    final static int[] MIDDLEINTERVAL = {8000, 6000, 2000};
    final static int NUMBEROFZOMBIECLASSES = 5;
    final static int DEVIATION = 1000;

    final static String[] ZOMBIES = {"NormalZombie", "ConeHeadZombie", "PoleVaultingZombie", "MetalBucketZombie", "FootballZombie"};
    final static int[] ZOMBIESCORES = {5, 7, 10, 12, 15};
    final static int[] STAGETOTALSCORES = {50, 70, 100};
    final static int[] STAGEAVAILABLEZOMBIES = {3, 4, 5};

    Timer producer;
    Timer imgPresenter;
    int stage;
    int stage_score;
    
    public boolean []stage_complete;
    public boolean total_complete;     // redundancy for readability
    Random rnd;
    GamePanel gp;

    ZombieProducer(Random _rnd, GamePanel _gp){
        rnd = _rnd;
        gp = _gp;
        stage_complete = new boolean[]{false, false, false};
        total_complete = false;
        stage = 0;
        stage_score = STAGETOTALSCORES[stage];
        producer = new Timer();
        imgPresenter = new Timer();
    }

    void start()
    {
        System.out.printf("Producing Zombies\n");
        gp.newStage = true;
                imgPresenter.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        gp.newStage = false;
                    }
                }, 3000);
        nextZombie(MIDDLEINTERVAL[stage]);
    }

    void nextZombie(int this_interval)
    {
        // Determin whether to change stage or complete
        if(total_complete) return;
        

        // Determine Zombie Type and generate zombie
        int zombie_index = rnd.nextInt(STAGEAVAILABLEZOMBIES[stage]);
        int lane = rnd.nextInt(5);
        Zombie z = Zombie.getZombie(ZOMBIES[zombie_index], gp, lane);
        gp.getLaneZombies().get(lane).add(z);
        gp.add(z);


        // Determine the zombie after this zombie interval
        int tmp_interval = MIDDLEINTERVAL[stage] + (int) (DEVIATION * rnd.nextGaussian());
        
  
        stage_score -= ZOMBIESCORES[zombie_index];
        if(stage_score < 0)
        {
            stage_complete[stage] = true;
            tmp_interval = 10000;
            System.out.printf("Stage %d Complete\n", stage);
            if(stage == 2)
            {
                total_complete = true;
            }
            else
            {
                stage += 1;
                stage_score = STAGETOTALSCORES[stage];
                if(stage <= 1){
                    gp.newStage = true;
                    imgPresenter.schedule(new TimerTask(){
                        @Override
                        public void run() {
                            gp.newStage = false;
                        
                        }
                    }, 5000);
                }
                else{
                    gp.finalStage = true;
                    imgPresenter.schedule(new TimerTask(){
                        @Override
                        public void run() {
                            gp.finalStage = false;
                        }
                    }, 5000);
                }
            }
        }

        // variable used in timertask must be final
        final int next_interval = tmp_interval;
        System.out.printf("Next Zombie in %d ms\n", next_interval);


        producer.schedule(new TimerTask(){
            @Override
            public void run() {
                nextZombie(next_interval);
            }
        }, this_interval);
        
    }
}
