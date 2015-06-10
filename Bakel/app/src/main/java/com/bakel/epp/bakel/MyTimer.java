package com.bakel.epp.bakel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Nikos on 9/6/2015.
 */
public class MyTimer extends View implements Runnable {
    private final int totalTime = 20; // total time you have for each question
    private int curentTime;
    private Game context;
    private boolean isGameOver = false;


    public MyTimer(Game context) {
        super(context);
        this.context = context;

        resetTimer();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(Color.RED);
        canvas.drawRect(0, 0, (int) (getWidth() * curentTime / (double) totalTime), getHeight(), paint);

        //   Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        for (int i = 0; i < context.getRemainingLifes(); i++) {
            //     canvas.drawBitmap(b, getWidth() / 3, 0, paint);

            Drawable d = getResources().getDrawable(R.drawable.heart);
            d.setBounds(i * getWidth() / 3, 0, (i + 1) * getWidth() / 3, getHeight());
            d.draw(canvas);
        }
    }

    /**
     * curent time resets
     */
    public void resetTimer() {
        curentTime = totalTime;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    public void run() {
        while (curentTime > 0 && !isGameOver) {
            try {
                Thread.sleep(1000);
                curentTime--;
                postInvalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!isGameOver) {
            context.runOnUiThread(new Thread() {
                @Override
                public void run() {
                    context.wrongAnswerFunction();
                    Toast.makeText(context, "out of time", Toast.LENGTH_SHORT);
                }
            });
        }
    }
}
