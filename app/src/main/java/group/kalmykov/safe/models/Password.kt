package group.kalmykov.safe.models

data class Password(
    var key: String = "unknown",
    var value: String = null.toString(),
    var description: String? = null,
)