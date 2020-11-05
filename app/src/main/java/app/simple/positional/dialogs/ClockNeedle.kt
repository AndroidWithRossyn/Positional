package app.simple.positional.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import app.simple.positional.R
import app.simple.positional.adapters.ClockNeedleAdapter
import app.simple.positional.preference.ClockPreferences
import app.simple.positional.ui.Clock
import app.simple.positional.views.CustomBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_clock_needle_skins.*
import java.lang.ref.WeakReference

class ClockNeedle(clock: Clock) : CustomBottomSheetDialog() {

    private val weakReference: WeakReference<Clock> = WeakReference(clock)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_clock_needle_skins, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        needle_skin.adapter = ClockNeedleAdapter(requireContext())
        indicator.attachViewPager(needle_skin)

        needle_skin.currentItem = ClockPreferences().getClockNeedleTheme(requireContext())

        needle_skin.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    ClockPreferences().setClockNeedleTheme(needle_skin.currentItem, requireContext())
                    weakReference.get()?.setNeedle(needle_skin.currentItem)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        weakReference.clear()
    }
}