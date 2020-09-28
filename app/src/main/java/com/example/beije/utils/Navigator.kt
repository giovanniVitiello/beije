package com.example.beije.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import androidx.core.content.ContextCompat
import com.example.beije.ui.detail.DetailScreen
import com.example.beije.ui.master.MasterScreen

interface Navigator {
    fun openMasterScreen()
    fun openDetailScreen(id: Int)
}

class AppNavigator(private val context: Context) : Navigator {

    override fun openMasterScreen() {
        ContextCompat.startActivity(
            context,
            Intent(context, MasterScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun openDetailScreen(id: Int) {
        ContextCompat.startActivity(context, DetailScreen.getIntent(context, id), null)
    }
}
