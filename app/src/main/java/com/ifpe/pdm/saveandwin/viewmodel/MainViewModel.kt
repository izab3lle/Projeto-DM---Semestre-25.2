package com.ifpe.pdm.saveandwin.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.GroupVisibility
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.model.User
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    private val _users = getUsersList().toMutableStateList()
    private val _userGroups = getUserGroupsList().toMutableStateList()
    private val _allGroups = getAllGroupsList().toMutableStateList()

    val loggedUser = users[2]
    val users
        get() = _users.toList()
    val userGroups
        get() = _userGroups.toList()
    val allGroups
        get() = _allGroups.toList()
    var selectedGroup: Group? = null
    var selectedPost: Post? = null
    var selectedUser: User = loggedUser

    private fun getUsersList(): List<User> {
        val images = getProfilePictures()
        val names: List<String> = listOf(
            "Pedro Lucas", "Carlos Oliveira", "Fernanda Santos", "Roberto Souza", "Patrícia Lima", "Gustavo Mendes",
            "Vanessa Costa", "André Pereira", "Tainá Rocha", "Júlio Alves", "Carla Gomes", "Alexandre Ribeiro", "Mônica Barros",
            "Sérgio Martins", "Renata Ferreira", "Paulo Castro", "Erika Pires", "Ricardo Nunes", "Viviane Freitas", "Diego Santos"
        )

        val users = List(images.size) { i ->
            User(username = names[i],
                email ="email$i@ifpe.br",
                password = "$i",
                points = (0..1000).random(),
                avatar = images[i]
            )
        }

        return users
    }

    private fun getUserGroupsList(): List<Group> {
        val lorem: Lorem = LoremIpsum.getInstance();

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

        val groups = List(names.size) { i ->
            Group(
                name = names[i],
                creator = users[i],
                description = lorem.getWords(5, 5),
                image = images[i],
                created = LocalDateTime.now(),
                visibility = GroupVisibility.PUBLIC
            )
        }

        groups.forEach { group ->
            group.members = users
            group.members.forEach { user ->
               group.posts.add(
                    Post(user, lorem.getWords(5, 10), LocalDateTime.now())
               )
            }
        }

        return groups
    }

    private fun getAllGroupsList(): List<Group> {
        var code = userGroups.size + 1
        val lorem: Lorem = LoremIpsum.getInstance();

        val images = listOf(
            R.drawable.mockup_viagem, R.drawable.mockup_temu,
            R.drawable.mockup_postits, R.drawable.mockup_notebook,
            R.drawable.mockup_pc_gamer, R.drawable.mockup_tigre,
            R.drawable.mockup_formatura, R.drawable.mockup_viagem,
            R.drawable.mockup_temu, R.drawable.mockup_notebook,
            R.drawable.mockup_pc_gamer, R.drawable.mockup_tigre
        )

        var groups = MutableList(images.size) { i ->
            Group(
                name = "Placeholder Grupo ${code++}",
                creator = users[i],
                description = lorem.getWords(5, 5),
                image = images[i],
                created = LocalDateTime.now(),
                visibility = GroupVisibility.PUBLIC
            )
        }

        groups.forEach { group ->
            group.members = users
            group.members.forEach { user ->
                group.posts.add(
                    Post(user, lorem.getWords(5, 30), LocalDateTime.now())
                )
            }
        }

        userGroups.forEach { group -> groups.add(group) }

        return groups.toList()
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
        _userGroups.add(
            Group(
                name = name.trim(),
                creator = creator,
                created = LocalDateTime.now(),
                description = description,
                image = image,
                visibility = visibility
            )
        )
    }

    fun removeGroup(name: String) {
        for (group in _userGroups) {
            if (name.lowercase().equals(group.name.lowercase())) {
                _userGroups.remove(group)
            }
        }
    }

    fun searchGroup(group: String) : Group? {
        var result: Group? = null
        for(g in allGroups) {
            if(g.name == group)
                result = g
        }

        return result
    }

    private fun getProfilePictures(): List<Int> = listOf(
        R.drawable.mockup_profile_1,
        R.drawable.mockup_profile_2,
        R.drawable.mockup_profile_13,
        R.drawable.mockup_profile_4,
        R.drawable.mockup_profile_5,
        R.drawable.mockup_profile_6,
        R.drawable.mockup_profile_7,
        R.drawable.mockup_profile_8,
        R.drawable.mockup_profile_9,
        R.drawable.mockup_profile_10,
        R.drawable.mockup_profile_11,
        R.drawable.mockup_profile_12,
        R.drawable.mockup_profile_3,
        R.drawable.mockup_profile_1,
        R.drawable.mockup_profile_2,
        R.drawable.mockup_profile_3,
        R.drawable.mockup_profile_4,
        R.drawable.mockup_profile_5,
        R.drawable.mockup_profile_6,
        R.drawable.mockup_profile_7,
    )

    fun getUserPosts(user: User) : List<Pair<List<Post>, Int>> {
        val posts: MutableList<Pair<List<Post>, Int>> = mutableListOf()
        _userGroups.forEach { group ->
            posts.add(
                group.posts.filter { post -> post.user.email.equals(user.email) } to group.image
            )
        }

        return posts.toList()
    }

    fun addPost(content: String, user: User, group: Group) {
        val result = searchGroup(group.name)
        when(result) {
            null -> {
                throw NoSuchElementException("O grupo não foi encontrado")
            }

            else -> {
                result.let {
                    result.posts.toMutableStateList()
                        .add(Post(user, content, LocalDateTime.now()))
                }
            }
        }
    }

    fun enterGroup(group: Group) {
        group.members.toMutableStateList().add(loggedUser)
        loggedUser.groups.toMutableStateList().add(group)
    }
}