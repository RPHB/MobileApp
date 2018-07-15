package tptest.test.esgi.com.beyoubet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.*
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
class AccueilFragment : Fragment() {
    interface MainFragmentCallback {
        fun onTitleClicked()
    }

    private val TITLE = "TITLE"



    fun newInstance1(title: String): AccueilFragment {
        val fragment = AccueilFragment()
        val args = Bundle()
        args.putString(TITLE, title)
        fragment.arguments = args
        return fragment
    }
    fun newInstanceBetFragment(matchId:String, teams: String,teamname1:String,teamname2:String, date : String): BetFragment {
        val fragment = BetFragment()
        val args = Bundle()
        val team1 = teamname1
        val team2 = teamname2

        var dateToSend =  date.substring(date.indexOf(" ") + 1)
        Toast.makeText(activity.applicationContext, date , Toast.LENGTH_SHORT).show()

        dateToSend =  dateToSend.substring(0 .. dateToSend.indexOf("T") - 1)
//        val jour = dateToSend.substring(0 .. 1)
//        val mois = dateToSend.substring(3 .. 4)
//        val annee = dateToSend.substring(6 .. 9)
        args.putString("team1", team1)
        args.putString("team2", team2)
        args.putString("date", dateToSend)
        args.putString("matchId", matchId)
        fragment.arguments = args
        return fragment
    }
    var title: TextView? = null
    var mainFragmentCallback: MainFragmentCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_accueil, container, false)
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
        val userId = (activity as accueilActivity).getUserid()
        val urlTxt = getString(R.string.url)+"/match/getFavoriteMatchs"


        Thread({
            val textURL = URL( urlTxt).readText()

            activity.runOnUiThread({
                var resultText = ""
                if (textURL.toString() != "Aucun ami") {
                    val jsonObj = JSONObject(textURL)
                    val jsonSize = jsonObj["length"].toString().toInt() - 1
                    var bestMatchList = view!!.findViewById<ListView>(R.id.bestMatchList) as? ListView
                    var myList: MutableList<String> = mutableListOf<String>()
                    var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                    for (i in 0..jsonSize) {
                        var cur = JSONObject(jsonObj["child" + i].toString())
                        var id = cur["id"].toString()
                        var team1 = cur["teamn1"].toString()
                        var team2 = cur["teamn2"].toString()
                        val rootObject = JSONObject()
                        rootObject.put("id", id)
                        rootObject.put("team1", team1)
                        rootObject.put("team1", team2)
                        myListObject.add(rootObject)
                        resultText = team1 + " - " + team2
                        myList.add(resultText)

                    }
                    val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                    bestMatchList!!.setAdapter(adapter)

                    bestMatchList.setOnItemClickListener { adapterView, view, i, l ->
                        val urlGetMatchInfo = getString(R.string.url)+"/match/getMatch/" + myListObject[i]["id"].toString()


                        Thread({
                            val textURL = URL( urlGetMatchInfo).readText()

                            activity.runOnUiThread({
                                val jsonObj = JSONObject(textURL)
                                val jsonSize = jsonObj["length"].toString().toInt() - 1
//                                Toast.makeText(activity.applicationContext, jsonObj.toString() + " " +myListObject[i]["id"].toString(), Toast.LENGTH_SHORT).show()

                                var cur = JSONObject(jsonObj["child0"].toString())
                                var id = cur["id"].toString()
                                var team1 = cur["teamn1"].toString()
                                var team2 = cur["teamn2"].toString()
                                var date = cur["date"].toString()
                                val rootObject = JSONObject()

                                activity.supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragmentLayout, newInstanceBetFragment(id.toString(),"toto",team1.toString(),team2.toString(),date.toString()))
                                        .addToBackStack(null)
                                        .commit()

                            })
                        }).start()

                    }
                }
            })
        }).start()
        val urlTxtPlayers = getString(R.string.url)+"/users/bestPlayers"

        Thread({
            val textURL = URL( urlTxtPlayers).readText()

            activity.runOnUiThread({
                var resultText = "";
                if (textURL.toString() != "Aucun ami") {
                    val jsonObj = JSONObject(textURL)
                    val jsonSize = jsonObj["length"].toString().toInt() - 1
                    var bestPlayersview = view!!.findViewById<ListView>(R.id.bestPlayersList) as? ListView
                    var myList: MutableList<String> = mutableListOf<String>()
                    var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                    for (i in 0..jsonSize) {
                        var cur = JSONObject(jsonObj["child" + i].toString())
                        var id = cur["id"].toString()
                        var username = cur["username"].toString()
                        var tokens = cur["tokens"].toString()
                        val rootObject = JSONObject()
                        rootObject.put("id", id)
                        rootObject.put("tokens", tokens)
                        rootObject.put("username", username)
                        myListObject.add(rootObject)
                        resultText = username + "  " + tokens
                        myList.add(resultText)

                    }
                    val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                    bestPlayersview!!.setAdapter(adapter)


                    bestPlayersview.setOnItemClickListener { adapterView, view, i, l ->


                    }
                }
            })
        }).start()

    }

}
