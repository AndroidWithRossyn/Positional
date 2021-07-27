package app.simple.positional.decorations.popup

import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import app.simple.positional.R
import app.simple.positional.util.ViewUtils
import app.simple.positional.util.ViewUtils.dimBehind

/**
 * A customised version of popup menu that uses [PopupWindow]
 * created to replace ugly material popup menu which does not
 * provide any customizable flexibility. This on the other hand
 * uses custom layout, background, animations and also dims entire
 * window when appears. It is highly recommended to use this
 * and ditch popup menu entirely.
 */
open class BasePopupWindow : PopupWindow() {

    fun init(contentView: View, view: View, xOff: Float, yOff: Float) {
        setContentView(contentView)
        init()
        showAsDropDown(view, xOff.toInt() - width / 2, yOff.toInt() - height / 2, Gravity.START)
    }

    fun init(contentView: View, view: View) {
        setContentView(contentView)
        init()
        showAsDropDown(view, (width / 1.2F * -1).toInt(), height / 8, Gravity.NO_GRAVITY)
    }

    fun init(contentView: View, view: View, gravity: Int, factor: Int) {
        setContentView(contentView)
        init()
        showAsDropDown(view, width / factor * -1, 0, gravity)
    }


    private fun init() {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        contentView.clipToOutline = false
        width = contentView.measuredWidth
        height = contentView.measuredHeight
        animationStyle = R.style.PopupAnimation
        isClippingEnabled = false
        isFocusable = true
        elevation = 50F
        overlapAnchor = true

        ViewUtils.addShadow(contentView)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            setIsClippedToScreen(false)
            setIsLaidOutInScreen(false)
        }
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        dimBehind(contentView)
    }

    override fun showAsDropDown(anchor: View?) {
        super.showAsDropDown(anchor)
        dimBehind(contentView)
    }
}
