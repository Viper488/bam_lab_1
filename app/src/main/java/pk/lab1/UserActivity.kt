package pk.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val textView = findViewById<TextView>(R.id.textView)
        val str = intent.getStringExtra("username")
        textView.text = str

        val services: MutableList<Intent> = emptyList<Intent>().toMutableList()
        val service = Intent(this, TimeService::class.java)

        start.setOnClickListener {
            runBlocking {
                launch {
                    startService(service)
                    delay(1000)
                }
            }
        }

        stop.setOnClickListener {
            stopService(service)
        }
    }
}