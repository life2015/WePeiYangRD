package com.twtstudio.navigationbottom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by : youngkaaa on 2016/8/31.
 * Contact me : 645326280@qq.com
 */
public class MyNavigationBottom extends LinearLayout implements View.OnClickListener {
    private static float BOTTOM_HEIGHT_VAL;
    public static final int BOTTOM_TAB_INSET_TOP = 12; //12dp
    public static final int BOTTOM_TAB_INSET_LEFT = 12; //12dp
    public static final int BOTTOM_TAB_INSET_RIGHT = 12; //12dp
    public static final int BOTTOM_TAB_INSET_BOTTOM = 12; //12dp
    public static final float BOTTOM_TAB_BG_SCALE = 1.1f;

    public static final int LEVEL_INACTIVE = 1;
    public static final int LEVEL_ACTIVE = 2;

    public static final int DEFAULT_SCROLL_DURATION = 500;
    private static final int DEFAULT_SELECTED_INDEX=0;

    private List<BottomMenuItem> items;
    private List<ImageView> bottomTabs = new ArrayList<>();
    private List<ImageView> bottomTabsTemp = new ArrayList<>();

    private float screenWidth;
    public static float screenHeight;
    private float bottomHeight;
    private float bottomWidth;
    private int bottomTabNum;
    private int bottomBgColor = getContext().getResources().getColor(R.color.bottomBg);
    public static float perWidth = 0;
    public static float perHeight = 0;
//    private float insetLeft;
//    private float insetTop;
//    private float insetRight;
//    private float insetBottom;
    private int selectedIndex;
    private int defaultSelectedIndex;
    private int lastSelectedIndex;
    private boolean isClickedNewPosition;
    private int scrollDuration;
    private OnTabSelectListener listener;


    public MyNavigationBottom(Context context) {
        super(context);
        inits(context, null);
    }

