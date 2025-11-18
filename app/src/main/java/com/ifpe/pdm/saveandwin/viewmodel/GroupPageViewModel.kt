package com.ifpe.pdm.saveandwin.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.User
import java.time.LocalDateTime


class GroupPageViewModel : ViewModel() {
    private val _users = getUsersList().toMutableStateList()
    private val _groups = getGroupsList(users)

    val groups
        get() = _groups.toList()
    val users
        get() = _users.toList()

    private fun getGroupsList(users: List<User>): SnapshotStateList<Group> {
        val images = listOf(
            R.drawable.mockup_tigre,
            R.drawable.mockup_formatura,
            R.drawable.mockup_pc_gamer
        )

        val names = listOf(
            "multirão parar de jogar no tigrinho",
            "Economizando pra formatura 2027.1,",
            "Juntando pro pc gamer da Andressa"
        )

        val groups = List(3) { i ->
            Group(
                code = "$i",
                name = names[i],
                creator = users[i],
                description = "Lorem ipsum dolor sit amet",
                image = images[i],
                created = LocalDateTime.now()
            )
        }

        groups.forEach { group ->
            group.members = users
        }

        return groups.toMutableStateList()
    }

    private fun getUsersList() = List(20) { i ->
        User(
            username = "User$i",
            email ="$i@ifpe.br",
            password = "$i"
        )
    }
}