package com.example.dartsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    GameLoop gameLoop;
    DartBoard dartBoard;
    Context context;
    public Game(Context context) {
        super(context);

        // Get surface holder and callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        dartBoard = new DartBoard();

        // View focusable for capture events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void update() {
        dartBoard.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        dartBoard.draw(getContext(), canvas);
    }

    public void drawUPS(Canvas canvas) {
        String avgNumber = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.yellow);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("UPS: " + avgNumber, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String avgNumber = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.yellow);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("FPS: " + avgNumber, 100, 150, paint);
    }
}
