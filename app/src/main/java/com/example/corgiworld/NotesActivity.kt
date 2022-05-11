package com.example.corgiworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corgiworld.db.DbManager
import com.example.corgiworld.db.MyAdapter
import kotlinx.android.synthetic.main.activity_notes.*


class NotesActivity : AppCompatActivity() {
    private lateinit var mHeader: AccountHeader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        init()

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

    fun onClickNew(view: View){
        var editIntent = Intent(this, EditActivity::class.java)
        startActivity(editIntent)
    }

    val myAdapter = MyAdapter(ArrayList(), this)
    val dbManager = DbManager(this)

    override fun onResume(){
        super.onResume()
        dbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
    fun init(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = myAdapter
    }

    fun fillAdapter(){
        myAdapter.updateAdapter(dbManager.readDbData())
    }

    private fun getSwapMg(): ItemTouchHelper{
        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT
                or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeItem(viewHolder.adapterPosition, dbManager)
            }
        })
    }

}