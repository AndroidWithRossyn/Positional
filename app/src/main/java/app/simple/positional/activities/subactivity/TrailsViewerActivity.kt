package app.simple.positional.activities.subactivity

import android.os.Bundle
import app.simple.positional.R
import app.simple.positional.activities.main.BaseActivity
import app.simple.positional.ui.subpanels.Trails
import app.simple.positional.util.ConditionUtils.isNull

class TrailsViewerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        if (savedInstanceState.isNull()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.sub_container, Trails.newInstance(), "trails")
                .commit()
        }
    }
}