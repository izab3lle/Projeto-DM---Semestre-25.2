package com.ifpe.pdm.saveandwin.model

import com.ifpe.pdm.saveandwin.ui.theme.AppBadge
import java.time.LocalDateTime

data class Badge(
    val badge: AppBadge,
    val wonDate: LocalDateTime
)
