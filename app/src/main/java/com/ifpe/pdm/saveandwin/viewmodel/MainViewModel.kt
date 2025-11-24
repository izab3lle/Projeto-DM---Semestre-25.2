package com.ifpe.pdm.saveandwin.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.vector.Group
import androidx.lifecycle.ViewModel
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Badge
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.GroupVisibility
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.model.User
import java.time.LocalDate
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    private val _users = getUsersList().toMutableStateList()
    private val _userGroups = getUserGroupsList().toMutableStateList()
    private val _allGroups = getAllGroupsList().toMutableStateList()

    val users
        get() = _users.toList()
    val userGroups
        get() = _userGroups.toList()
    val allGroups
        get() = _allGroups.toList()
    var selectedGroup: Group? = null

    private fun getUsersList(): List<User> {
        val names: List<String> = listOf(
            "Maria Vitória", "Carlos Oliveira", "Fernanda Santos", "Roberto Souza", "Patrícia Lima", "Gustavo Mendes",
            "Vanessa Costa", "André Pereira", "Tainá Rocha", "Júlio Alves", "Carla Gomes", "Alexandre Ribeiro",
            "Mônica Barros", "Sérgio Martins", "Renata Ferreira", "Paulo Castro", "Erika Pires", "Ricardo Nunes",
            "Viviane Freitas", "Diego Santos"
        )

        val users = List(20) { i ->
            User(username = names[i],
                email ="email$i@ifpe.br",
                password = "$i",
                points = (0..20000).random())
        }

        return users
    }

    private fun getUserGroupsList(): List<Group> {
        val images = listOf(
            R.drawable.mockup_pc_gamer,
            R.drawable.mockup_tigre,
            R.drawable.mockup_formatura,
        )

        val names = listOf(
            "Juntando pro pc gamer da Andressa",
            "multirão parar de jogar no tigrinho",
            "Economizando pra formatura 2027.1"
        )

        val groups = List(3) { i ->
            Group(
                name = names[i],
                creator = users[i],
                description = "Lorem ipsum dolor sit amet",
                image = images[i],
                created = LocalDateTime.now(),
                visibility = GroupVisibility.PUBLIC
            )
        }

        groups.forEach { group ->
            group.members = users
            group.members.forEach { user ->
               group.posts.add(
                    Post(user, "Lorem ipsum dolor sit amet", LocalDateTime.now())
                )
            }
        }

        return groups
    }

    private fun getAllGroupsList(): List<Group> {
        var code = userGroups.size + 1

        val groups = List(users.size) { i ->
            Group(
                name = "Grupo ${code++}",
                creator = users[i],
                description = "Lorem ipsum dolor sit amet",
                image = R.drawable.mockup_pc_gamer,
                created = LocalDateTime.now(),
                visibility = GroupVisibility.PUBLIC
            )
        }

        groups.forEach { group ->
            group.members = users
        }

        return groups
    }

    fun getMockupUser(): User {
        return _users[0]
    }

    fun addGroup(
        name: String,
        creator: User,
        description: String?,
        image: Int,
        visibility: GroupVisibility
    ) {
        _userGroups.forEach { group ->
            if(name.trim().lowercase().equals(group.name.lowercase())) {
                throw IllegalArgumentException("Um grupo com o mesmo nome já existe")
            }
        }
        _userGroups.add(Group(
            name = name.trim(),
            creator = creator,
            created = LocalDateTime.now(),
            description = description,
            image = image,
            visibility = visibility
        ))
    }

    fun removeGroup(name: String) {
        for (group in _userGroups) {
            if (name.lowercase().equals(group.name.lowercase())) {
                _userGroups.remove(group)
            }
        }
    }

    fun getUserPosts(user: User) : List<Post> {
        val posts: MutableList<Post> = mutableListOf()
        user.groups.forEach { group ->
            posts += group.posts.filter { post -> post.user.email.equals(user.email) }
        }
        return posts
    }

    fun addPost(content: String, user: User, groupName: String) {
        for (group in _userGroups) {
            if(group.name.lowercase().equals(groupName.lowercase())) {
                group.posts.toMutableStateList().add(
                    Post(user, content, LocalDateTime.now()
                ))
            }
        }
    }

    fun enterGroup(user: User, group: Group) {
        group.members.toMutableStateList().add(user)
        user.groups.toMutableStateList().add(group)
    }
}