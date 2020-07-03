package com.templatePJ.domain

data class User(
    var id: String,
    var name: String,
    var items: List<Item>
) {
    // domain logic here
}