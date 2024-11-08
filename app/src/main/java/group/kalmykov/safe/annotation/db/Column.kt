package group.kalmykov.safe.annotation.db

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(val name: String, val min: Int = -1, val max: Int = -1)
