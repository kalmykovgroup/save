package group.kalmykov.safe.models

import androidx.compose.runtime.mutableStateListOf

data class Source(
    val name : String = "unknown",
    val passwords : MutableList<Password> = mutableStateListOf()
) {


    init{
        passwords.add(Password(key = "my.site.com", value = "password 1"))
        passwords.add(Password(key = "my.site.com", value = "password 2"))
        passwords.add(Password(key = "my.site.com", value = "password 3"))
    }
}