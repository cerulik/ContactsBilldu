package com.example.contactsbilldu

import BillduTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactsbilldu.ui.addcontact.AddContactScreen
import com.example.contactsbilldu.ui.contactdetail.ContactDetailScreen
import com.example.contactsbilldu.ui.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsApp()
        }
    }
}

@Composable
fun ContactsApp() {
    val navController = rememberNavController()

    BillduTheme(
        darkTheme = false
    ) {
        NavHost(
            navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    onContactSelected = {
                        navController.navigate("contact_detail/${it}")
                    },
                    onAddContactClick = {
                        navController.navigate("add_contact")
                    }
                )
            }
            composable("add_contact") {
                AddContactScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onContactSaved = {
                        navController.popBackStack()
                    }
                )
            }
            composable("contact_detail/{contactId}") { backStackEntry ->
                val contactId =
                    backStackEntry.arguments?.getString("contactId")?.toIntOrNull() ?: 0
                ContactDetailScreen(contactId)
            }
        }
    }
}