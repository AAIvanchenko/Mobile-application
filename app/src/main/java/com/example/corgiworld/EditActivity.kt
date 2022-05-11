package com.example.corgiworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.corgiworld.db.DbManager
import com.example.corgiworld.db.ItemConstant
import kotlinx.android.synthetic.main. activity_edit.*

class EditActivity : AppCompatActivity() {
    val dbManager = DbManager(this)
    var id = 0
    var isEditState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        getMyIntents()
    }
     override fun onResume(){
         super.onResume()
         dbManager.openDb()
     }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    fun onClickSave(view: View){
        val myTitle = edTitle.text.toString()
        val myDesc = edDesc.text.toString()

        if (myTitle != "" && myDesc != ""){
            if (isEditState){
                dbManager.updateItemFromDb(myTitle, myDesc, id)
            } else{
                dbManager.insertToDb(myTitle, myDesc)
            }

        }
        finish()
    }

    fun getMyIntents(){
        val myIntent = intent

        if (myIntent != null){
            if (myIntent.getStringExtra(ItemConstant.I_TITLE_KEY) != null){
                edTitle.setText(myIntent.getStringExtra(ItemConstant.I_TITLE_KEY))
                edDesc.setText(myIntent.getStringExtra(ItemConstant.I_DESC_KEY))
                id = myIntent.getIntExtra(ItemConstant.I_ID_KEY, 0)
                isEditState = true
            }
        }
    }

}