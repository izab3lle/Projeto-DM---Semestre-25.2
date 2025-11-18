package com.ifpe.pdm.saveandwin.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.vector.Group
import androidx.lifecycle.ViewModel
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.User
import java.time.LocalDate
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    private val _users = getUsersList().toMutableStateList()

    val users
        get() = _users.toList()

    private fun getUsersList() = List(5) { i ->
        User(username = "User$i",
            email ="email$i@ifpe.br",
            password = "password$i")
    }
}