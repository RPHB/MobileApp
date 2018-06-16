package tptest.test.esgi.com.beyoubet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.json.JSONObject
import java.net.URL

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        val scoreValue = intent.getStringExtra("score")
        val dateValue = intent.getStringExtra("date")
        val goToBetButton = findViewById<Button>(R.id.goToBetButton) as? Button
        val score = findViewById<Button>(R.id.scoreView) as? TextView
        val matchDateTextView = findViewById<Button>(R.id.matchDateTextView) as? TextView
        score!!.setText(scoreValue)
        matchDateTextView!!.setText(dateValue);




    }
}
