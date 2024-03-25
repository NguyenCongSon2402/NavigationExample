package net.braniumacademy.chapter14.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import net.braniumacademy.chapter14.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}