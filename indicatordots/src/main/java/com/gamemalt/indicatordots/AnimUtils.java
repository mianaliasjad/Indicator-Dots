package com.gamemalt.indicatordots;

import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

class AnimUtils {

    /***
     * anim for making view shaking
     ***/
    static ScaleAnimation appearingAnim(int duration, float overShootInterpolator) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setInterpolator(new OvershootInterpolator(overShootInterpolator));
        return scaleAnimation;
    }

    /***
     * anim for making view dis appearing
     ***/
    static ScaleAnimation disAppearingAnim(int duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, ScaleAnimation.RELATIVE_TO_SELF, 1f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(duration);
        return scaleAnimation;
    }


}
