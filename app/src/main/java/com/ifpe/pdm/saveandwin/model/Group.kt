package com.ifpe.pdm.saveandwin.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

data class Group(
    var name: String,
    var creator: User,
    var created: LocalDateTime,
    var description: String?,
    var image: Int,
    var visibility: GroupVisibility
) {
    var members: List<User> = mutableStateListOf()
    var posts: MutableList<Post> = mutableStateListOf()

    fun getCreatedDateString() : String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/mm/YYYY")
        return created.format(dateFormatter)
    }
}