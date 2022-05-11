package com.example.corgiworld.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import java.util.ArrayList

class DbManager(val context: Context) {
    val notesDb = NotesDb(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = notesDb.writableDatabase
    }

    fun insertToDb(title: String, content: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun removeItemFromDb(id: String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    fun updateItemFromDb(title: String, content: String, id: Int){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
        }
        db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
    }

    @SuppressLint("Range")
    fun readDbData(): ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()

        val cursor = db?. query(MyDbNameClass.TABLE_NAME, null,
            null,null,
            null,null,null)

        with(cursor){
            while (this?.moveToNext()!!){
                val dataTitle = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataDesc = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataId = cursor?.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val item = ListItem()
                item.title = dataTitle.toString()
                item.desc = dataDesc.toString()
                item.id = dataId!!.toInt()
                dataList.add(item)
            }
        }
        cursor?.close()
        return dataList
    }

    fun closeDb(){
        notesDb.close()
    }
}