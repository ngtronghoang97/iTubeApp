package com.example.itubeapp.model

class PlayListModel {
    var itemId : Long? = null
    var userName : String? = null
    var youTubeUrl : String? = null

    /**
     * Secondary constructor
     */
    constructor(
        itemId: Long?,
        userName: String?,
        youTubeUrl: String?
    ) {
        this.itemId = itemId
        this.userName = userName
        this.youTubeUrl = youTubeUrl
    }
}