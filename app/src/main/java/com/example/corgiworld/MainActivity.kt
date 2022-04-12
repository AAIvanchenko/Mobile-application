package com.example.corgiworld

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.mikepenz.materialdrawer.Drawer
import com.example.corgiworld.databinding.ActivityMainBinding
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun mapShow(view: View){
        val mapIntent = Intent(this, MapActivity::class.java)
        startActivity(mapIntent)
    }

    fun weatherShow(view: View){
        val weatherIntent = Intent(this, WeatherActivity::class.java)
        startActivity(weatherIntent)
    }

    fun notesShow(view: View){
        val notesIntent = Intent(this, NotesActivity::class.java)
        startActivity(notesIntent)
    }
}
