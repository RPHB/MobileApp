package tptest.test.esgi.com.beyoubet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.net.HttpURLConnection
import java.net.URL

class InscriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inscription_activity)
        var validateInscriptionButton = findViewById<Button>(R.id.validateInscriptionButton) as? Button
        var inscriptionPseudo = findViewById<EditText>(R.id.inscriptionPseudo) as? EditText
        var inscriptionEmail = findViewById<EditText>(R.id.inscriptionEmail) as? EditText
        var inscriptionEmailConfirmation = findViewById<EditText>(R.id.inscriptionEmailConfirmation) as? EditText
        var inscriptionPwd = findViewById<EditText>(R.id.inscriptionPwd) as? EditText
        var inscriptionPwdconfirmation = findViewById<EditText>(R.id.inscriptionPwdconfirmation) as? EditText


        validateInscriptionButton!!.setOnClickListener {

            val pseudo = inscriptionPseudo!!.text.toString()
            val pwd = inscriptionPwd!!.text.toString()
            val pwdconf = inscriptionPwdconfirmation!!.text.toString()
            val email = inscriptionEmail!!.text.toString()
            val emailConf = inscriptionEmailConfirmation!!.text.toString()
            if (pwd.length == 0 || pseudo.length == 0 || pwdconf.length == 0 || email.length == 0 || emailConf.length == 0)
            {
                Toast.makeText(this,R.string.blanckPwd, Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else if (pwd != pwdconf)
            {
                Toast.makeText(this," " + pwd + " " + pwdconf, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this,R.string.errorPwd , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (email != emailConf)
            {
                Toast.makeText(this,R.string.errorMail, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val urlTxt = getString(R.string.url)+"/users/create/"+ pseudo + "/" +  pwd + "/" + email


            Thread({
                val objUrl = URL( urlTxt)
                val textURL = objUrl.readText()


                runOnUiThread({
                    if (textURL=="true")
                    {
                        Toast.makeText(this,R.string.inscriptionSucceded, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ConnectionActivity::class.java)
//                        intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
                        startActivity(intent)
                    }
                    else if (textURL=="duplicateEmail")
                    {
                        Toast.makeText(this,R.string.duplicateEmail, Toast.LENGTH_SHORT).show()
                    }
                    else if (textURL=="duplicatePseudo")
                    {
                        Toast.makeText(this,R.string.duplicatePseudo, Toast.LENGTH_SHORT).show()
                    }

                })
            }).start()
        }
    }
}
