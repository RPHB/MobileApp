package tptest.test.esgi.com.beyoubet


import android.app.Activity
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
 *
 */
class FriendFragment : Fragment() {

    var mainFragmentCallback: TeamListFragment.MainFragmentCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is TeamListFragment.MainFragmentCallback)
            mainFragmentCallback = activity
    }

    override fun onDetach() {
        super.onDetach()
        mainFragmentCallback = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        title = view!!.findViewById<View>(R.id.title) as? TextView

        val userId = (activity as accueilActivity).getUserid()
        val urlTxt = getString(R.string.url)+"/friends/getFriends/" + userId
        val friendEditText = view!!.findViewById<EditText>(R.id.friendEditText) as? EditText
        val addFriendButton = view!!.findViewById<Button>(R.id.addFriendButton) as? Button

        addFriendButton!!.setOnClickListener() {
            val newFriendName = friendEditText!!.text
            if (newFriendName.length == 0)
            {
                return@setOnClickListener
            }
            val getIdUrlTxt=getString(R.string.url)+"/users/getUserId/"+newFriendName
            Thread({
                val textURL = URL( getIdUrlTxt).readText()

                activity.runOnUiThread({
                    if (textURL!="falseUser")
                    {
                        val newFriendId=textURL.toString().substring(textURL.indexOf(":") + 1).toInt()
                        val addFriendUrl = getString(R.string.url)+"/friends/addFriend/" + userId + "/" + newFriendId
                        Thread({
                            val textURL = URL( addFriendUrl).readText()

                            activity.runOnUiThread({

                                    Toast.makeText(activity.applicationContext, R.string.invitationSent , Toast.LENGTH_SHORT).show()


                            })
                        }).start()
                    }
                    else
                    {
                        Toast.makeText(activity.applicationContext, R.string.unknownUser , Toast.LENGTH_SHORT).show()

                    }

                })
            }).start()

        }

        Thread({
            val textURL = URL( urlTxt).readText()

            activity.runOnUiThread({
                var resultText = ""
                if (textURL.toString() != "Aucun ami") {
                    val jsonObj = JSONObject(textURL)
                    val jsonSize = jsonObj["length"].toString().toInt() - 1
                    var friendList = view!!.findViewById<ListView>(R.id.myFriendsListView) as? ListView
                    var myList: MutableList<String> = mutableListOf<String>()
                    var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                    for (i in 0..jsonSize) {
                        var cur = JSONObject(jsonObj["child" + i].toString())
                        var id = cur["id"].toString()
                        var username = cur["username"].toString()
                        val rootObject = JSONObject()
                        rootObject.put("id", id)
                        rootObject.put("username", username)
                        myListObject.add(rootObject)
                        resultText = username
                        myList.add(username)

                    }
                    val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                    friendList!!.setAdapter(adapter)

                    friendList.setOnItemClickListener { adapterView, view, i, l ->

                    }
                }
            })
        }).start()
        val urlTxtPending = getString(R.string.url)+"/friends/getPendingFriends/" + userId

        Thread({
            val textURL = URL( urlTxtPending).readText()

            activity.runOnUiThread({
                var resultText = "";
                if (textURL.toString() != "Aucun ami") {
                    val jsonObj = JSONObject(textURL)
                    val jsonSize = jsonObj["length"].toString().toInt() - 1
                    var friendList = view!!.findViewById<ListView>(R.id.waitingFriendsListView) as? ListView
                    var myList: MutableList<String> = mutableListOf<String>()
                    var myListObject: MutableList<JSONObject> = mutableListOf<JSONObject>()
                    for (i in 0..jsonSize) {
                        var cur = JSONObject(jsonObj["child" + i].toString())
                        var id = cur["id"].toString()
                        var username = cur["username"].toString()
                        val rootObject = JSONObject()
                        rootObject.put("id", id)
                        rootObject.put("username", username)
                        myListObject.add(rootObject)
                        resultText = username
                        myList.add(username)

                    }
                    val adapter = ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_list_item_1, myList)
                    friendList!!.setAdapter(adapter)


                    friendList.setOnItemClickListener { adapterView, view, i, l ->

                    }
                }
            })
        }).start()
    }



}
