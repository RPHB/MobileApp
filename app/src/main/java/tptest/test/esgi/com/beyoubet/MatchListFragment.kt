package tptest.test.esgi.com.beyoubet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccueilFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccueilFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MatchListFragment : Fragment() {

    interface MainFragmentCallback {
        fun onTitleClicked()
    }

    private val teamId : Int = 0

    fun newInstance(idteam: Int): MatchListFragment {
        val fragment = MatchListFragment()
        val args = Bundle()
        args.putInt("teamId", idteam)
        fragment.arguments = args
        return fragment
    }

    var title: TextView? = null
    var mainFragmentCallback: MainFragmentCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.match_list_activity, container, false)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is MainFragmentCallback)
            mainFragmentCallback = activity
    }

    override fun onDetach() {
        super.onDetach()
        mainFragmentCallback = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view!!.findViewById<View>(R.id.title) as? TextView

        val value = arguments.getInt("teamId", 0)
//        val value = intent.getIntExtra("VALUE", 118)
        val urlTxt = getString(R.string.url)+"/match/"+value.toString()


        Log.i("URLURLURLURLURL", urlTxt)
        Thread({
            val textURL = URL( urlTxt).readText()
            activity.runOnUiThread({
                var resultText = "";
                val jsonObj = JSONObject(textURL)
                val jsonSize = jsonObj["length"].toString().toInt() - 1;
                var matchList = view!!.findViewById<ListView>(R.id.matchList) as? ListView
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
                val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                matchList!!.setAdapter(adapter)

                matchList!!.setOnItemClickListener { adapterView, view, i, l ->
                    //                   if (myListObject[i]["score"].toString() =="-")
    //                   {
//                    val intent = Intent(this, MatchDetailActivity::class.java)
//                    intent.putExtra("score", myListObject[i]["score"].toString())
//                    intent.putExtra("team1", myListObject[i]["match"].toString())
//                    intent.putExtra("team2", myListObject[i]["match"].toString())
//                    intent.putExtra("date", myListObject[i]["date"].toString())
//                    startActivity(intent)
    //                   }
    //                   else
    //                   {
    //                       Log.i("WEB_VIEW_TEST", myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString())
                           Toast.makeText(activity.applicationContext, myListObject[i]["date"].toString() + "  " + myListObject[i]["score"].toString() , Toast.LENGTH_SHORT).show()
    //                   }

                }

            })
        }).start()
    }

}
