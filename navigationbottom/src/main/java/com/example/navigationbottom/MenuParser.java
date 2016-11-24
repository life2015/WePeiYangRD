package com.example.navigationbottom;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by : youngkaaa on 2016/8/31.
 * Contact me : 645326280@qq.com
 */
public class MenuParser {
    private static BottomMenuItem item;
    public static List<BottomMenuItem> inflateMenu(Context context, int resId) {
        List<BottomMenuItem> items=null;
        try {
            XmlResourceParser parser = context.getResources().getLayout(resId);
            AttributeSet set = Xml.asAttributeSet(parser);
            String tagName = null;
            int eventType = parser.getEventType();
            do {
                if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals("menu")) {
                    readMenu(context,set);
                    eventType=parser.next();
                    break;
                }
            }
            eventType=parser.next();
            } while (eventType != XmlPullParser.END_DOCUMENT);
            boolean endOfMenu = false;
            while (!endOfMenu){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        tagName=parser.getName();
                        if(tagName.equals("item")){
                            readItem(context,set);
                        }else{
                            break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName=parser.getName();
                        if(tagName.equals("item")){
                            if(hasItem()){
                                if(items==null){
                                  items=new ArrayList<>();
                                }
                                items.add(getItem());
                            }
                        }
                        if(tagName.equals("menu")){
                            endOfMenu=true;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        Log.d("kklog","MenuParser [case XmlPullParser.END_DOCUMENT]");
                        break;
                }
                eventType = parser.next();
            }
    } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    private static void readItem(Context context,AttributeSet set){
        TypedArray array=context.obtainStyledAttributes(set,R.styleable.MyNavigationBottomItem);
        String title=array.getString(R.styleable.MyNavigationBottomItem_android_title);
        int resIdInactive=array.getResourceId(R.styleable.MyNavigationBottomItem_nb_tabInActiveResId,0);
        int resIdActive=array.getResourceId(R.styleable.MyNavigationBottomItem_nb_tabActiveResId,0);
        float insetLeft = array.getDimension(R.styleable.MyNavigationBottomItem_nb_insetLeft, 0f);
        Log.d("kklog", "inits() insetLeft==>" + insetLeft);
        float insetTop = array.getDimension(R.styleable.MyNavigationBottomItem_nb_insetTop, 0);
        float insetRight = array.getDimension(R.styleable.MyNavigationBottomItem_nb_insetRight, 0);
        float insetBottom = array.getDimension(R.styleable.MyNavigationBottomItem_nb_insetBottom, 0);
        Log.d("kklog","readItem() insetLeft==>"+insetLeft);
        Log.d("kklog","readItem() insetTop==>"+insetTop);
        Log.d("kklog","readItem() insetRight==>"+insetRight);
        Log.d("kklog","readItem() insetBottom==>"+insetBottom);





        item=new BottomMenuItem();
        item.setTitle(title);
        item.setActiveResId(resIdActive);
        item.setInActiveResId(resIdInactive);
        item.setInsetLeft(insetLeft);
        item.setInsetTop(insetTop);
        item.setInsetRight(insetRight);
        item.setInsetBottom(insetBottom);
        array.recycle();
    }

    public static int readMenu(Context context,AttributeSet set) {
        TypedArray array=context.obtainStyledAttributes(set,R.styleable.MyNavigationBottom);
        int resId=array.getResourceId(R.styleable.MyNavigationBottom_nb_xmlResource,0);
        array.recycle();
        return resId;
    }

    public static BottomMenuItem getItem(){
        BottomMenuItem item1=item;
        item=null;
        return item1;
    }

    public static boolean hasItem(){
        return item==null?false:true;
    }
}

