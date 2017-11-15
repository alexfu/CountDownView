package com.alexfu.countdownview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Locale;

public class CountDownView extends View {
    private static final int HOUR = 3600000;
    private static final int MIN = 60000;
    private static final int SEC = 1000;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Layout textLayout;
    private String text;
    private CountDownTimer timer;
    private long currentTimerDuration;
    private boolean timerRunning;

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dpToPx(20, getResources()));
    }

    public void setStartDuration(long duration) {
        if (timerRunning) {
            return;
        }
        currentTimerDuration = duration;
        updateText(duration);
    }

    public void start() {
        if (timerRunning) {
            return;
        }

        timerRunning = true;
        timer = new CountDownTimer(currentTimerDuration, 1000) {
            @Override public void onTick(long millis) {
                currentTimerDuration = millis;
                updateText(millis);
                invalidate();
            }

            @Override public void onFinish() {
                stop();
            }
        };
        timer.start();
    }

    public void stop() {
        if (!timerRunning) {
            return;
        }

        timerRunning = false;
        timer.cancel();
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

    @Nullable @Override protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        CountDownViewState viewState = new CountDownViewState(superState);
        viewState.currentTimerDuration = currentTimerDuration;
        viewState.timerRunning = timerRunning;
        return viewState;
    }

    @Override protected void onRestoreInstanceState(Parcelable state) {
        CountDownViewState viewState = (CountDownViewState) state;
        super.onRestoreInstanceState(viewState.getSuperState());
        setStartDuration(viewState.currentTimerDuration);
        if (viewState.timerRunning) {
            start();
        }
    }

    void updateText(long duration) {
        text = generateCountdownText(duration);
        textLayout = createTextLayout(text);
    }

    Layout createTextLayout(String text) {
        int textWidth = (int) textPaint.measureText(text);
        int unitTextSize = (int) (textPaint.getTextSize() / 2);
        SpannableString spannedString = new SpannableString(text);
        spannedString.setSpan(new AbsoluteSizeSpan(unitTextSize), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannedString.setSpan(new AbsoluteSizeSpan(unitTextSize), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannedString.setSpan(new AbsoluteSizeSpan(unitTextSize), 10, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new StaticLayout(spannedString, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 0, 0, true);
    }

    static String generateCountdownText(long duration) {
        Locale locale = Locale.getDefault();
        int hr = (int) (duration / HOUR);
        int min = (int) ((duration - (hr * HOUR)) / MIN);
        int sec = (int) ((duration - (hr * HOUR) - (min * MIN)) / SEC);
        return String.format(locale, "%02dh %02dm %02ds", hr, min, sec);
    }

    private static float dpToPx(int dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  resources.getDisplayMetrics());
    }
}
