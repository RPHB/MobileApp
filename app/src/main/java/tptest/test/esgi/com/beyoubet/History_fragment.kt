package tptest.test.esgi.com.beyoubet

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
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
 * [.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class History_fragment: Fragment() {
    // TODO: Rename and change types of parameters

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
        return inflater!!.inflate(R.layout.fragment_history, container, false)
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

        val userId = (activity as accueilActivity).getUserid()
        val urlTxt = getString(R.string.url)+"/bets/getForUser/"+userId.toString()

        Thread({
            val textURL = URL( urlTxt).readText()

            activity.runOnUiThread({
                var resultText = "";
                val jsonObj = JSONObject(textURL.toString())
                val jsonSize = jsonObj["length"].toString().toInt() - 1;
                var betsList = view!!.findViewById<ListView>(R.id.betListView) as? ListView
                var myList: MutableList<String> = mutableListOf<String>()
                var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                for (i in 0 .. jsonSize)
                {
                    var cur= JSONObject(jsonObj["child"+i].toString())
                    var team1=cur["team1"].toString()
                    var team2=cur["team2"].toString()
                    var choiceInt=cur["choice"].toString().toInt()
                    var choiceStr="égalité"
                    if (choiceInt == 1) choiceStr = team1
                    else if (choiceInt == 2) choiceStr = team2
                    val rootObject= JSONObject()
                    rootObject.put("team1",team1)
                    rootObject.put("team2",team2)
                    rootObject.put("choice",choiceStr)
                    myListObject.add(rootObject)
                    resultText=team1 + " " + team2
//                   resultText=date + " " + match + " " +score + "\n"
                    myList.add(resultText)

                }
                val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                betsList!!.setAdapter(adapter)

                betsList.setOnItemClickListener { adapterView, view, i, l ->

                    Toast.makeText(activity.applicationContext, myListObject[i]["choice"].toString(), Toast.LENGTH_SHORT).show()


                }
            })
        }).start()
    }

}
