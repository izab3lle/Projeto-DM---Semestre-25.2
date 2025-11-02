package com.ifpe.pdm.saveandwin.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

data class Post(
    var user: User,
    var content: String,
    var created: LocalDateTime
) {
    fun getStringDate(format: String) : String {
        val dateFormatter = DateTimeFormatter.ofPattern(format)
        return created.format(dateFormatter)
    }
}