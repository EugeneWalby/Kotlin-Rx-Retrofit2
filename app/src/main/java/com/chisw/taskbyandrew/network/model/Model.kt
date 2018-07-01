package com.chisw.taskbyandrew.network.model

object Model {
    data class HitsResponse(val hits: ArrayList<HitsStoryResponse>)
    data class HitsStoryResponse(val title: String, val author: String)
    data class UserResponse(val id: Long, val username: String, val karma: Long)
}