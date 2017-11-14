package com.alexfu.countdownview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CountDownView extends View {
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private StaticLayout textLayout;
    private String text = "Hello World";

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dpToPx(20, getResources()));
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (textLayout == null || !textLayout.getText().equals(text)) {
            int textWidth = (int) textPaint.measureText(text);
            textLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
        }
        setMeasuredDimension(textLayout.getWidth(), textLayout.getHeight());
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textLayout.draw(canvas);
    }

    private static float dpToPx(int dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  resources.getDisplayMetrics());
    }
}