    public MyNavigationBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        inits(context, attrs);
    }

    public MyNavigationBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inits(context, attrs);
    }

    private void inits(Context context, AttributeSet set) {

        if (set == null) {
            throw new InflateException("MyNavigationBottom must be using in xml!");
        }

        screenWidth = MetricUtils.getScrWidth(getContext());
        screenHeight = MetricUtils.getScrHeight(getContext());

        Log.d("kklog", "inits() screenWidth==>" + screenWidth);
        Log.d("kklog", "inits() screenHeight==>" + screenHeight);

        //default height equals 56dp
        BOTTOM_HEIGHT_VAL = MetricUtils.dp2px(getContext(), 56);

        //set bottom background color！
//        setBackgroundColor(bottomBgColor);
        //set linearlayout' s orientation to horizontal!
        setOrientation(HORIZONTAL);

        TypedArray array = context.obtainStyledAttributes(set, R.styleable.MyNavigationBottom);
        defaultSelectedIndex = array.getInt(R.styleable.MyNavigationBottom_nb_defaultIndex, DEFAULT_SELECTED_INDEX);
        scrollDuration = array.getInt(R.styleable.MyNavigationBottom_nb_scrollDuration, DEFAULT_SCROLL_DURATION);

        int resId = array.getResourceId(R.styleable.MyNavigationBottom_nb_xmlResource, 0);
//        insetLeft = array.getDimension(R.styleable.MyNavigationBottom_nb_insetLeft, 0f);
//        Log.d("kklog", "inits() insetLeft==>" + insetLeft);
//        insetTop = array.getDimension(R.styleable.MyNavigationBottom_nb_insetTop, 0);
//        insetRight = array.getDimension(R.styleable.MyNavigationBottom_nb_insetRight, 0);
//        insetBottom = array.getDimension(R.styleable.MyNavigationBottom_nb_insetBottom, 0);

        if (resId == 0) {
            throw new InflateException("MyNavigationBottom must has the attribute of nb_xmlResource");
        }
        array.recycle();

        items = MenuParser.inflateMenu(getContext(), resId);
        bottomTabNum = items.size();
        if (bottomTabNum < 3 || bottomTabNum > 5) {
            throw new IllegalArgumentException("Bottom tab's number should between 3 and 5!");
        }
        for (int i = 0; i < bottomTabNum; i++) {
            ImageView imageView = new ImageView(getContext());
            bottomTabsTemp.add(imageView);
            addView(imageView, i);
        }

        lastSelectedIndex = defaultSelectedIndex;
        selectedIndex = defaultSelectedIndex;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int measuredModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int measuredSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredSizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //measure all children
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        bottomWidth = measuredSizeWidth;
        bottomHeight = measuredSizeHeight;
        if (measuredModeWidth == MeasureSpec.AT_MOST && measuredModeHeight == MeasureSpec.AT_MOST) {
            bottomWidth = screenWidth;
            bottomHeight = BOTTOM_HEIGHT_VAL;
        } else if (measuredModeHeight == MeasureSpec.AT_MOST) {
            bottomHeight = BOTTOM_HEIGHT_VAL;
            bottomWidth = measuredSizeWidth;
        } else if (measuredModeWidth == MeasureSpec.AT_MOST) {
            bottomWidth = screenWidth;
            bottomHeight = measuredSizeHeight;
        } else if (measuredModeWidth == MeasureSpec.EXACTLY) {
            if (measuredSizeWidth < (int) screenWidth) {
                throw new IllegalArgumentException("NavigationBottom's width must be match_parent,which couldn't less than screenSize!");
            }
            if (measuredSizeWidth > (int) screenWidth) {
                bottomWidth = screenWidth;
            }
        }
        setMeasuredDimension((int) bottomWidth, (int) bottomHeight);
    }

    private void makeTabs() {
        perWidth = bottomWidth / bottomTabNum;
        perHeight = bottomHeight;
//        if (insetTop == 0) {
//            insetTop = (int) MetricUtils.dp2px(getContext(), BOTTOM_TAB_INSET_TOP);
//        }
//        if (insetBottom == 0) {
//            insetBottom = insetTop;
//        }
//        if (insetLeft == 0) {
//            float drawableHeight = perHeight - insetTop - insetBottom;
//
//            float drawableWidth = drawableHeight * BOTTOM_TAB_BG_SCALE;
//
//            insetLeft = (int) ((perWidth - drawableWidth) / 2);
//        }
//        if (insetRight == 0) {
//            insetRight = insetLeft;
//        }
//        if ((insetLeft + insetRight) >= perWidth) {
//            throw new IllegalArgumentException("xml attribute of \"nb_insetLeft\" or \"nb_insetRight\" is too large!");
//        }
//
//        if ((insetTop + insetBottom) >= perHeight) {
//            throw new IllegalArgumentException("xml attribute of \"nb_insetTop\" or \"nb_insetBottom\" is too large!");
//        }



        for (int i = 0; i < bottomTabNum; i++) {
            checkTabInsets(i);

            float insetLeft;
            float insetTop;
            float insetRight;
            float insetBottom;
            insetLeft=items.get(i).getInsetLeft();
            insetTop=items.get(i).getInsetTop();
            insetRight=items.get(i).getInsetRight();
            insetBottom=items.get(i).getInsetBottom();

            LayoutParams params = new LayoutParams((int) perWidth, (int) perHeight);
            params.width = (int) perWidth;
            params.height = (int) perHeight;
            params.gravity = Gravity.CENTER;
            final ImageView imageView = bottomTabsTemp.get(i);

            imageView.setLayoutParams(params);
            final Drawable drawable = makeDrawable(i, (int) insetLeft, (int) insetTop, (int) insetRight, (int) insetBottom);
            imageView.setBackgroundDrawable(drawable);
            bottomTabs.add(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selectedIndex) {
                        selectedIndex = finalI;
                        lastSelectedIndex = selectedIndex;
                        isClickedNewPosition = false;
                    } else {
                        isClickedNewPosition = true;
                        lastSelectedIndex = selectedIndex;  //save last select index
                        selectedIndex = finalI;   //update newest selected index to variable of selectedIndex
                    }

                    listener.onTabSelected(selectedIndex, view);

                    if (isClickedNewPosition) {
                        changeStatus(finalI);
                        changeStatus(lastSelectedIndex);
                    }
                }
            });

        }
    }

    private void checkTabInsets(int i) {
        float insetLeft;
        float insetTop;
        float insetRight;
        float insetBottom;
        insetLeft=items.get(i).getInsetLeft();
        insetTop=items.get(i).getInsetTop();
        insetRight=items.get(i).getInsetRight();
        insetBottom=items.get(i).getInsetBottom();

        Log.d("kklog","checkTabInsets1() insetLeft==>"+insetLeft);
        Log.d("kklog","checkTabInsets1() insetTop==>"+insetTop);
        Log.d("kklog","checkTabInsets1() insetRight==>"+insetRight);
        Log.d("kklog","checkTabInsets1() insetBottom==>"+insetBottom);

        if (insetTop == 0) {
            insetTop = (int) MetricUtils.dp2px(getContext(), BOTTOM_TAB_INSET_TOP);
        }
        if (insetBottom == 0) {
            insetBottom = insetTop;
        }
        if (insetLeft == 0) {
            float drawableHeight = perHeight - insetTop - insetBottom;

            float drawableWidth = drawableHeight * BOTTOM_TAB_BG_SCALE;

            insetLeft = (int) ((perWidth - drawableWidth) / 2);
        }
        if (insetRight == 0) {
            insetRight = insetLeft;
        }
        if ((insetLeft + insetRight) >= perWidth) {
            throw new IllegalArgumentException("xml attribute of \"nb_insetLeft\" or \"nb_insetRight\" is too large!");
        }

        if ((insetTop + insetBottom) >= perHeight) {
            throw new IllegalArgumentException("xml attribute of \"nb_insetTop\" or \"nb_insetBottom\" is too large!");
        }
        items.get(i).setInsetLeft(insetLeft);
        items.get(i).setInsetTop(insetTop);
        items.get(i).setInsetRight(insetRight);
        items.get(i).setInsetBottom(insetBottom);
        Log.d("kklog","checkTabInsets2() insetLeft==>"+insetLeft);
        Log.d("kklog","checkTabInsets2() insetTop==>"+insetTop);
        Log.d("kklog","checkTabInsets2() insetRight==>"+insetRight);
        Log.d("kklog","checkTabInsets2() insetBottom==>"+insetBottom);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        makeTabs();
        int childLeft = 0;
        View childView = null;
        for (int i = 0; i < bottomTabNum; i++) {
            childView = getChildAt(i);
            childView.layout(childLeft, 0, childLeft + (int) perWidth, (int) perHeight);
            childLeft += perWidth;
        }
    }

    private Drawable makeDrawable(int i, int insetLeft, int insetTop, int insetRight, int insetBottom) {
        Drawable inActiveDrawable = getContext().getResources().getDrawable(items.get(i).getInActiveResId());
        Drawable activeDrawable = getContext().getResources().getDrawable(items.get(i).getActiveResId());
        InsetDrawable bgDrawableInactive = null;
        InsetDrawable bgDrawableActive = null;
        if (i != defaultSelectedIndex) {
            bgDrawableInactive = new InsetDrawable(inActiveDrawable, insetLeft, insetTop, insetRight, insetBottom);
            bgDrawableActive = new InsetDrawable(activeDrawable, insetLeft, insetTop, insetRight, insetBottom);
        } else {
            bgDrawableInactive = new InsetDrawable(activeDrawable, insetLeft, insetTop, insetRight, insetBottom);
            bgDrawableActive = new InsetDrawable(inActiveDrawable, insetLeft, insetTop, insetRight, insetBottom);
        }

        LevelListDrawable levelListDrawable = new LevelListDrawable();
        levelListDrawable.addLevel(0, LEVEL_INACTIVE, bgDrawableInactive);
        levelListDrawable.addLevel(0, LEVEL_ACTIVE, bgDrawableActive);


        return levelListDrawable;
    }



    public void setOnTabListener(OnTabSelectListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }


    @Override
    public void onClick(View view) {
        listener.onTabSelected(selectedIndex, view);
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setDefaultIndex(int index) {
        defaultSelectedIndex = index <= 0 ? 0 : index;
    }

    private boolean checkIfOneSelected() {
        int count = 0;
        for (int i = 0; i < bottomTabNum; i++) {
            ImageView imageView1 = bottomTabs.get(i);
            Drawable drawable1 = imageView1.getBackground();
            int level = ((LevelListDrawable) drawable1).getLevel();
            if (level == LEVEL_ACTIVE) {
                count++;
            }
        }
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    private void changeStatus(int i) {
        ImageView imageView1 = bottomTabs.get(i);
        Drawable drawable1 = imageView1.getBackground();
        int level = ((LevelListDrawable) drawable1).getLevel();
        if (level == LEVEL_INACTIVE || level == 0) {
            drawable1.setLevel(LEVEL_ACTIVE);
        } else {
            drawable1.setLevel(LEVEL_INACTIVE);
        }
    }

    public int getDefaultSelectedIndex() {
        return defaultSelectedIndex;
    }



    public void hide() {
        smoothScroll(this,bottomHeight,scrollDuration);
    }

    public void show(){
        smoothScroll(this,0,scrollDuration);
    }

    private void smoothScroll(Object target, float delta, int duration) {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(target,"translationY",delta);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }


}
