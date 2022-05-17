package com.example.corgiworld

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val CITY: String = "Krasnoyarsk"
    val API: String = "06c921750b9a82d8f5d1294e1586276f" // Use API key
    private lateinit var mHeader: AccountHeader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherTask().execute()

        var mToolbar: Toolbar = findViewById(R.id.weather_toolbar)

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
                        notesShow()
                    }
                    return false
                }}).build()

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }


        override fun doInBackground(vararg params: String?): String? {
            var response:String?

            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getInt("temp").toString()+"°C"
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()


                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

    }

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

    fun notesShow(){
        val notesIntent = Intent(this, NotesActivity::class.java)
        startActivity(notesIntent)
    }

    fun mapShow(){
        val mapIntent = Intent(this, MapActivity::class.java)
        startActivity(mapIntent)
    }
}