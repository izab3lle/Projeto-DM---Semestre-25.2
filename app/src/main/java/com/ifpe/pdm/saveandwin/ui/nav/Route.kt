package com.ifpe.pdm.saveandwin.ui.nav

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object UserGroups : Route
    @Serializable
    data object FindGroups : Route
    @Serializable
    data object UserProfile : Route
    @Serializable
    data object GroupPage : Route
    @Serializable
    data object CreateGroup : Route
    @Serializable
    data object Notifications : Route
}