package app.simple.positional.dialogs.app

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.simple.positional.R
import app.simple.positional.adapters.AccentColorAdapter
import app.simple.positional.decorations.views.CustomBottomSheetDialogFragment
import app.simple.positional.preference.MainPreferences

class AccentColor : CustomBottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var accentColorAdapter: AccentColorAdapter
    private var spanCount = 4

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.dialog_color_accent, container, false)

        spanCount = if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            4
        } else {
            7
        }

        recyclerView = view.findViewById(R.id.accent_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        accentColorAdapter = AccentColorAdapter()
        recyclerView.adapter = accentColorAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accentColorAdapter.setOnPaletteChangeListener(object : AccentColorAdapter.Companion.PalettesAdapterCallbacks {
            override fun onColorPressed(source: Int) {
                MainPreferences.setAccentColor(source)
            }
        })
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == MainPreferences.accentColor) {
            requireActivity().recreate()
        }
    }

    companion object {
        fun newInstance(): AccentColor {
            val args = Bundle()
            val fragment = AccentColor()
            fragment.arguments = args
            return fragment
        }
    }
}