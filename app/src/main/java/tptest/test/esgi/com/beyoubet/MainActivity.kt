package tptest.test.esgi.com.beyoubet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject
import java.net.URL
import android.widget.ArrayAdapter
import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.widget.TextView


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val context : Context? = null
//        val res = context?.getResources()
        val urlTxt = getString(R.string.url)+"/match"


        Log.i("URLURLURLURLURL", urlTxt)
        Thread({
            val textURL = URL( urlTxt).readText()

            runOnUiThread({
                var resultText = "";
                val jsonObj = JSONObject(textURL)
                val jsonSize = jsonObj["length"].toString().toInt() - 1;
                var matchList = findViewById<ListView>(R.id.teamList) as? ListView
                var myList: MutableList<String> = mutableListOf<String>()
                var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                for (i in 1 .. jsonSize)
                {
                    var cur=JSONObject(jsonObj["child"+i].toString())
                    var team=cur["team"].toString()
                    var value=cur["value"].toString()
                    val rootObject= JSONObject()
                    rootObject.put("team",team)
                    rootObject.put("value",value)
                    myListObject.add(rootObject)
                    resultText=team
//                   resultText=date + " " + match + " " +score + "\n"
                    myList.add(team)

                }
                val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, myList)
                matchList!!.setAdapter(adapter)

                matchList.setOnItemClickListener { adapterView, view, i, l ->
                    Log.i("WEB_VIEW_TEST", myListObject[i]["value"].toString())
                    //Toast.makeText(this, myListObject[i]["value"].toString() , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MatchListActivity::class.java)
                    intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
                    startActivity(intent)

                }
            })
        }).start()

        setContentView(R.layout.main_activity)
    }

}
