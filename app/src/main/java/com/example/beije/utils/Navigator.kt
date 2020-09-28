package com.metiswebdev.veronacalcio.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.beije.ui.detail.DetailScreen
import com.metiswebdev.Hellas.WebViewScreen
import com.metiswebdev.Hellas.main.ui.MainScreen
import com.metiswebdev.Hellas.detail.DetailScreen
import com.metiswebdev.Hellas.newslist.NewsListScreen
import com.metiswebdev.Hellas.newslist.NewsListScreen.Companion.getIntent

interface Navigator {
    fun openMainScreen()
    fun openDetailScreen(id: Int)
}

class AppNavigator(private val context: Context) : Navigator {

    override fun openMainScreen() {
        ContextCompat.startActivity(
            context,
            Intent(context, MainScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun openDetailScreen(id: Int) {
        ContextCompat.startActivity(context, DetailScreen.getIntent(context, id), null)
    }
}
