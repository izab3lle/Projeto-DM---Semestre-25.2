package com.ifpe.pdm.saveandwin.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

data class Group(
    var code: String,
    var name: String,
    var creator: User,
    var created: LocalDateTime
) {
    var members: List<User> = mutableListOf()
    var posts: List<Post> = mutableListOf()

    fun getStringDate(format: String) : String {
        val dateFormatter = DateTimeFormatter.ofPattern(format)
        return created.format(dateFormatter)
    }
}