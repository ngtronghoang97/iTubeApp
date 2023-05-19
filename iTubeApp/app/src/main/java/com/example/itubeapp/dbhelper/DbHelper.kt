package com.example.itubeapp.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.itubeapp.scheme.TubeAppContract

class  DbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Create a database using an SQL helper.
     * Implement methods that create and maintain the database and tables.
     */

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USER_ENTRIES)
        db.execSQL(SQL_CREATE_PLAYLIST_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /**
         * This database is only a cache for online data, so its upgrade policy is to simply to
         * discard the data and start over.
         */
        db.execSQL(SQL_DELETE_USERS_ENTRIES)
        db.execSQL(SQL_DELETE_PLAYLIST_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        /**
         * If you change the database schema, you must increment the database version.
         */
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "iTubeAppDb.db"

        private const val SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE ${TubeAppContract.UsersEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TubeAppContract.UsersEntry.COLUMN_USER_NAME} TEXT," +
                    "${TubeAppContract.UsersEntry.COLUMN_FULL_NAME} TEXT," +
                    "${TubeAppContract.UsersEntry.COLUMN_PASSWORD} TEXT)"

        private const val SQL_CREATE_PLAYLIST_ENTRIES =
            "CREATE TABLE ${TubeAppContract.PlaylistEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TubeAppContract.PlaylistEntry.COLUMN_USER_NAME} TEXT," +
                    "${TubeAppContract.PlaylistEntry.COLUMN_YOUTUBE_URL} TEXT)"

        private const val SQL_DELETE_USERS_ENTRIES =
            "DROP TABLE IF EXISTS ${TubeAppContract.UsersEntry.TABLE_NAME}"

        private const val SQL_DELETE_PLAYLIST_ENTRIES =
            "DROP TABLE IF EXISTS ${TubeAppContract.PlaylistEntry.TABLE_NAME}"
    }
}