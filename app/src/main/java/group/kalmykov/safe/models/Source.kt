package group.kalmykov.safe.models

import androidx.compose.runtime.mutableStateListOf


data class Source(
    val  host: String,
    val  password: String,
    val  username : String? = null,
    val  description: String? = null,
) {

}