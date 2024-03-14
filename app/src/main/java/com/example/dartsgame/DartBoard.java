package com.example.dartsgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class DartBoard {
    public void draw(Context context, Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int maxRadius = Math.min(width, height) / 2;

        // Dibujar los segmentos
        int numRings = 5; // Número de anillos (incluido el centro)
        int segmentNum = 20; // Número de segmentos por anillo
        int segmentAngle = 360 / segmentNum;

        for (int i = 0; i < numRings; i++) {
            int ringRadius;

            if (i == numRings - 1) {
                ringRadius = (int) (((maxRadius) * (numRings - i) / numRings) *0.8);
            } else if (i  == numRings - 3) {
                ringRadius = (int) (((maxRadius) * (numRings - i) / numRings) *0.8);
            } else if (i  == 0){
                ringRadius = (int) (((maxRadius) * (numRings - i) / numRings) *0.9);
            } else {
                ringRadius = maxRadius * (numRings - i) / numRings;
            }

            for (int j = 0; j < segmentNum; j++) {
                if (i % 2 == 0) { // Anillos pares
                    if (j % 2 == 0) { // Segmentos pares
                        paint.setColor(ContextCompat.getColor(context, R.color.red));

                    } else {
                        paint.setColor(ContextCompat.getColor(context, R.color.green));
                    }
                } else { // Anillos impares
                    if (j % 2 == 0) {
                        paint.setColor(ContextCompat.getColor(context, R.color.black));
                    } else {
                        paint.setColor(ContextCompat.getColor(context, R.color.white));
                    }
                }

                int startAngle = (j * segmentAngle + (segmentAngle / 2)) % 360; // Centra el segmento
                int sweepAngle = segmentAngle;
                canvas.drawArc(centerX - ringRadius, centerY - ringRadius, centerX + ringRadius, centerY + ringRadius,
                        startAngle, sweepAngle, true, paint);
            }
        }
    }

    public void update() {
    }
}
