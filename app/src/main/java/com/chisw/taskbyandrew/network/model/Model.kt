package com.chisw.taskbyandrew.network.model

object Model {
    data class HitsResponse(val hits: ArrayList<HitsItemResponse>)
    data class HitsItemResponse(val title: String)
}