package tptest.test.esgi.com.beyoubet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.json.JSONObject
import java.net.URL

class ConnectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connection_activity)
        var connectionButton = findViewById<Button>(R.id.connectionButton) as? Button
        var inscriptionButton = findViewById<Button>(R.id.inscriptionButton) as? Button
        var connectPseudo = findViewById<EditText>(R.id.connectPseudo) as? EditText
        var connectPwd = findViewById<EditText>(R.id.connectPwd) as? EditText


        connectionButton!!.setOnClickListener {

            val pseudo = connectPseudo!!.text
            val pwd = connectPwd!!.text
            if (pwd.length == 0 || pseudo.length == 0)
            {
                Toast.makeText(this,R.string.blanckPwd,Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val urlTxt = getString(R.string.url)+"/users/connect/"+ pseudo + "/" +  pwd


            Thread({
                val textURL = URL( urlTxt).readText()

                runOnUiThread({
                    if (textURL=="true")
                    {
//                        val intent = Intent(this, TeamListActivity::class.java)
                        val intent = Intent(this, accueilActivity::class.java)
//                        intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,textURL,Toast.LENGTH_SHORT).show()
                    }

                })
            }).start()

        }
        inscriptionButton!!.setOnClickListener {

            val intent = Intent(this, InscriptionActivity::class.java)

//                        intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
            startActivity(intent)

        }



    }
}
