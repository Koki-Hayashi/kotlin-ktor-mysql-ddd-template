package com.templatePJ.util

fun getDbHost(): String {
    return System.getenv("DB_HOST")
}

fun getJdbcUrl(): String {
    val dbName = System.getenv("DB_NAME")

    return "jdbc:mysql://${getDbHost()}/$dbName"
}

fun getDbUser(): String {
    return System.getenv("DB_USER")
}

fun getDbPassword(): String {
    return System.getenv("DB_PASSWORD")
}

fun getMongoDBConnectionString(): String {
    return System.getenv("MONGO_DB_CONNECTION_STRING")
}
