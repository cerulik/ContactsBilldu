package com.example.contactsbilldu.ui.home.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contactsbilldu.R
import com.example.contactsbilldu.ui.home.ContactItem

@Composable
fun FavoritesScreen(
    favoritesUiState: FavoritesViewModel.FavoritesUiState,
    onContactClick: (contactId: Int) -> Unit
) {
    if (favoritesUiState.favoriteContacts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(16.dp).align(Alignment.Center),
                text = stringResource(R.string.screen_favorites_label_no_favorites),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    } else {
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
}