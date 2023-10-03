package pk.lab1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val username = intent.getStringExtra(TimeService.USERNAME)
        val number = intent.getIntExtra(TimeService.NUMBER, 0)

        Log.i("NumberReceiver", "Username: $username, number: $number")
    }
}