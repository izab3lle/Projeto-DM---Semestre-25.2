package com.ifpe.pdm.saveandwin.model

data class User(
    var username: String,
    var email: String,
    var password : String,
    var points : Int = 0
) {
    var groups: List<Group> = mutableListOf()
    var badges: List<Badge> = mutableListOf()
}