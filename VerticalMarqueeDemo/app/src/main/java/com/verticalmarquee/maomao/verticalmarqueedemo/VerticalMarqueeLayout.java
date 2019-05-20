package com.verticalmarquee.maomao.verticalmarqueedemo;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;

import java.util.List;

public class VerticalMarqueeLayout extends ViewAnimator {

    private static final long DEFAULT_TIMER = 2000L;
    private long delayTime = DEFAULT_TIMER;
    private int viewIndex;
    private List<View> views;
    private static Handler handler = new Handler();
    private boolean started;//是否已经开始轮播

    public VerticalMarqueeLayout(Context context) {
        super(context);
        this.init();
    }

    public VerticalMarqueeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.setInAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.vertical_marquee_in));
        this.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.vertical_marquee_out));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void startMarquee() {
        if (this.views != null) {
            if (this.views.size() > 1) {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        VerticalMarqueeLayout.this.viewIndex++;
                        if (VerticalMarqueeLayout.this.viewIndex >= VerticalMarqueeLayout.this.views.size()) {
                            VerticalMarqueeLayout.this.viewIndex = 0;
                        }
                        showNext();
                        VerticalMarqueeLayout.handler.postDelayed(this, delayTime);
                    }
                }, delayTime);
                started = true;
            } else if (this.views.size() > 0) {
                this.viewIndex = 0;
            } else {
                this.viewIndex = 0;
            }
        } else {
            this.viewIndex = 0;
        }

    }

    /**
     * 获取当前显示的View
     * 修改方法名，避免与父类方法重名
     *
     * @return View
     */
    public View getCurView() {
        if (this.views != null && this.viewIndex >= 0 && this.viewIndex < this.views.size()) {
            return this.views.get(this.viewIndex);
        }

        return null;
    }

    /**
     * 获取当前显示View的index
     *
     * @return index
     */
    public int getCurIndex() {
        return this.viewIndex;
    }

    /**
     * 设置轮播的View列表，该方法会自动轮播
     *
     * @param views view列表
     */
    public void setViewList(List<View> views) {
        setViewList(views, DEFAULT_TIMER);
    }

    /**
     * 设置轮播的View列表，该方法会自动轮播
     *
     * @param views     view列表
     * @param delayTime 间歇时间
     */
    public void setViewList(final List<View> views, long delayTime) {
        if (views == null || views.size() == 0) {
            return;
        }
        if (delayTime >= 100) {
            //最少100毫秒,否则为默认值
            this.delayTime = delayTime;
        }
        this.views = views;
        handler.removeCallbacksAndMessages(null);
        started = false;
        post(new Runnable() {
            @Override
            public void run() {
                for (View view : views) {
                    addView(view);
                }
                startMarquee();
            }
        });
    }


    //开始倒计时(轮播),在页面可见并且需要自动轮播的时候调用该方法
    public void startTimer() {
        if (started || views == null || views.size() <= 1) {
            return;
        }
        stopTimer();
        startMarquee();
        Log.d("VerticalMarqueeLayout", "VerticalMarqueeLayout startTimer!");
    }

    //停止倒计时(轮播),如果调用过startTimer();在页面不可见的时候调用该方法停止自动轮播
    public void stopTimer() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            started = false;
            Log.d("VerticalMarqueeLayout", "VerticalMarqueeLayout stopTimer!");
        }
    }
}
