package com.twtstudio.navigationbottom;

/**
 * Created by : youngkaaa on 2016/8/31.
 * Contact me : 645326280@qq.com
 */
public class BottomMenuItem {
    private int id;
    private String title;
    private int inActiveResId;
    private int activeResId;
    private float insetLeft;
    private float insetTop;
    private float insetRight;
    private float insetBottom;

    public BottomMenuItem(String title,int inActiveResId,int activeResId){
        this.title=title;
        this.inActiveResId=inActiveResId;
        this.activeResId=activeResId;
    }

    public BottomMenuItem(){}


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getActiveResId() {
        return activeResId;
    }

    public void setActiveResId(int activeResId) {
        this.activeResId = activeResId;
    }

    public void setInActiveResId(int inActiveResId) {
        this.inActiveResId = inActiveResId;
    }

    public int getInActiveResId() {
        return inActiveResId;
    }

    public float getInsetLeft() {
        return insetLeft;
    }

    public void setInsetLeft(float insetLeft) {
        this.insetLeft = insetLeft;
    }

    public float getInsetTop() {
        return insetTop;
    }

    public void setInsetTop(float insetTop) {
        this.insetTop = insetTop;
    }

    public float getInsetRight() {
        return insetRight;
    }

    public void setInsetRight(float insetRight) {
        this.insetRight = insetRight;
    }

    public float getInsetBottom() {
        return insetBottom;
    }

    public void setInsetBottom(float insetBottom) {
        this.insetBottom = insetBottom;
    }
}
