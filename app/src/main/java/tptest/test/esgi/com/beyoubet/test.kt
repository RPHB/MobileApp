package tptest.test.esgi.com.beyoubet

import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.action_ongle_1 -> {
                }
                R.id.action_ongle_2 -> {
                }

                else -> {
                }
            }//Action quand onglet 1 sélectionné
            //Action quand onglet 2 sélectionné
            //Action quand onglet 3 sélectionné
            true
        }
    }
}