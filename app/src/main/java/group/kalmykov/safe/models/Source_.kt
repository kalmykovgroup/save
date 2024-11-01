package group.kalmykov.safe.models

import androidx.compose.runtime.mutableStateListOf

data class Source_(
    val name : String = "unknown",
    val passwords : MutableList<Password> = mutableStateListOf()
) {


    init{
        passwords.add(Password(key = "my.site1.com", value = "password 1 password 1 password 1 yiubbu uhu"))
        passwords.add(Password(key = "my.site2.com", value = "password 2"))
        passwords.add(Password(key = "my.site3.com", value = "password 3"))
    }
}