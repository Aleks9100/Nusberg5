package com.example.nusberg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class PathView extends View {
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(50,50);
        mPath.lineTo(50,getHeight() - 50);
        mPath.lineTo(getWidth() - 50 ,getHeight() - 50);
        mPath.lineTo(getWidth() - 50 , 50);
        mPath.close();

        mPhase++;
        pathDashPathEffect = new PathDashPathEffect(mSharePath,mAdvance,mPhase,mStyle);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(mPath,mPaint);

        invalidate();
    }

    private Paint mPaint;
    private Path mPath;

    private Path mSharePath;
    private float mPhase;
    private float mAdvance;
    private PathDashPathEffect pathDashPathEffect;
    private PathDashPathEffect.Style mStyle;

    public PathView(Context context) {
        super(context);
        Init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Init();
    }

    private void Init() {
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(12);

        mPath = new Path();

        mSharePath = new Path();
        mSharePath.addCircle(8,8,8,Path.Direction.CCW);

        mPhase=0;
        mAdvance=30.0f;
        mStyle=PathDashPathEffect.Style.ROTATE;
        pathDashPathEffect = new PathDashPathEffect(mSharePath,mAdvance,mPhase,mStyle);
    }
}
