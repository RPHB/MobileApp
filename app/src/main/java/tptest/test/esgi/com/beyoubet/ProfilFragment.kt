package tptest.test.esgi.com.beyoubet


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var coins = view!!.findViewById<TextView>(R.id.coinTextView) as? TextView
        val userId=(activity as accueilActivity).getUserid()
        val urlTxt = getString(R.string.url)+"/users/getTokens/" + userId
        Thread({
            val textURL = URL( urlTxt).readText()

            activity.runOnUiThread({
                val jsonObj = JSONObject(textURL)
                val tokens=jsonObj["tokens"]
                coins!!.text = "coins : " + tokens



            })
        }).start()
    }


}
