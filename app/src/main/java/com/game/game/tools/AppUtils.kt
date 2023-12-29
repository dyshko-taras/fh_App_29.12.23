package com.game.game.tools

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.game.game.R

class AppUtils {
    companion object {
        fun shareApp(context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this cool app: ${context.resources.getString(R.string.app_name)}. Download: https://play.google.com/store/apps/details?id=${
                    context.resources.getString(R.string.app_bundle)
                }"
            )
            context.startActivity(intent)
        }

        fun rateApp(context: Context) {
            val uri = Uri.parse("market://details?id=${context.resources.getString(R.string.app_bundle)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
