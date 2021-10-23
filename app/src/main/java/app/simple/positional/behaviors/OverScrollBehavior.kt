package app.simple.positional.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class OverScrollBehavior(context: Context, attributeSet: AttributeSet) :
        CoordinatorLayout.Behavior<View>(context, attributeSet) {

    /**
     * Used to store all [SpringAnimation] objects used to animate
     * sub views inside the NestedScrollView so that they can be
     * cancelled accordingly once they are not needed.
     *
     * Cancel the views in [onStartNestedScroll] and once all
     * the animations are cancelled, clear the list since
     * these objects will then be obsolete and to avoid any
     * exceptions.
     *
     * Add all newly created animation objects in [moveToDefPosition]
     * called inside [onStopNestedScroll] on [onNestedPreFling].
     */
    private val animationObjects = arrayListOf<SpringAnimation>()

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        val group = target as ViewGroup
        val count = group.childCount

        /**
         * Cancel all [SpringAnimation] animations
         */
        for (animation in animationObjects) {
            animation.cancel()
        }

        /**
         * Unnecessary but added as a precautionary measure,
         * clear any animation running over the views
         */
        for (i in 0 until count) {
            with(group.getChildAt(i)) {
                clearAnimation()
                overScrollY = translationY
            }
        }

        /**
         * Clear obsolete [SpringAnimation] objects
         * from the list
         */
        animationObjects.clear()
        return true
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        overScrollY -= dyUnconsumed / OVER_SCROLL_AREA
        val group = target as ViewGroup
        val count = group.childCount
        for (i in 0 until count) {
            val view = group.getChildAt(i)
            view.translationY = overScrollY
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
        /**
         * Smooth animate to 0 when the user stops scrolling.
         *
         * This is where the list will starts to go back once
         * the finger is lifted from the screen.
         */
        moveToDefPosition(target)
    }

    /**
     * [SpringForce.DAMPING_RATIO_NO_BOUNCY] will remove the bouncy effect, bouncy effect
     * is a short lived animation pleasure and not feasible for having to see a view
     * bouncing all the time
     *
     * [SpringForce.STIFFNESS_LOW] will give smooth over-scroll restoration effect
     */
    private fun moveToDefPosition(target: View) {
        val group = target as ViewGroup
        val count = group.childCount
        for (i in 0 until count) {
            val view = group.getChildAt(i)

            val springAnimation = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y)
            springAnimation.spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)

            springAnimation.start()

            /**
             * Add [SpringAnimation] objects in the list
             * so that they can be cancelled later if
             * needed.
             */
            animationObjects.add(springAnimation)
        }
    }

    companion object {
        private const val OVER_SCROLL_AREA = 1.5F
        private var overScrollY = 0F
    }
}