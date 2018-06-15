package com.chisw.taskbyandrew.network.model

object Model {
    data class Result(val hits: Hits)
    data class Hits(val zero: Zero)
    data class Zero(val title: String)
}