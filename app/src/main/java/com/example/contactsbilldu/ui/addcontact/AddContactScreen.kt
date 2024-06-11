package com.example.contactsbilldu.ui.addcontact

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.contactsbilldu.R
import com.example.contactsbilldu.data.source.local.entity.Contact
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddContactScreen(
    oldContactId: Int? = null,
    onBackClick: () -> Unit,
    onContactSaved: () -> Unit
) {
    val addContactViewModel = getViewModel<AddContactViewModel>(
        parameters = { parametersOf(oldContactId.let { it ?: -1 }) }
    )
    val addContactUiState = addContactViewModel.uiState.collectAsState()

    var firstName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var isFirstNameDirty by rememberSaveable {
        mutableStateOf(false)
    }
    var isFirstNameErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var lastName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var phoneNumber by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var isPhoneNumberDirty by rememberSaveable {
        mutableStateOf(false)
    }
    var isPhoneNumberErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isFavorite by rememberSaveable { mutableStateOf(false) }
    var selectedCountry by rememberSaveable(
        stateSaver = Saver<Country, Any>(
            save = { it.name },
            restore = { CountryData.countryList.find { country -> country.name == it } }
        )
    ) {
        mutableStateOf(
            CountryData.countryList.find { it.name == "Slovakia" }
                ?: CountryData.countryList.first()
        )
    }

    val maxNameFieldLength = 50
    val maxPhoneNumberLength = 15

    LaunchedEffect(addContactViewModel) {
        addContactViewModel.effect.collect {
            when (it) {
                is AddContactViewModel.AddContactEffect.ContactSaved -> {
                    onContactSaved.invoke()
                }
            }
        }
    }

    LaunchedEffect(addContactUiState.value.oldContact) {
        addContactUiState.value.oldContact?.let {
            firstName = TextFieldValue(it.firstName)
            lastName = TextFieldValue(it.lastName)
            selectedCountry = CountryData.countryList.find {
                country -> country.code == it.countryCode
            } ?: CountryData.countryList.find { country ->
                country.name == "Slovakia"
            } ?: CountryData.countryList.first()
            phoneNumber = TextFieldValue(it.phoneNumber.substring(it.countryCode.length))
            isFavorite = it.isFavorite
        }
    }

    Scaffold(
        topBar = {
            AddContactTopBar(
                topBarTitleRes = addContactUiState.value.oldContact?.let {
                    R.string.screen_add_contact_title_edit
                } ?: R.string.screen_add_contact_title_add,
                onBackClick = {
                    onBackClick.invoke()
                }
            )
        }
    ) { innerPadding ->
        if (addContactUiState.value.isContactLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
        } else {
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = firstName,
                        onValueChange = {
                            if (isFirstNameDirty) {
                                isFirstNameErrorVisible = it.text.isEmpty()
                            } else if (it.text.isNotEmpty()) {
                                isFirstNameDirty = true
                            }

                            if (it.text.length <= maxNameFieldLength) {
                                firstName = it
                            }
                        },
                        label = {
                            Text(
                                stringResource(R.string.screen_add_contact_text_field_first_name_label)
                            )
                        },
                        shape = MaterialTheme.shapes.small,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    if (isFirstNameErrorVisible) {
                        Text(
                            text = stringResource(R.string.screen_add_contact_text_field_first_name_error),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = lastName,
                        onValueChange = {
                            if (it.text.length <= maxNameFieldLength) {
                                lastName = it
                            }
                        },
                        label = {
                            Text(
                                stringResource(R.string.screen_add_contact_text_field_last_name_label)
                            )
                        },
                        shape = MaterialTheme.shapes.small,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min)
                    ) {
                        CountryCodeDropdown(
                            modifier = Modifier.padding(top = 8.dp),
                            selectedCountry
                        ) { newCountry ->
                            selectedCountry = newCountry
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = phoneNumber,
                                onValueChange = {
                                    if (isPhoneNumberDirty) {
                                        isPhoneNumberErrorVisible = it.text.isEmpty()
                                    } else if (it.text.isNotEmpty()) {
                                        isPhoneNumberDirty = true
                                    }

                                    if (it.text.all { char -> char.isDigit() }
                                        && it.text.length <= maxPhoneNumberLength) {
                                        phoneNumber = it
                                    }
                                },
                                label = {
                                    Text(
                                        stringResource(R.string.screen_add_contact_text_field_phone_number_label)
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                shape = MaterialTheme.shapes.small
                            )
                            if (isPhoneNumberErrorVisible) {
                                Text(
                                    text = stringResource(
                                        R.string.screen_add_contact_text_field_phone_number_error
                                    ),
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isFavorite,
                            onCheckedChange = { isFavorite = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.screen_add_contact_check_box_label))
                    }
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .weight(1f)
                    )
                    if (addContactUiState.value.isContactSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    } else {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = firstName.text.isNotEmpty() && phoneNumber.text.isNotEmpty(),
                            onClick = {
                                val oldContact = addContactUiState.value.oldContact
                                val contact = oldContact?.copy(
                                    firstName = firstName.text,
                                    lastName = lastName.text,
                                    countryCode = selectedCountry.code,
                                    phoneNumber = selectedCountry.code + phoneNumber.text,
                                    isFavorite = isFavorite
                                ) ?: Contact(
                                    firstName = firstName.text,
                                    lastName = lastName.text,
                                    countryCode = selectedCountry.code,
                                    phoneNumber = selectedCountry.code + phoneNumber.text,
                                    isFavorite = isFavorite
                                )

                                addContactViewModel.onEvent(
                                    AddContactViewModel.AddContactEvent.SaveContactClicked(contact)
                                )
                            }
                        ) {
                            Text("Save Contact")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactTopBar(
    @StringRes topBarTitleRes: Int,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(id = topBarTitleRes), style = MaterialTheme.typography.titleLarge)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.screen_add_contact_back_description)
                )
            }
        }
    )
}