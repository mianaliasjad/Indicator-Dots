package com.gamemalt.indicatordots;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class CircleView extends View {


    private Paint mpaint;

    private int dotColor;
    private int widthHeight;


    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        try {
            dotColor = typedArray.getColor(R.styleable.CircleView_dot_color, getContext().getResources().getColor(R.color.white));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }


        init();

    }

    public CircleView(Context context) {
        super(context);
        init();
    }


    private void init() {
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = this.getWidth();
        int h = this.getHeight();

        float ox = (w / 2.0f);
        float oy = (h / 2.0f);

        mpaint.setColor(dotColor);
        canvas.drawCircle(ox, oy, (getWidth() / 2.0f), mpaint);


    }

    public int getWidthHeight() {
        return widthHeight;
    }

    public void setWidthHeight(int widthHeight) {
        this.widthHeight = widthHeight;
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        invalidate();

    }
}
