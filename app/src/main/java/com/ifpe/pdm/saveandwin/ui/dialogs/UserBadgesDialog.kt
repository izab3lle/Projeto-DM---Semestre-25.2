package com.ifpe.pdm.saveandwin.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen

@Composable
fun UserBadgesDialog(username: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Insígnias de ${username}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
                Spacer(Modifier.size(5.dp))
                HorizontalDivider()
                Spacer(Modifier.size(15.dp))

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(count = 4, key = { it }) { badge ->
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (i in 0..2) {
                                UserBadge()
                                if (i < 2) Spacer(Modifier.size(5.dp))
                            }
                        }
                        Spacer(Modifier.size(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun UserBadge() {
    Icon(
        painter = painterResource(id = R.drawable.outline_award_star_24),  // id = badge.image
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(80.dp)
            .background(GreenSW),
        tint = MintGreen
    )
}