package com.example.contactsbilldu.ui.home.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.contactsbilldu.R
import com.example.contactsbilldu.data.source.local.entity.Contact
import com.example.contactsbilldu.ui.home.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contacts: LazyPagingItems<Contact>,
    onContactClick: (contactId: Int) -> Unit,
    onEvent: (ContactsViewModel.ContactsEvent) -> Unit
) {
    var searchQuery by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onEvent(ContactsViewModel.ContactsEvent.SearchQueryChanged(it.text))
            },
            label = { Text(stringResource(id = R.string.screen_contacts_text_field_search_label)) },
            leadingIcon = {
                Icon(Icons.Default.Search,
                    contentDescription = stringResource(
                        id = R.string.screen_contacts_text_field_search_label
                    )
                )
            },
            shape = MaterialTheme.shapes.small,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        if (contacts.itemCount == 0 && contacts.loadState.refresh !is LoadState.Loading) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(16.dp).align(Alignment.Center),
                    text = stringResource(R.string.screen_contacts_label_no_contacts),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(contacts.itemCount) { index ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd
                        }, positionalThreshold = { 150.dp.toPx() }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        ),
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
                                contact = contacts[index]!!,
                                onClick = onContactClick
                            )
                        }
                    )

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        LaunchedEffect(dismissState) {
                            onEvent(ContactsViewModel.ContactsEvent.ContactDeleteClicked(contacts[index]!!.id))
                            dismissState.reset()
                        }
                    } else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                        LaunchedEffect(dismissState) {
                            onEvent(ContactsViewModel.ContactsEvent.ContactFavoriteClicked(contacts[index]!!))
                            dismissState.reset()
                        }
                    }
                }

                contacts.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = contacts.loadState.append as LoadState.Error
                            item {
                                Text(
                                    text = "Error: ${e.error.localizedMessage}",
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}