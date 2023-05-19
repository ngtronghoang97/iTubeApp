package com.example.itubeapp.scheme

import android.provider.BaseColumns

class TubeAppContract {

    /**
     * Group table contents together in an anonymous companion object class.
     */
    object UsersEntry : BaseColumns {
        const val TABLE_NAME = "t_users"
        const val COLUMN_USER_NAME = "userName"
        const val COLUMN_FULL_NAME = "fullName"
        const val COLUMN_PASSWORD = "password"
    }

    object PlaylistEntry : BaseColumns {
        const val TABLE_NAME = "t_playlist"
        const val COLUMN_USER_NAME = "userName"
        const val COLUMN_YOUTUBE_URL = "youTubeUrl"
    }
}