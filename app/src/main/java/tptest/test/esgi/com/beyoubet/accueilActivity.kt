package tptest.test.esgi.com.beyoubet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.Toast

class accueilActivity : AppCompatActivity(), AccueilFragment.MainFragmentCallback {
    private var userId : Int = 0

    public fun getUserid() : Int
    {
        return userId
    }
    override fun onTitleClicked() {
        Toast.makeText(this,"Tu as clické !",Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        userId = intent.getIntExtra("userId", 0)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.onglet_accueil -> {
//                    Toast.makeText(this,"onglet0", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, AccueilFragment())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.onglet_profil -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, ProfilFragment())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.onglet_social -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, FriendFragment())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.onglet_historique -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, History_fragment())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.onglet_social -> {
                    Toast.makeText(this,"onglet2", Toast.LENGTH_SHORT).show()
                }

                R.id.onglet_sport -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, TeamListFragment())
                            .addToBackStack(null)
                            .commit()
                }

                else -> {

//                    Toast.makeText(this,"onglet3", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, TeamListActivity::class.java)
//                    val intent = Intent(this, accueilActivity::class.java)
//                        intent.putExtra("VALUE", myListObject[i]["value"].toString().toInt())
//                    startActivity(intent)
                }
            }
            true
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, AccueilFragment())
                .commit()
    }
}
