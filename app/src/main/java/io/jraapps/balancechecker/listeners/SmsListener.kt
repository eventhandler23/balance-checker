package io.jraapps.balancechecker.listeners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SMS_INTENT_ACTION) {
            intent.extras?.let { bundle ->
                try {
                    val pdus = bundle.get("pdus") as Array<Object>
                    val format = bundle.getString("format")
                    pdus.forEach {
                        val sms = SmsMessage.createFromPdu(it as ByteArray, format)
                        val from = sms.originatingAddress
                        val msgBody = sms.messageBody

                        Log.i(TAG, "Received SMS from: $from")
                        Log.i(TAG, "SMS content: $msgBody")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.message ?: "Failed to retrieve SMS.")
                }
            }
        }
    }

    companion object {
        private const val TAG = "SmsListener"
        private const val SMS_INTENT_ACTION = "android.provider.Telephony.SMS_RECEIVED"
    }
}