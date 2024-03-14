package com.example.dartsgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private double averageUPS, averageFPS;
    private static final double MAX_UPS = 60;

    private static final double UPS_PERIOD = 1000 / MAX_UPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }
    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas;
        startTime = System.currentTimeMillis();

        // Game loop
        while (isRunning) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                game.update();
                game.draw(canvas);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            updateCount++;
            frameCount++;

            // Sleep game loop thread to not exceed max UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            while (sleepTime < 0 && updateCount < MAX_UPS) {
                game.update();

                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            // Calculate avg UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);

                updateCount = frameCount = 0;

                startTime = System.currentTimeMillis();
            }
        }
    }
}
