package pk.lab1

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.room.Room
import pk.lab1.Const.BROADCAST
import pk.lab1.Const.DATABASE_NAME
import pk.lab1.Const.PROVIDER_NAME
import pk.lab1.database.TimeDatabase
import pk.lab1.receiver.NumberReceiver
import java.util.concurrent.atomic.AtomicInteger


class UserActivity : ComponentActivity() {
    private val receiver = NumberReceiver()
    private val serviceCounter = AtomicInteger(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        registerReceiver(receiver, IntentFilter(BROADCAST))


        val db = Room.databaseBuilder(
            applicationContext,
            TimeDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries().build()
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val read = findViewById<Button>(R.id.read)
        val readProvider = findViewById<Button>(R.id.readProvider)
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

        readProvider.setOnClickListener {
            val cursor = contentResolver.query(
                Uri.parse("content://pk.lab1.provider/time"),
                null,
                null,
                null,
                null
            )

            if (cursor!!.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    Log.i("ContentProvider",  "Username: ${cursor.getString(cursor.getColumnIndex("username"))}, number: ${cursor.getInt(cursor.getColumnIndex("number"))}")
                    cursor.moveToNext()
                }
            } else {
                Log.d("ContentProvider",  "No records found")
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}