package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Storage.GameStorage;
import org.springframework.stereotype.Service;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GameScheduler {
    GameStorage gameStorage;
    GameStateService gameStateService;
    public GameScheduler(GameStorage gameStorage,GameStateService gameStateService) {
        this.gameStorage=gameStorage;
        this.gameStateService=gameStateService;
    }

    public void startCountdown() {
        gameStateService.updateToBetsOpen();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long startTime = System.currentTimeMillis();
        Runnable task = new Runnable() {
            int count = 10;
            @Override
            public void run() {
                if (count == 0) {
                    scheduler.shutdown();
                    gameStateService.updateToGame();
                } else {
                    count--;
                    System.out.println("time left: "+count);
                    scheduler.schedule(this, 1, TimeUnit.SECONDS);
                }
            }
        };

        scheduler.schedule(task, 0, TimeUnit.SECONDS);
    }
}
