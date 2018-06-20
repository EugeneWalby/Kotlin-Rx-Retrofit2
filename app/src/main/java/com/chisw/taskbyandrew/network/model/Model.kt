package com.chisw.taskbyandrew.network.model

object Model {
    data class HitsResponse(val hits: List<HitsItemResponse>)
    data class HitsItemResponse(val title: String)
}