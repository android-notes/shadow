#### Android实现和web中一样的阴影效果

阴影是画在view外面的，不是画在view上的

##### 使用方式：
自定义view，并在onDraw中执行如下代码

```java

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

```

具体用法参考 `RadiusView.java`

##### 效果

![1](https://raw.githubusercontent.com/android-notes/blogimg/master/shadow1.png)
![1](https://raw.githubusercontent.com/android-notes/blogimg/master/shadow2.png)
![1](https://raw.githubusercontent.com/android-notes/blogimg/master/shadow3.png)
