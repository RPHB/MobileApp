package tptest.test.esgi.com.beyoubet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.Toast

class accueilActivity : AppCompatActivity(), AccueilFragment.MainFragmentCallback {
    override fun onTitleClicked() {
        Toast.makeText(this,"Tu as clické !",Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.action_ongle_0 -> {
//                    Toast.makeText(this,"onglet0", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, AccueilFragment())
                            .commit()
                }
                R.id.action_ongle_1 -> {
                    Toast.makeText(this,"onglet1", Toast.LENGTH_SHORT).show()
                }
                R.id.action_ongle_2 -> {
                    Toast.makeText(this,"onglet2", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentLayout, TeamListFragment())
                            .commit()
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
