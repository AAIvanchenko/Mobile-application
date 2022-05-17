package com.example.corgiworld

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mHeader: AccountHeader
    private val KRASNOYARS = LatLng( 56.01528, 92.89325)
    private val MINUSINSK = LatLng( 56.003100, 92.799517)
    private val DUDINKA = LatLng(56.002625, 92.764402)
    private val SKVER = LatLng(56.012095, 92.814739)


    private var markerPerth: Marker? = null
    private var markerSydney: Marker? = null
    private var markerBrisbane: Marker? = null

    private lateinit var mMap: GoogleMap
    private  lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var mToolbar: Toolbar = findViewById(R.id.maptoolbar)

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
                if (position == 3){
                    weatherShow()
                }
                if (position == 4){
                    notesShow()
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

    fun notesShow(){
        val notesIntent = Intent(this, NotesActivity::class.java)
        startActivity(notesIntent)
    }

    fun weatherShow(){
        val weatherIntent = Intent(this, WeatherActivity::class.java)
        startActivity(weatherIntent)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val cameraPosition = CameraPosition.Builder()
            .target(KRASNOYARS) // Sets the center of the map to Mountain View
            .zoom(12f)            // Sets the zoom
            .bearing(90f)         // Sets the orientation of the camera to east
            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        markerPerth = map.addMarker(
            MarkerOptions()
                .position(SKVER)
                .title("Сквер имени степанова")
                .snippet("Скверпарк культуры и отдыха")
        )
        markerPerth?.tag = 0
        markerSydney = map.addMarker(
            MarkerOptions()
                .position(MINUSINSK)
                .title("Парк Юнатов")
                .snippet("Можно погулять с собакой")
        )
        markerSydney?.tag = 0
        markerBrisbane = map.addMarker(
            MarkerOptions()
                .position(DUDINKA)
                .title("Гремячая грива")
                .snippet("Может быть много клещей")
        )
        markerBrisbane?.tag = 0

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this)
    }


    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                this,
                "${marker.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
}