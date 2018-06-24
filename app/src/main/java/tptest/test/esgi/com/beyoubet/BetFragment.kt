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
 * [BetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BetFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BetFragment : Fragment() {

    interface MainFragmentCallback {
        fun onTitleClicked()
    }

    private val teamId : Int = 0

    fun newInstanceBetFragment(teams: String): BetFragment {
        val fragment = BetFragment()
        val args = Bundle()
        val team1 = teams.substring(0 .. teams.indexOf("-") - 1)
        val team2 = teams.substring(teams.indexOf("-") + 2)

        args.putString("team1", team1)
        args.putString("team2", team2)
        fragment.arguments = args
        return fragment
    }
    var title: TextView? = null
    var mainFragmentCallback: MainFragmentCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_bet, container, false)

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
    fun containDigit(str : String) : Boolean
    {
        var i = 0
        for (i in 0 .. 9)
        {
            if (str.indexOf('0' + i) != -1)
                return true;
        }
        return false
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var betButton = view!!.findViewById<Button>(R.id.goToBetButton) as? Button
        var nameTeam1 = view!!.findViewById<TextView>(R.id.nomEquipe1) as? TextView
        var nameTeam2 = view!!.findViewById<TextView>(R.id.nomEquipe2) as? TextView
        var scoreTeam1 = view!!.findViewById<EditText>(R.id.scoreEquipe1) as? EditText
        var scoreTeam2 = view!!.findViewById<EditText>(R.id.scoreEquipe2) as? EditText
        val team1=arguments.getString("team1", "0")
        val team2=arguments.getString("team2", "0")
        val date=arguments.getString("date", "0")
        nameTeam1!!.text = "Score " + team1
        nameTeam2!!.text = "Score " + team2
        val userId = (activity as accueilActivity).getUserid()


        betButton!!.setOnClickListener{
//            Toast.makeText(activity.applicationContext, arguments.getString("date").toString() , Toast.LENGTH_SHORT).show()
            var choice = 0;
            val score1 = scoreTeam1!!.text.toString()
            val score2 = scoreTeam2!!.text.toString()
            if (score1.length == 0 || score2.length == 0)
            {
                Toast.makeText(activity.applicationContext, R.string.blanckPwd , Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val score1int=score1.toInt()
            val score2int=score2.toInt()

            if (score1int>score2int) choice = 1
            else if (score1int<score2int) choice = 2

            val urlTxt = getString(R.string.url)+"/bets/createApp/"+ userId + "/" +  choice + "/" + date + "/" +team1 + "/" + team2
            Thread({

                val textURL = URL( urlTxt).readText()
                activity.runOnUiThread({


                    Toast.makeText(activity.applicationContext, R.string.betSucces , Toast.LENGTH_SHORT).show()


                })
            }).start()
        }


    }

}
