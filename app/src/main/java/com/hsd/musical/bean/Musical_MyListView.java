package com.hsd.musical.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by MR.WEN on 2016/9/22.
 */

public class Musical_MyListView extends ListView {
    public Musical_MyListView(Context context) {
        super(context);
    }

    public Musical_MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Musical_MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mCurX,mCurY,mDownX,mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mCurX = ev.getX();
        mCurY = ev.getY();

        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            //记录按下时候的坐标
            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            mDownX = ev.getX();
            mDownY = ev.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(ev.getAction() == MotionEvent.ACTION_UP){
            //在up时判断是否按下和松手的坐标为一个点
            //如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
            if(mDownX==mCurX && mDownY==mCurY){
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

}
