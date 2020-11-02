package com.bw.more.view;

import android.graphics.Point;

public class PointView extends Point {
    public int index;//用于转化密码的下标

    public PointView(int x, int y) {
        super(x, y);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
