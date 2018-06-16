package tptest.test.esgi.com.beyoubet

import android.content.Intent
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
        setContentView(R.layout.match_list_activity)
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
                   rootObject.put("match",match)
                   myListObject.add(rootObject)
                   resultText=match
//                   resultText=date + " " + match + " " +score + "\n"
                   myList.add(match)

               }
               val adapter = ArrayAdapter<String>(this@MatchListActivity, android.R.layout.simple_list_item_1, myList)
               matchList!!.setAdapter(adapter)
               
               matchList.setOnItemClickListener { adapterView, view, i, l ->
//                   if (myListObject[i]["score"].toString() =="-")
//                   {
                       val intent = Intent(this, MatchDetailActivity::class.java)
                        intent.putExtra("score", myListObject[i]["score"].toString())
                        intent.putExtra("team1", myListObject[i]["match"].toString())
                        intent.putExtra("team2", myListObject[i]["match"].toString())
                        intent.putExtra("date", myListObject[i]["date"].toString())
                       startActivity(intent)
//                   }
//                   else
//                   {
//                       Log.i("WEB_VIEW_TEST", myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString())
//                       Toast.makeText(this, myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString() , Toast.LENGTH_SHORT).show()
//                   }

               }

           })
       }).start()


    }

}
