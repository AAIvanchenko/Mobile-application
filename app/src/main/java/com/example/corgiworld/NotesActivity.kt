package com.example.corgiworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class NotesActivity : AppCompatActivity() {
    private lateinit var mHeader: AccountHeader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        var mToolbar: Toolbar = findViewById(R.id.notes_toolbar)

        setSupportActionBar(mToolbar)
        createHeader()
        val mDrawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(mToolbar)
            .withAccountHeader(mHeader)
            .withSelectedItem(-1)
            .withActionBarDrawerToggle(true)
            .addDrawerItems(
                //Home
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Home")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_home),


                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Map")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_map),

                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Weather")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_weather),

                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("My Corgi")
                    .withSelectable(false)
                    .withIcon(R.drawable.corgi),

                ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    if (position == 1) {
                        mainShow()
                    }
                    if (position == 2){
                        mapShow()
                    }
                    if (position == 4){
                        weatherShow()
                    }
                    return false
                }}).build()
    }
    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.laughing).build()
    }

    fun mainShow() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun weatherShow(){
        val weatherIntent = Intent(this, WeatherActivity::class.java)
        startActivity(weatherIntent)
    }

    fun mapShow(){
        val mapIntent = Intent(this, MapActivity::class.java)
        startActivity(mapIntent)
    }

}