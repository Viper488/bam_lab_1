package pk.lab1

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.room.Room
import pk.lab1.database.TimeDatabase
import java.util.concurrent.atomic.AtomicInteger

class UserActivity : ComponentActivity() {
    private val receiver = NumberReceiver()
    private val serviceCounter = AtomicInteger(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        registerReceiver(receiver, IntentFilter("pk.lab1.STOP_LOGGING"))


        val db = Room.databaseBuilder(
            applicationContext,
            TimeDatabase::class.java, "time-database"
        ).allowMainThreadQueries().build()
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val read = findViewById<Button>(R.id.read)
        val username = findViewById<TextView>(R.id.usernameView)
        username.text = intent.getStringExtra("username")
        val service = Intent(this, TimeService::class.java)

        start.setOnClickListener {
            service.putExtra(TimeService.USERNAME, username.text)
            service.putExtra(TimeService.IDENTIFIER_EXTRA, serviceCounter.incrementAndGet().toString())
            service.putExtra("username", username.text)

            startService(service)
        }

        stop.setOnClickListener {
            stopService(service)
        }

        read.setOnClickListener {
            db.timeDao().getAll().forEach {time ->
                Log.i("Database",  "Username: ${time.username}, number: ${time.number}")
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}