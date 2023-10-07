package pk.lab1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pk.lab1.Const.BROADCAST

class TimeService : Service() {
    private var username: String? = null
    private var second = 0
    private var isLogging = false
    private val logTag = "TimeService"
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        isLogging = true
        username = intent.getStringExtra("username")
        intent.getStringExtra(IDENTIFIER_EXTRA)?.let { startLogging(it) }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        isLogging = false
        sendBroadcast()
        super.onDestroy()
    }

    private fun startLogging(identifier: String) {
        coroutineScope.launch {
            while (isLogging) {
                second++
                Log.i(logTag + identifier, "Instance: $identifier Logging to LogCat every second: $second")
                delay(1000)
            }
        }
    }

    private fun sendBroadcast() {
        val stopLogging = Intent(BROADCAST)
        stopLogging.putExtra(USERNAME, username)
        stopLogging.putExtra(NUMBER, second)
        sendBroadcast(stopLogging)
    }

    companion object {
        const val USERNAME = "username"
        const val NUMBER = "number"
        const val IDENTIFIER_EXTRA = "identifier_extra"
    }
}