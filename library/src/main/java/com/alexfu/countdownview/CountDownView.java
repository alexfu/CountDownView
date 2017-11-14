package com.alexfu.countdownview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CountDownView extends View {
    private static final int HOUR = 3600000;
    private static final int MIN = 60000;
    private static final int SEC = 1000;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Layout textLayout;
    private String text;
    private CountDownTimer timer;

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dpToPx(20, getResources()));
        setStartDuration(5000000);
    }

    public void setStartDuration(long duration) {
        timer = new CountDownTimer(duration, 1000) {
            @Override public void onTick(long millis) {
                updateText(millis);
                invalidate();
            }

            @Override public void onFinish() {
                // No op
            }
        };
        updateText(duration);
    }

    public void start() {
        timer.start();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (textLayout == null) {
            textLayout = createTextLayout(text);
        }
        setMeasuredDimension(textLayout.getWidth(), textLayout.getHeight());
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textLayout.draw(canvas);
    }

    void updateText(long duration) {
        text = generateCountdownText(duration);
        textLayout = createTextLayout(text);
    }

    Layout createTextLayout(String text) {
        int textWidth = (int) textPaint.measureText(text);
        return new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
    }

    static String generateCountdownText(long duration) {
        int hr = (int) (duration / HOUR);
        int min = (int) ((duration - (hr * HOUR)) / MIN);
        int sec = (int) ((duration - (hr * HOUR) - (min * MIN)) / SEC);
        return hr + "h " + min + "m " + sec + "s ";
    }

    private static float dpToPx(int dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  resources.getDisplayMetrics());
    }
}
