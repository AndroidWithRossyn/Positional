package app.simple.positional.util

import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import app.simple.positional.R

object ViewUtils {
    /**
     * Dim the background when PopupWindow shows
     * Should be called from showAsDropDown function
     * because this is when container's parent is
     * initialized
     */
    fun dimBehind(contentView: View) {
        val container = contentView.rootView
        val windowManager = contentView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = container.layoutParams as WindowManager.LayoutParams
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.3f
        windowManager.updateViewLayout(container, layoutParams)
    }

    // @RequiresApi(28)
    /**
     * Adds outline shadows to the view using the accent color
     * of the app
     *
     * @param contentView [View] that needs to be elevated with colored
     *                    shadow
     */
    fun addShadow(contentView: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            contentView.outlineAmbientShadowColor = ContextCompat.getColor(contentView.context, R.color.switch_on)
            contentView.outlineSpotShadowColor = ContextCompat.getColor(contentView.context, R.color.switch_on)
        }
    }

    /**
     * Makes the view go away
     */
    fun View.makeGoAway() {
        this.visibility = View.GONE
    }

    /**
     * Makes the view come back
     */
    fun View.makeVisible() {
        this.visibility = View.VISIBLE
    }
}