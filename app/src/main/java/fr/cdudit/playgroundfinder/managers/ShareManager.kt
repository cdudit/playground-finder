package fr.cdudit.playgroundfinder.managers

import android.content.Context
import android.content.Intent
import android.net.Uri

object ShareManager {
    private const val SMS_BODY_EXTRA = "sms_body"
    private const val SMS_URI_STRING = "sms:"

    fun shareViaSMS(context: Context, text: String) {
        val smsUri = Uri.parse(SMS_URI_STRING)
        val intent = Intent(Intent.ACTION_VIEW, smsUri).apply {
            putExtra(SMS_BODY_EXTRA, text)
        }
        context.startActivity(intent)
    }
}