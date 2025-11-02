package com.ifpe.pdm.saveandwin.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ifpe.pdm.saveandwin.model.User

class LoginViewModel : ViewModel() {
    private val _users = getUsersList().toMutableStateList()
    private var _loggedUser: User = User("", "", "")
    val users
        get() = _users.toList()

    // "Loga" apenas verificando se o usário existe na lista _users
    fun login(email: String, password: String) : Boolean {
        _users.forEach { user ->
            if(email == user.email && password == user.password) {
                _loggedUser = user
                return true
            }
        }
        return false
    }

    fun getLoggedUser(): String {
        return _loggedUser.username
    }

    private fun getUsersList() = List(5) { i ->
        User(username = "User$i",
            email ="email$i@ifpe.br",
            password = "password$i")
    }
}