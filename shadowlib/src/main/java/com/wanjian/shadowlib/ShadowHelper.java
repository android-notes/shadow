package com.wanjian.shadowlib;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import android.view.View;


public class ShadowHelper {
    private static final float RATIO = 1.3f;//绘制矩形的区域至少要距离view边框Ratio倍blur,否定导致裁剪掉

    private static Path sPath = new Path();
    private static Paint sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static RectF sRectF = new RectF();


    public static void draw(Canvas canvas, View view, Config config) {
        Check.ifNull(canvas);
        Check.ifNull(view);
        Check.ifNull(config);

        View parent = (View) view.getParent();
        if (parent.getLayerType() != View.LAYER_TYPE_SOFTWARE) {
            parent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            return;
        }

        int count = canvas.save();
        int viewHeight = view.getHeight();
        int viewWidth = view.getWidth();
        float padding = config.radius * RATIO;
        int xOffset = config.xOffset;
        int yOffset = config.yOffset;

        initPath(viewWidth, viewHeight, config);

        try {
            /*
                clipPath时部分4.0 手机会抛出如下异常, 比如
                OPPO X907 cpu高通骁龙Snapdragon MSM8260 内存1GB 系统版本4.0.3 分辨率800x480
                FATAL EXCEPTION:main
                java.lang.UnsupportedOperationException
                at android.view.GLES20Canvas.clipPath(GLES20Canvas.java:429)
            */
            canvas.clipPath(sPath, Region.Op.REPLACE);
        } catch (Exception e) {
            Log.e("shadow", "不支持clipPath");
            e.printStackTrace();
            canvas.restoreToCount(count);
            config.recycle();
            return;

        }

        sRectF.left = xOffset - padding;
        sRectF.top = yOffset - padding;
        sRectF.right = viewWidth + xOffset + padding;
        sRectF.bottom = viewHeight + yOffset + padding;

        canvas.clipRect(sRectF, Region.Op.REVERSE_DIFFERENCE);

        canvas.translate(xOffset, yOffset);

        sPaint.setColor(config.color);
        sPaint.setMaskFilter(new BlurMaskFilter(config.radius, BlurMaskFilter.Blur.NORMAL));

        canvas.drawPath(sPath, sPaint);

        canvas.restoreToCount(count);
        config.recycle();
    }


    /**
     * 圆角path
     */
    private static void initPath(int w, int h, Config config) {

        float lt = config.leftTopCorner;
        float rt = config.rightTopCorner;
        float rb = config.rightBottomCorner;
        float lb = config.leftBottomCorner;
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

        sPath.reset();

        sRectF.left = 0;
        sRectF.top = 0;
        sRectF.right = lt * 2;
        sRectF.bottom = lt * 2;
        sPath.arcTo(sRectF, 180, 90, false);

        sRectF.left = w - rt * 2;
        sRectF.top = 0;
        sRectF.right = w;
        sRectF.bottom = rt * 2;
        sPath.arcTo(sRectF, -90, 90, false);

        sRectF.left = w - rb * 2;
        sRectF.top = h - rb * 2;
        sRectF.right = w;
        sRectF.bottom = h;
        sPath.arcTo(sRectF, 0, 90, false);

        sRectF.left = 0;
        sRectF.top = h - lb * 2;
        sRectF.right = lb * 2;
        sRectF.bottom = h;
        sPath.arcTo(sRectF, 90, 90, false);

        sPath.close();

    }


}
