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
class TeamListFragment : Fragment() {
    interface MainFragmentCallback {
        fun onTitleClicked()
    }

    private val TITLE = "TITLE"

    fun newInstanceMatchListFragment(idteam: Int): MatchListFragment {
        val fragment = MatchListFragment()
        val args = Bundle()
        args.putInt("teamId", idteam)
        fragment.arguments = args
        return fragment
    }

    var title: TextView? = null
    var mainFragmentCallback: MainFragmentCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.team_list_activity, container, false)
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

        val urlTxt = getString(R.string.url)+"/match"


        Log.i("URLURLURLURLURL", urlTxt)
        Thread({
            val textURL = URL( urlTxt).readText()

            activity.runOnUiThread({
                var resultText = "";
                val jsonObj = JSONObject(textURL)
                val jsonSize = jsonObj["length"].toString().toInt() - 1;
                var matchList = view!!.findViewById<ListView>(R.id.teamList) as? ListView
                var myList: MutableList<String> = mutableListOf<String>()
                var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                for (i in 1 .. jsonSize)
                {
                    var cur= JSONObject(jsonObj["child"+i].toString())
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
                val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                matchList!!.setAdapter(adapter)

                matchList.setOnItemClickListener { adapterView, view, i, l ->

                    activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, newInstanceMatchListFragment(myListObject[i]["value"].toString().toInt()))
                            .commit()
//                    Log.i("WEB_VIEW_TEST", myListObject[i]["value"].toString())
//                    Toast.makeText(activity.applicationContext, myListObject[i]["value"].toString() , Toast.LENGTH_SHORT).show()
//                    val intent = Intent(activity.applicationContext, MatchListActivity::class.java)
//                    intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
//                    startActivity(intent)

                }
            })
        }).start()
    }

}
