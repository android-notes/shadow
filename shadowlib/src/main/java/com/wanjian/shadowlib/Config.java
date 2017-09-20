package com.wanjian.shadowlib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2017/9/14.
 */

public class Config {
    private static List<Config> sConfigs = new ArrayList<>();

    private Config() {
    }

    public static Config obtain() {
        if (sConfigs.isEmpty()) {
            return new Config();
        }
        return sConfigs.remove(sConfigs.size() - 1);
    }

    public static Config obtain(Config config) {
        Config b = obtain();
        b.color = config.color;
        b.xOffset = config.xOffset;
        b.yOffset = config.yOffset;
        b.radius = config.radius;
        b.leftTopCorner = config.leftTopCorner;
        b.rightTopCorner = config.rightTopCorner;
        b.rightBottomCorner = config.rightBottomCorner;
        b.leftBottomCorner = config.leftBottomCorner;
        return b;
    }

    int color;
    int xOffset;
    int yOffset;
    float radius;
    int leftTopCorner;
    int rightTopCorner;
    int rightBottomCorner;
    int leftBottomCorner;

    /**
     * @param color 阴影颜色
     * @return this
     */
    public Config color(int color) {
        this.color = color;
        return this;
    }

    /**
     * @param xOffset x轴偏移量
     * @return this
     */
    public Config xOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    /**
     * @param yOffset y轴偏移量
     * @return this
     */
    public Config yOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    /**
     * @param radius 模糊半径
     * @return this
     */
    public Config radius(float radius) {
        if (radius < 1e-6) {
            radius = 0.01f;
        }
        this.radius = radius;
        return this;
    }

    public Config leftTopCorner(int leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
        return this;
    }

    public Config rightTopCorner(int rightTopCorner) {
        this.rightTopCorner = rightTopCorner;
        return this;
    }

    public Config rightBottomCorner(int rightBottomCorner) {
        this.rightBottomCorner = rightBottomCorner;
        return this;
    }

    public Config leftBottomCorner(int leftBottomCorner) {
        this.leftBottomCorner = leftBottomCorner;
        return this;
    }

    void recycle() {
        if (sConfigs.contains(this)) {
            throw new RuntimeException("build has been recycled!");
        }
        color = 0;
        xOffset = 0;
        yOffset = 0;
        radius = 0;
        leftTopCorner = 0;
        rightTopCorner = 0;
        rightBottomCorner = 0;
        leftBottomCorner = 0;
        if (sConfigs.size() < 50) {
            sConfigs.add(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Config)) {
            return false;
        }
        Config b = ((Config) obj);
        if (obj == this ||
                b.color == color &&
                        b.xOffset == xOffset &&
                        b.yOffset == yOffset &&
                        b.radius == radius &&
                        b.leftTopCorner == leftTopCorner &&
                        b.rightTopCorner == rightTopCorner &&
                        b.rightBottomCorner == rightBottomCorner &&
                        b.leftBottomCorner == leftBottomCorner
                ) {
            return true;
        }

        return false;
    }
}