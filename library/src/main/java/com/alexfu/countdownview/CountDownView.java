package com.alexfu.countdownview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Locale;

public class CountDownView extends View {
    private static final int HOUR = 3600000;
    private static final int MIN = 60000;
    private static final int SEC = 1000;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    @Nullable private TextAppearanceSpan textAppearanceSpan;
    private Layout textLayout;
    private SpannableStringBuilder spannableString = new SpannableStringBuilder();
    private CountDownTimer timer;
    private long startDuration;
    private long currentDuration;
    private boolean timerRunning;

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        textPaint.setColor(Color.BLACK);

        int textSize;
        int startDuration;
        int textAppearanceRef;
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownView);
        startDuration = ta.getInt(R.styleable.CountDownView_startDuration, 0);
        textSize = ta.getDimensionPixelSize(R.styleable.CountDownView_android_textSize, (int) dpToPx(12, getResources()));
        textAppearanceRef = ta.getResourceId(R.styleable.CountDownView_android_textAppearance, 0);
        ta.recycle();

        textPaint.setTextSize(textSize);
        if (textAppearanceRef != 0) {
            textAppearanceSpan = new TextAppearanceSpan(getContext(), textAppearanceRef);
            textPaint.setTextSize(textAppearanceSpan.getTextSize());
        }
        setStartDuration(startDuration);
    }

    public void setStartDuration(long duration) {
        if (timerRunning) {
            return;
        }
        startDuration = currentDuration = duration;
        updateText(duration);
    }

    public void start() {
        if (timerRunning) {
            return;
        }

        timerRunning = true;
        timer = new CountDownTimer(currentDuration, 1000) {
            @Override public void onTick(long millis) {
                currentDuration = millis;
                updateText(millis);
                invalidate();
            }

            @Override public void onFinish() {
                stop();
            }
        };
        timer.start();
    }

    public void reset() {
        stop();
        setStartDuration(startDuration);
        invalidate();
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
            updateText(currentDuration);
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
        viewState.startDuration = startDuration;
        viewState.currentDuration = currentDuration;
        viewState.timerRunning = timerRunning;
        return viewState;
    }

    @Override protected void onRestoreInstanceState(Parcelable state) {
        CountDownViewState viewState = (CountDownViewState) state;
        super.onRestoreInstanceState(viewState.getSuperState());
        setStartDuration(viewState.startDuration);
        currentDuration = viewState.currentDuration;
        if (viewState.timerRunning) {
            start();
        }
    }

    void updateText(long duration) {
        String text = generateCountdownText(duration);
        textLayout = createTextLayout(text);
    }

    Layout createTextLayout(String text) {
        int textWidth = (int) textPaint.measureText(text);
        int unitTextSize = (int) (textPaint.getTextSize() / 2);
        spannableString.clear();
        spannableString.clearSpans();
        spannableString.append(text);
        if (textAppearanceSpan != null) {
            spannableString.setSpan(textAppearanceSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableString.setSpan(new AbsoluteSizeSpan(unitTextSize), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(unitTextSize), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(unitTextSize), 10, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new StaticLayout(spannableString, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 0, 0, true);
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
