package com.enciyo.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.ShareCompat


fun Context.openTwitter(twitterUsername: String) {
    openUrl("https://x.com/${twitterUsername}")
}

fun Context.openUrl(uri: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    } catch (e: Exception) {
        Log.i("MyLogger", "${e.message}")
    }
}

fun Context.share(link:String){
    ShareCompat.IntentBuilder(this)
        .setType("text/plain")
        .setChooserTitle("Share Url")
        .setText(link)
        .startChooser()
}