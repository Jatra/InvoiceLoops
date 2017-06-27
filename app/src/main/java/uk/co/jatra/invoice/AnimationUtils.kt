package uk.co.jatra.invoice

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * https://stackoverflow.com/a/31720191/2512177
 */
object AnimationUtils {
    fun expand(view: View) {
        val initialHeight = view.height
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = view.measuredHeight
        val heightChange = targetHeight - initialHeight

        view.layoutParams.height = view.height
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
                view.layoutParams.height = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    initialHeight + (heightChange * interpolatedTime).toInt()
                }
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        runAnimation(view, animation, heightChange)
    }

    fun collapse(view: View, targetHeight: Int) {
        val initialHeight = view.measuredHeight
        val heightChange = initialHeight - targetHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
                view.layoutParams.height = initialHeight - (heightChange * interpolatedTime).toInt()
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        runAnimation(view, animation, heightChange)
    }

    private fun runAnimation(view: View, animation: Animation, heightChange: Int) {
        animation.duration = (heightChange / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }
}