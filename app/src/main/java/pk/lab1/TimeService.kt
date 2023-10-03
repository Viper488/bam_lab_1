package pk.lab1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeService : Service() {
    private var isLogging = false
    private val logTag = "TimeService"
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        isLogging = true
        sendBroadcast(intent)
        intent.getStringExtra(IDENTIFIER_EXTRA)?.let { startLogging(it) }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isLogging = false
        Log.i(logTag, "Stopped all instances")
    }

    private fun startLogging(identifier: String) {
        coroutineScope.launch {
            var second = 0;
            while (isLogging) {
                second++
                Log.i(logTag + identifier, "Instance: $identifier Logging to LogCat every second: $second")
                delay(1000)
            }
        }
    }

    companion object {
        const val IDENTIFIER_EXTRA = "identifier_extra"
    }
}