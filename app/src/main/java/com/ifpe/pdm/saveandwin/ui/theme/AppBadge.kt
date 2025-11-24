package com.ifpe.pdm.saveandwin.ui.theme

import com.ifpe.pdm.saveandwin.R

sealed interface AppBadge {
    data object OneWeekStrike {
        val description = "Ofensiva de uma semana"
        val image = R.drawable.outline_face
    }

    data object TwoWeekStrike {
        val description = "Ofensiva de duas semanas"
        val image = R.drawable.outline_face
    }

    data object MonthStrike {
        val description = "Ofensiva de um mês"
        val image = R.drawable.outline_face
    }

    data object Reacted {
        val description = "Reagiu a 25 posts"
        val image = R.drawable.outline_face
    }

    data object Creator {
        val description = "Criou o próprio grupo"
        val image = R.drawable.outline_face
    }
}