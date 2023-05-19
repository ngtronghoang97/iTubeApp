package com.example.itubeapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.itubeapp.dbhelper.DbHelper
import com.example.itubeapp.model.PlayListModel
import com.example.itubeapp.prefmanager.SharedPrefManager
import com.example.itubeapp.scheme.TubeAppContract
import com.example.itubeapp.views.HomeFragment
import com.example.itubeapp.views.LoginFragment

class MainActivity : AppCompatActivity() {

    private val dbHelper = DbHelper(this)
    var pref: SharedPrefManager? = null
    var dataItemsModel : ArrayList<PlayListModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }

        pref = SharedPrefManager(this@MainActivity)
    }

    fun createNewUser(fullName: String, userName: String, password: String) {
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(TubeAppContract.UsersEntry.COLUMN_FULL_NAME, fullName)
            put(TubeAppContract.UsersEntry.COLUMN_USER_NAME, userName)
            put(TubeAppContract.UsersEntry.COLUMN_PASSWORD, password)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TubeAppContract.UsersEntry.TABLE_NAME, null, values)
        Log.d("insertedRow", newRowId.toString())

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commitNow()
    }

    fun loginUser(usrName: String, usrPassword: String) {
        val db = dbHelper.readableDatabase

        // Def columns to return after query
        val projection = arrayOf(
            TubeAppContract.UsersEntry.COLUMN_USER_NAME,
            TubeAppContract.UsersEntry.COLUMN_PASSWORD,
            TubeAppContract.UsersEntry.COLUMN_FULL_NAME
        )

        // Filter results WHERE "userName" = 'Jones', NOTE that 'Jones' is the selectionArgs
        val selection = "${TubeAppContract.UsersEntry.COLUMN_USER_NAME} = ?"
        val selectionArgs = arrayOf(usrName)

        // SQL statement.
        val cursor = db.query(
            TubeAppContract.UsersEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null, null, null)

        var uName = ""
        var uPass = ""
        var uFullName = ""
        with(cursor) {
            while (moveToNext()) {
                uName = getString(getColumnIndexOrThrow(TubeAppContract.UsersEntry.COLUMN_USER_NAME))
                uPass = getString(getColumnIndexOrThrow(TubeAppContract.UsersEntry.COLUMN_PASSWORD))
                uFullName = getString(getColumnIndexOrThrow(TubeAppContract.UsersEntry.COLUMN_FULL_NAME))

                // log data
                Log.d("resData", "user:$uName pass:$uPass")
            }
        }

        // if user success take go to home
        if (uName == usrName && uPass == usrPassword) {
            Toast.makeText(this@MainActivity, "Dear $uFullName, welcome to iTube", Toast.LENGTH_LONG).show()

            pref!!.setUSER_NAME(uName)
            pref!!.setFULL_NAME(uFullName)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        } else {
            Toast.makeText(this@MainActivity, "Invalid User, Retry", Toast.LENGTH_SHORT).show()
        }
    }

    fun addToPlayList(userName: String?, url: String) {
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(TubeAppContract.PlaylistEntry.COLUMN_USER_NAME, userName)
            put(TubeAppContract.PlaylistEntry.COLUMN_YOUTUBE_URL, url)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TubeAppContract.PlaylistEntry.TABLE_NAME, null, values)
        Log.d("insertedRow", newRowId.toString())

        Toast.makeText(this, "Added To PlayList", Toast.LENGTH_SHORT).show()
    }

    fun intentToPlayer(youTubeUrl: String?) {
//        val bundle = Bundle()
//        bundle.putString("youTubeUrl", youTubeUrl)
//        val transaction = supportFragmentManager.beginTransaction()
//        val fragmentTwo = PlayerFragment.newInstance()
//        fragmentTwo.arguments = bundle
//        transaction.replace(R.id.container, fragmentTwo)
//        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction.commit()

        val i = Intent(this, PlayerActivity::class.java)
        i.putExtra("youTubeUrl", youTubeUrl)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    fun readPlayListData(): List<PlayListModel> {
        val db = dbHelper.readableDatabase

        /**
         *  columns to return after this query.
         */
        val projection = arrayOf(
            BaseColumns._ID,
            TubeAppContract.PlaylistEntry.COLUMN_USER_NAME,
            TubeAppContract.PlaylistEntry.COLUMN_YOUTUBE_URL
        )

        // sort order
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = db.query(
            TubeAppContract.PlaylistEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        dataItemsModel = ArrayList()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemName = getString(getColumnIndexOrThrow(
                    TubeAppContract.PlaylistEntry.COLUMN_USER_NAME))
                val itemUrl = getString(getColumnIndexOrThrow(
                    TubeAppContract.PlaylistEntry.COLUMN_YOUTUBE_URL))
                dataItemsModel.add(PlayListModel(itemId, itemName, itemUrl))
            }
        }
        cursor.close()
        Log.d("resDataLog1", dataItemsModel.toString())
        return dataItemsModel
    }
}