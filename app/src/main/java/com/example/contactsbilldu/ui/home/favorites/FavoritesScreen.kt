package com.example.contactsbilldu.ui.home.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.contactsbilldu.ui.home.ContactItem

@Composable
fun FavoritesScreen(
    favoritesUiState: FavoritesViewModel.FavoritesUiState,
    onContactClick: (contactId: Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(
            favoritesUiState.favoriteContacts,
            key = { it.id }
        ) {
            ContactItem(contact = it, onClick = onContactClick)
        }
    }
}