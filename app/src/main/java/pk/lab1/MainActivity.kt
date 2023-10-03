package pk.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val buttonClick = findViewById<Button>(R.id.button)
        val text = findViewById<EditText>(R.id.text_input)

        buttonClick.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("username", text.text.toString())
            startActivity(intent)
        }
    }
}