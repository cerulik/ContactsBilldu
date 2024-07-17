package com.example.contactsbilldu.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.contactsbilldu.R
import com.example.contactsbilldu.ui.home.contacts.ContactsScreen
import com.example.contactsbilldu.ui.home.contacts.ContactsViewModel
import com.example.contactsbilldu.ui.home.favorites.FavoritesScreen
import com.example.contactsbilldu.ui.home.favorites.FavoritesViewModel
import org.koin.androidx.compose.getViewModel

enum class BottomNavScreen {
    Contacts,
    Favorites
}

@Composable
fun HomeScreen(
    onContactSelected: (contactId: Int) -> Unit,
    onAddContactClick: () -> Unit
) {
    var bottomNavScreen by rememberSaveable {
        mutableStateOf(BottomNavScreen.Contacts)
    }

    Column {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    bottomNavScreen = bottomNavScreen,
                    onTabClick = {
                        bottomNavScreen = it
                    }
                )
            },
            floatingActionButton = {
                val fabBackgroundColor = MaterialTheme.colorScheme.primary
                val fabContentColor = MaterialTheme.colorScheme.onPrimary
                FloatingActionButton(
                    onClick = onAddContactClick,
                    containerColor = fabBackgroundColor,
                    contentColor = fabContentColor
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(
                            id = R.string.screen_home_button_add_contact
                        )
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (bottomNavScreen) {
                    BottomNavScreen.Contacts -> {
                        val contactsViewModel = getViewModel<ContactsViewModel>()
                        val contacts = contactsViewModel.contacts.collectAsLazyPagingItems()

                        ContactsScreen(
                            contacts = contacts,
                            onContactClick = onContactSelected,
                            onEvent = {
                                contactsViewModel.onEvent(it)
                            }
                        )
                    }

                    BottomNavScreen.Favorites -> {
                        val favoritesViewModel = getViewModel<FavoritesViewModel>()
                        val favoritesUiState = favoritesViewModel.uiState.collectAsState()

                        FavoritesScreen(
                            favoritesUiState = favoritesUiState.value,
                            onContactClick = onContactSelected
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavScreen: BottomNavScreen,
    onTabClick: (BottomNavScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(
                        id = R.string.screen_home_nav_bar_item_contacts
                    )
                )
            },
            label = {
                Text(
                    stringResource(
                        id = R.string.screen_home_nav_bar_item_contacts
                    )
                )
            },
            selected = bottomNavScreen == BottomNavScreen.Contacts,
            onClick = { onTabClick(BottomNavScreen.Contacts) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                indicatorColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = stringResource(
                        id = R.string.screen_home_nav_bar_item_favorites
                    )
                )
            },
            label = {
                Text(
                    stringResource(
                        id = R.string.screen_home_nav_bar_item_favorites
                    )
                )
            },
            selected = bottomNavScreen == BottomNavScreen.Favorites,
            onClick = { onTabClick(BottomNavScreen.Favorites) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                indicatorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}