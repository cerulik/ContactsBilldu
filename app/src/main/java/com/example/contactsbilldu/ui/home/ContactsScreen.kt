package com.example.contactsbilldu.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ContactsScreen(
    onContactClick: (contactId: Int) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(10) {
            ListItem(
                headlineContent = { Text("Meno Priezvisko #$it") },
                trailingContent = {
                    Row {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                Toast.makeText(
                                    context,
                                    "Favorite button clicked on contact #$it",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Toggle Favorite")
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                Toast.makeText(
                                    context,
                                    "Delete button clicked on contact #$it",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                },
                modifier = Modifier.clickable {
                    onContactClick(it)
                }
            )
        }
    }
}