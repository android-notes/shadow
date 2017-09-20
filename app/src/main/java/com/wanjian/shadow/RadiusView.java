package com.wanjian.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wanjian.shadowlib.Config;
import com.wanjian.shadowlib.ShadowHelper;

/**
 * Created by wanjian on 2017/9/15.
 * <p>
 * 圆角+阴影 Demo
 */

public class RadiusView extends View {
    private float lt, rt, rb, lb, radius;
    private int xOffset, yOffset;
    private Path path = new Path();
    private int color;

    private Bitmap bitmap;

    public RadiusView(Context context) {
        super(context);
        init();
    }

    public RadiusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.erha);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidate();
            }
        });
    }


    public void setRadius(float lt, float rt, float rb, float lb) {
        this.lt = lt;
        this.rt = rt;
        this.rb = rb;
        this.lb = lb;
        invalidate();
    }

    public void setShadow(int xoffset, int yoffset, float radius, int color) {
        this.xOffset = xoffset;
        this.yOffset = yoffset;
        this.radius = radius;
        this.color = color;
        this.radius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImage(canvas);

        //实现阴影
        ShadowHelper.draw(canvas, this,
                Config.obtain()
                        .color(color)
                        .leftTopCorner((int) lt)
                        .rightTopCorner((int) rt)
                        .leftBottomCorner((int) lb)
                        .rightBottomCorner((int) rb)
                        .radius(radius)
                        .xOffset(xOffset)
                        .yOffset(yOffset)
        );
    }

    private void drawImage(Canvas canvas) {
        canvas.save();
        clipBorder(canvas);

        float scale = Math.max(1f * getWidth() / bitmap.getWidth(), 1f * getHeight() / bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        canvas.drawBitmap(bitmap, matrix, null);

        canvas.restore();

    }

    private void clipBorder(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        if (lt + rt > w) {
            float scale = w / (lt + rt);
            lt *= scale;
            rt *= scale;
            rb *= scale;
            lb *= scale;
        }
        if (rt + rb > h) {
            float scale = h / (rt + rb);
            lt *= scale;
            rt *= scale;
            rb *= scale;
            lb *= scale;
        }

        if (rb + lb > h) {
            float scale = w / (rb + lb);
            lt *= scale;
            rt *= scale;
            rb *= scale;
            lb *= scale;
        }

        if (lt + lb > h) {
            float scale = w / (lt + lb);
            lt *= scale;
            rt *= scale;
            rb *= scale;
            lb *= scale;
        }

        path.reset();
        path.arcTo(new RectF(0, 0, lt * 2, lt * 2), 180, 90, false);
        path.arcTo(new RectF(w - rt * 2, 0, w, rt * 2), -90, 90, false);
        path.arcTo(new RectF(w - rb * 2, h - rb * 2, w, h), 0, 90, false);
        path.arcTo(new RectF(0, h - lb * 2, lb * 2, h), 90, 90, false);

        path.close();
        try {
            canvas.clipPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
