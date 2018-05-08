package tptest.test.esgi.com.beyoubet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject
import java.net.URL
import android.widget.ArrayAdapter
import android.widget.TextView


class MatchListActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getIntExtra("VALUE", 118)
        val urlTxt = getString(R.string.url)+"/match/"+value.toString()

       Thread({
           val textURL = URL( urlTxt).readText()

           runOnUiThread({
               var resultText = "";
               val jsonObj = JSONObject(textURL)
               val jsonSize = jsonObj["length"].toString().toInt() - 1;
               var matchList = findViewById<ListView>(R.id.matchList) as? ListView
               var myList: MutableList<String> = mutableListOf<String>()
               var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
               for (i in 0 .. jsonSize)
               {
                   var cur=JSONObject(jsonObj["child"+i].toString())
                   var date=cur["date"].toString()
                   var match=cur["match"].toString()
                   var score=cur["score"].toString()
                   val rootObject= JSONObject()
                   rootObject.put("date",date)
                   rootObject.put("score",score)
                   myListObject.add(rootObject)
                   resultText=match
//                   resultText=date + " " + match + " " +score + "\n"
                   myList.add(match)

               }
               val adapter = ArrayAdapter<String>(this@MatchListActivity, android.R.layout.simple_list_item_1, myList)
               matchList!!.setAdapter(adapter)
               
               matchList.setOnItemClickListener { adapterView, view, i, l ->
                   Log.i("WEB_VIEW_TEST", myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString())
                   Toast.makeText(this, myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString() , Toast.LENGTH_SHORT).show()
               }
           })
       }).start()

        setContentView(R.layout.match_list_activity)
    }

}
