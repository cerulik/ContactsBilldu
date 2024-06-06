package com.example.contactsbilldu.ui.home.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.contactsbilldu.ui.home.ContactItem

@Composable
fun FavoritesScreen(
    favoritesUiState: FavoritesViewModel.FavoritesUiState,
    onContactClick: (contactId: Int) -> Unit
) {
    LazyColumn {
        items(
            favoritesUiState.favoriteContacts,
            key = { it.id }
        ) {
            ContactItem(contact = it, onClick = onContactClick)
        }
    }
}