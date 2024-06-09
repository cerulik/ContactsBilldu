package com.example.contactsbilldu.ui.home.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.contactsbilldu.ui.home.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contactsUiState: ContactsViewModel.ContactsUiState,
    onContactClick: (contactId: Int) -> Unit,
    onEvent: (ContactsViewModel.ContactsEvent) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            contactsUiState.contacts,
            key = { it.id }
        ) {
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd
                }, positionalThreshold = { 150.dp.toPx() }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val color = when (direction) {
                        DismissDirection.StartToEnd -> MaterialTheme.colorScheme.secondary
                        DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
                    }
                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Favorite
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 16.dp),
                            contentAlignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                            }
                        ) {
                            Icon(icon, contentDescription = null, tint = Color.White)
                        }
                    }
                },
                dismissContent = {
                    ContactItem(
                        contact = it,
                        onClick = onContactClick
                    )
                }
            )

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                LaunchedEffect(dismissState) {
                    onEvent(ContactsViewModel.ContactsEvent.ContactDeleteClicked(it.id))
                    dismissState.reset()
                }
            } else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                LaunchedEffect(dismissState) {
                    onEvent(ContactsViewModel.ContactsEvent.ContactFavoriteClicked(it))
                    dismissState.reset()
                }
            }
        }
    }
}