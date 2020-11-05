package app.simple.positional.menu.clock.configuration

import android.content.Context
import android.view.Gravity
import app.simple.positional.R
import app.simple.positional.preference.ClockPreferences
import app.simple.positional.ui.Clock
import com.github.zawadz88.materialpopupmenu.popupMenu
import kotlinx.android.synthetic.main.frag_clock.*

class MovementType {

    var value = false

    fun setMovementType(context: Context, clock: Clock) {

        value = ClockPreferences().getMovementType(context)

        val popupMenu = popupMenu {
            style = R.style.popupMenu
            dropdownGravity = Gravity.END
            section {
                title = "Movement Type"
                item {
                    label = "Smooth"
                    dismissOnSelect = false
                    icon = if (value) R.drawable.ic_radio_button_checked else R.drawable.ic_radio_button_unchecked
                    callback = {
                        this.icon = R.drawable.ic_radio_button_checked
                        value = true
                        setValue(clock)
                    }
                }
                item {
                    label = "Tick"
                    dismissOnSelect = false
                    icon = if (value) R.drawable.ic_radio_button_unchecked else R.drawable.ic_radio_button_checked
                    callback = {
                        this.icon = R.drawable.ic_radio_button_checked
                        value = false
                        setValue(clock)
                    }
                }
            }
        }

        popupMenu.show(context = context, anchor = clock.clock_menu)
    }

    private fun setValue(clock: Clock) {
        clock.isMovementTypeSmooth = value
        clock.setMotionDelay()
        ClockPreferences().setMovementType(value = value, context = clock.requireContext())
    }
}