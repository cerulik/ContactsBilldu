package com.example.contactsbilldu.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    onContactSelected: (contactId: Int) -> Unit,
    onAddContactClick: () -> Unit
) {
    val navController = rememberNavController()

    Column {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            },
            floatingActionButton = {
                val fabBackgroundColor = MaterialTheme.colorScheme.primary
                val fabContentColor = MaterialTheme.colorScheme.onPrimary
                FloatingActionButton(
                    onClick = onAddContactClick,
                    containerColor = fabBackgroundColor,
                    contentColor = fabContentColor
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Contact")
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = "contacts",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("contacts") {
                    ContactsScreen(onContactClick = onContactSelected)
                }
                composable("favorites") {
                    FavoritesScreen(onContactClick = onContactSelected)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Contacts"
                )
            },
            label = { Text("Contacts") },
            selected = currentRoute == "contacts",
            onClick = { navController.navigate("contacts") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = { Text("Favorites") },
            selected = currentRoute == "favorites",
            onClick = { navController.navigate("favorites") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}