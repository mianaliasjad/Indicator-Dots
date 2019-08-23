package com.gamemalt.indicatordots;


import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Stack;
import com.gamemalt.indicatordots.R;


public class IndicatorDots extends LinearLayout {

    @IntDef({IndicatorType.FILL, IndicatorType.FILL_WITH_ANIMATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorType {
        int FILL = 1;
        int FILL_WITH_ANIMATION = 2;
    }

    private Stack<View> stack = new Stack<>();


    private static final int DEFAULT_PIN_LENGTH = 0;
    private static final int DEFAULT_ANIMATION_DURATION = 100;


    private int mDotDiameter;
    private int mDotSpacing;

    private int mDotColor;

    private int mInitialPinLength;
    private int mIndicatorType;


    public IndicatorDots(Context context) {
        this(context, null);
    }

    public IndicatorDots(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorDots(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorDots);


        try {
            mDotDiameter = (int) typedArray.getDimension(R.styleable.IndicatorDots_dotDiameter, getResources().getDimension(R.dimen.dot_diameter));
            mDotSpacing = (int) typedArray.getDimension(R.styleable.IndicatorDots_dotSpacing, getResources().getDimension(R.dimen.dot_spacing));

            mInitialPinLength = typedArray.getInt(R.styleable.IndicatorDots_pinLength, DEFAULT_PIN_LENGTH);
            mIndicatorType = typedArray.getInt(R.styleable.IndicatorDots_indicatorType, IndicatorType.FILL_WITH_ANIMATION);
            mDotColor = typedArray.getColor(R.styleable.IndicatorDots_dotColor, getResources().getColor(R.color.dots_defaultColor));
        } finally {
            typedArray.recycle();
        }

        initView(context);
    }

    public void setDotColor(int mDotColor) {
        this.mDotColor = mDotColor;
    }

    private void initView(Context context) {


        if (mInitialPinLength != 0) {
            for (int i = 0; i < mInitialPinLength; i++) {

                CircleView circleView = getOneDot();
                addView(circleView);
                stack.push(circleView);

            }
        }

        if (mIndicatorType == IndicatorType.FILL_WITH_ANIMATION) {
            LayoutTransition layoutTransition = new LayoutTransition();

            layoutTransition.setDuration(DEFAULT_ANIMATION_DURATION);
            layoutTransition.setStartDelay(LayoutTransition.APPEARING, 0);
//            layoutTransition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
//            layoutTransition.setStartDelay(LayoutTransition.CHANGING, 0);
            layoutTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
//            getLayoutTransition().setStartDelay(LayoutTransition.DISAPPEARING, 0);


            setLayoutTransition(layoutTransition);

        }
    }


    private CircleView getOneDot() {
        final CircleView circleView1 = new CircleView(getContext());
        circleView1.setDotColor(mDotColor);
        LayoutParams params = new LayoutParams(mDotDiameter,
                mDotDiameter);
        params.setMargins(mDotSpacing, 0, mDotSpacing, 0);
        circleView1.setLayoutParams(params);
        return circleView1;
    }


    public void incrementDot() {
        final CircleView circleView1 = getOneDot();

        addView(circleView1);
        stack.push(circleView1);


        if (mIndicatorType == IndicatorType.FILL_WITH_ANIMATION) {
            circleView1.post(new Runnable() {
                @Override
                public void run() {
                    circleView1.startAnimation(AnimUtils.appearingAnim(350, 4.5f));
                }
            });
        }

    }


  /*  public void fallDownAnimation() {




//        int[] arr={500,800,600,800};
//        int[] arr={800,600,750,550};
        final int[] arr = {400, 300, 350, 250, 300, 200, 250, 150};

//        final int[] arr = {150, 250, 200, 300, 250, 350, 300, 400};

        boolean isNext = false;
        for (int i = 0; i < getChildCount(); i++) {
            isNext = !isNext;
            final View v = getChildAt(i);
            final int finalI = i;

            v.post(new Runnable() {
                @Override
                public void run() {
                    v.animate()
                            .translationY(50)
                            .setInterpolator(new AccelerateInterpolator())
                            .setDuration(arr[finalI % arr.length])
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {


                                    if (getChildCount() % 2 == 0) {
                                        if (getChildCount() - 1 == finalI) {
                                            resetDots();
                                            //Log.d("finalI", "reseting");
                                        }
                                    } else {
                                        if (getChildCount() - 2 == finalI) {
                                            resetDots();
                                            //Log.d("finalI", "reseting");
                                        }
                                    }
//                                 removeView(v);

                                }


                            });
                }
            });

        }

//        resetDots();


//        for (int i = 0; i <= viewStack.size(); i++) {
//            final View view = viewStack.pop();
//            view.animate()
//                    .translationY(1000)
//                    .setInterpolator(new AccelerateInterpolator())
//                    .setDuration(i * 50)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            removeView(view);
//
//                        }
//                    });
//
//        }

    } */


    public void decrementDot() {

        if (!stack.isEmpty()) {
            final View v = stack.pop();

            final ScaleAnimation scaleAnimation = AnimUtils.disAppearingAnim(130);
            scaleAnimation.setFillEnabled(true);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {


                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            removeView(v);

                        }
                    });
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            v.post(new Runnable() {
                @Override
                public void run() {
                    v.startAnimation(scaleAnimation);

                }
            });
        }


    }



    public void setWidth(int widthPx) {
        this.getLayoutParams().width = widthPx;
    }

    public void resetDots() {
        removeAllViews();
    }

    public void resetDotsWithAnim() {

        while (!stack.isEmpty()) {
            final View view = stack.pop();
            if (view == null)
                return;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
            scaleAnimation.setFillEnabled(true);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            removeView(view);

                        }
                    });


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            scaleAnimation.setDuration(130);
            view.startAnimation(scaleAnimation);
        }

    }


    public int getInitialPinLength() {
        return mInitialPinLength;
    }

    public void setInitialPinLength(int pinLength) {
        mInitialPinLength = pinLength;
    }

    public
    @IndicatorType
    int getIndicatorType() {
        return mIndicatorType;
    }

    public void setIndicatorType(@IndicatorType int type) {
        mIndicatorType = type;
    }
}