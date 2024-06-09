package com.example.contactsbilldu.ui.addcontact

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(val name: String, val code: String, val flag: String) : Parcelable

object CountryData {
    val countryList = listOf(
        Country("Afghanistan", "+93", "🇦🇫"),
        Country("Albania", "+355", "🇦🇱"),
        Country("Algeria", "+213", "🇩🇿"),
        Country("Andorra", "+376", "🇦🇩"),
        Country("Angola", "+244", "🇦🇴"),
        Country("Antigua and Barbuda", "+1-268", "🇦🇬"),
        Country("Argentina", "+54", "🇦🇷"),
        Country("Armenia", "+374", "🇦🇲"),
        Country("Australia", "+61", "🇦🇺"),
        Country("Austria", "+43", "🇦🇹"),
        Country("Azerbaijan", "+994", "🇦🇿"),
        Country("Bahamas", "+1-242", "🇧🇸"),
        Country("Bahrain", "+973", "🇧🇭"),
        Country("Bangladesh", "+880", "🇧🇩"),
        Country("Barbados", "+1-246", "🇧🇧"),
        Country("Belarus", "+375", "🇧🇾"),
        Country("Belgium", "+32", "🇧🇪"),
        Country("Belize", "+501", "🇧🇿"),
        Country("Benin", "+229", "🇧🇯"),
        Country("Bhutan", "+975", "🇧🇹"),
        Country("Bolivia", "+591", "🇧🇴"),
        Country("Bosnia and Herzegovina", "+387", "🇧🇦"),
        Country("Botswana", "+267", "🇧🇼"),
        Country("Brazil", "+55", "🇧🇷"),
        Country("Brunei", "+673", "🇧🇳"),
        Country("Bulgaria", "+359", "🇧🇬"),
        Country("Burkina Faso", "+226", "🇧🇫"),
        Country("Burundi", "+257", "🇧🇮"),
        Country("Cabo Verde", "+238", "🇨🇻"),
        Country("Cambodia", "+855", "🇰🇭"),
        Country("Cameroon", "+237", "🇨🇲"),
        Country("Canada", "+1", "🇨🇦"),
        Country("Central African Republic", "+236", "🇨🇫"),
        Country("Chad", "+235", "🇹🇩"),
        Country("Chile", "+56", "🇨🇱"),
        Country("China", "+86", "🇨🇳"),
        Country("Colombia", "+57", "🇨🇴"),
        Country("Comoros", "+269", "🇰🇲"),
        Country("Congo, Democratic Republic of the", "+243", "🇨🇩"),
        Country("Congo, Republic of the", "+242", "🇨🇬"),
        Country("Costa Rica", "+506", "🇨🇷"),
        Country("Croatia", "+385", "🇭🇷"),
        Country("Cuba", "+53", "🇨🇺"),
        Country("Cyprus", "+357", "🇨🇾"),
        Country("Czech Republic", "+420", "🇨🇿"),
        Country("Denmark", "+45", "🇩🇰"),
        Country("Djibouti", "+253", "🇩🇯"),
        Country("Dominica", "+1-767", "🇩🇲"),
        Country("Dominican Republic", "+1-809", "🇩🇴"),
        Country("Ecuador", "+593", "🇪🇨"),
        Country("Egypt", "+20", "🇪🇬"),
        Country("El Salvador", "+503", "🇸🇻"),
        Country("Equatorial Guinea", "+240", "🇬🇶"),
        Country("Eritrea", "+291", "🇪🇷"),
        Country("Estonia", "+372", "🇪🇪"),
        Country("Eswatini", "+268", "🇸🇿"),
        Country("Ethiopia", "+251", "🇪🇹"),
        Country("Fiji", "+679", "🇫🇯"),
        Country("Finland", "+358", "🇫🇮"),
        Country("France", "+33", "🇫🇷"),
        Country("Gabon", "+241", "🇬🇦"),
        Country("Gambia", "+220", "🇬🇲"),
        Country("Georgia", "+995", "🇬🇪"),
        Country("Germany", "+49", "🇩🇪"),
        Country("Ghana", "+233", "🇬🇭"),
        Country("Greece", "+30", "🇬🇷"),
        Country("Grenada", "+1-473", "🇬🇩"),
        Country("Guatemala", "+502", "🇬🇹"),
        Country("Guinea", "+224", "🇬🇳"),
        Country("Guinea-Bissau", "+245", "🇬🇼"),
        Country("Guyana", "+592", "🇬🇾"),
        Country("Haiti", "+509", "🇭🇹"),
        Country("Honduras", "+504", "🇭🇳"),
        Country("Hungary", "+36", "🇭🇺"),
        Country("Iceland", "+354", "🇮🇸"),
        Country("India", "+91", "🇮🇳"),
        Country("Indonesia", "+62", "🇮🇩"),
        Country("Iran", "+98", "🇮🇷"),
        Country("Iraq", "+964", "🇮🇶"),
        Country("Ireland", "+353", "🇮🇪"),
        Country("Israel", "+972", "🇮🇱"),
        Country("Italy", "+39", "🇮🇹"),
        Country("Jamaica", "+1-876", "🇯🇲"),
        Country("Japan", "+81", "🇯🇵"),
        Country("Jordan", "+962", "🇯🇴"),
        Country("Kazakhstan", "+7", "🇰🇿"),
        Country("Kenya", "+254", "🇰🇪"),
        Country("Kiribati", "+686", "🇰🇮"),
        Country("Kuwait", "+965", "🇰🇼"),
        Country("Kyrgyzstan", "+996", "🇰🇬"),
        Country("Laos", "+856", "🇱🇦"),
        Country("Latvia", "+371", "🇱🇻"),
        Country("Lebanon", "+961", "🇱🇧"),
        Country("Lesotho", "+266", "🇱🇸"),
        Country("Liberia", "+231", "🇱🇷"),
        Country("Libya", "+218", "🇱🇾"),
        Country("Liechtenstein", "+423", "🇱🇮"),
        Country("Lithuania", "+370", "🇱🇹"),
        Country("Luxembourg", "+352", "🇱🇺"),
        Country("Madagascar", "+261", "🇲🇬"),
        Country("Malawi", "+265", "🇲🇼"),
        Country("Malaysia", "+60", "🇲🇾"),
        Country("Maldives", "+960", "🇲🇻"),
        Country("Mali", "+223", "🇲🇱"),
        Country("Malta", "+356", "🇲🇹"),
        Country("Marshall Islands", "+692", "🇲🇭"),
        Country("Mauritania", "+222", "🇲🇷"),
        Country("Mauritius", "+230", "🇲🇺"),
        Country("Mexico", "+52", "🇲🇽"),
        Country("Micronesia", "+691", "🇫🇲"),
        Country("Moldova", "+373", "🇲🇩"),
        Country("Monaco", "+377", "🇲🇨"),
        Country("Mongolia", "+976", "🇲🇳"),
        Country("Montenegro", "+382", "🇲🇪"),
        Country("Morocco", "+212", "🇲🇦"),
        Country("Mozambique", "+258", "🇲🇿"),
        Country("Myanmar", "+95", "🇲🇲"),
        Country("Namibia", "+264", "🇳🇦"),
        Country("Nauru", "+674", "🇳🇷"),
        Country("Nepal", "+977", "🇳🇵"),
        Country("Netherlands", "+31", "🇳🇱"),
        Country("New Zealand", "+64", "🇳🇿"),
        Country("Nicaragua", "+505", "🇳🇮"),
        Country("Niger", "+227", "🇳🇪"),
        Country("Nigeria", "+234", "🇳🇬"),
        Country("North Korea", "+850", "🇰🇵"),
        Country("North Macedonia", "+389", "🇲🇰"),
        Country("Norway", "+47", "🇳🇴"),
        Country("Oman", "+968", "🇴🇲"),
        Country("Pakistan", "+92", "🇵🇰"),
        Country("Palau", "+680", "🇵🇼"),
        Country("Palestine", "+970", "🇵🇸"),
        Country("Panama", "+507", "🇵🇦"),
        Country("Papua New Guinea", "+675", "🇵🇬"),
        Country("Paraguay", "+595", "🇵🇾"),
        Country("Peru", "+51", "🇵🇪"),
        Country("Philippines", "+63", "🇵🇭"),
        Country("Poland", "+48", "🇵🇱"),
        Country("Portugal", "+351", "🇵🇹"),
        Country("Qatar", "+974", "🇶🇦"),
        Country("Romania", "+40", "🇷🇴"),
        Country("Russia", "+7", "🇷🇺"),
        Country("Rwanda", "+250", "🇷🇼"),
        Country("Saint Kitts and Nevis", "+1-869", "🇰🇳"),
        Country("Saint Lucia", "+1-758", "🇱🇨"),
        Country("Saint Vincent and the Grenadines", "+1-784", "🇻🇨"),
        Country("Samoa", "+685", "🇼🇸"),
        Country("San Marino", "+378", "🇸🇲"),
        Country("Sao Tome and Principe", "+239", "🇸🇹"),
        Country("Saudi Arabia", "+966", "🇸🇦"),
        Country("Senegal", "+221", "🇸🇳"),
        Country("Serbia", "+381", "🇷🇸"),
        Country("Seychelles", "+248", "🇸🇨"),
        Country("Sierra Leone", "+232", "🇸🇱"),
        Country("Singapore", "+65", "🇸🇬"),
        Country("Slovakia", "+421", "🇸🇰"),
        Country("Slovenia", "+386", "🇸🇮"),
        Country("Solomon Islands", "+677", "🇸🇧"),
        Country("Somalia", "+252", "🇸🇴"),
        Country("South Africa", "+27", "🇿🇦"),
        Country("South Korea", "+82", "🇰🇷"),
        Country("South Sudan", "+211", "🇸🇸"),
        Country("Spain", "+34", "🇪🇸"),
        Country("Sri Lanka", "+94", "🇱🇰"),
        Country("Sudan", "+249", "🇸🇩"),
        Country("Suriname", "+597", "🇸🇷"),
        Country("Sweden", "+46", "🇸🇪"),
        Country("Switzerland", "+41", "🇨🇭"),
        Country("Syria", "+963", "🇸🇾"),
        Country("Taiwan", "+886", "🇹🇼"),
        Country("Tajikistan", "+992", "🇹🇯"),
        Country("Tanzania", "+255", "🇹🇿"),
        Country("Thailand", "+66", "🇹🇭"),
        Country("Timor-Leste", "+670", "🇹🇱"),
        Country("Togo", "+228", "🇹🇬"),
        Country("Tonga", "+676", "🇹🇴"),
        Country("Trinidad and Tobago", "+1-868", "🇹🇹"),
        Country("Tunisia", "+216", "🇹🇳"),
        Country("Turkey", "+90", "🇹🇷"),
        Country("Turkmenistan", "+993", "🇹🇲"),
        Country("Tuvalu", "+688", "🇹🇻"),
        Country("Uganda", "+256", "🇺🇬"),
        Country("Ukraine", "+380", "🇺🇦"),
        Country("United Arab Emirates", "+971", "🇦🇪"),
        Country("United Kingdom", "+44", "🇬🇧"),
        Country("United States", "+1", "🇺🇸"),
        Country("Uruguay", "+598", "🇺🇾"),
        Country("Uzbekistan", "+998", "🇺🇿"),
        Country("Vanuatu", "+678", "🇻🇺"),
        Country("Vatican City", "+379", "🇻🇦"),
        Country("Venezuela", "+58", "🇻🇪"),
        Country("Vietnam", "+84", "🇻🇳"),
        Country("Yemen", "+967", "🇾🇪"),
        Country("Zambia", "+260", "🇿🇲"),
        Country("Zimbabwe", "+263", "🇿🇼")
    )
}

@Composable
fun CountryCodeDropdown(
    modifier: Modifier,
    selectedCountry: Country,
    onCountryChange: (Country) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            modifier = modifier.fillMaxHeight(),
            shape = MaterialTheme.shapes.small,
            onClick = { expanded = true }
        ) {
            Text(
                text = "${selectedCountry.flag} ${selectedCountry.code}",
                color = MaterialTheme.colorScheme.background,
                fontSize = 18.sp
            )
        }


        DropdownMenu(
            modifier = Modifier.heightIn(max = 300.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 0.dp, y = 8.dp)
        ) {
            // TODO: use lazy list on dropdown menu if possible. if not change to custom card
            CountryData.countryList.forEach { country ->
                DropdownMenuItem(
                    text = {
                        Text(text = "${country.flag} ${country.name} (${country.code})")
                    },
                    onClick = {
                        onCountryChange(country)
                        expanded = false
                    }
                )
            }
        }
    }
}