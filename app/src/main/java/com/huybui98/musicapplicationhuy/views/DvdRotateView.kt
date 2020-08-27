package com.huybui98.musicapplicationhuy.views

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is custom view class for dvd view
 */

class DvdRotateView(context: Context?, attrs: AttributeSet?) :
    de.hdodenhof.circleimageview.CircleImageView(context, attrs) {

    companion object {
        private const val START = 0.0F
        private const val END = 360.0F
        private const val ANIM_MODE = "rotation"
        private const val DURATION = 20000L
    }

    private var animRotate = ObjectAnimator()

    internal fun startAnim() {
        if (animRotate.isStarted) {
            animRotate.end()
        }
        animRotate = ObjectAnimator.ofFloat(this, ANIM_MODE, START, END)
        animRotate.duration = DURATION
        animRotate.repeatCount = ObjectAnimator.INFINITE
        animRotate.repeatMode = ObjectAnimator.RESTART
        animRotate.start()
    }

    internal fun pauseAnim() {
        animRotate.pause()
    }

    internal fun resumeAnim() {
        animRotate.resume()
    }

    internal fun endAnim() {
        animRotate.end()
    }
}
