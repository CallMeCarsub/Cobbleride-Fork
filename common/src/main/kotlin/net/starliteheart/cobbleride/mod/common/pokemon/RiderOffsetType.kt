package net.starliteheart.cobbleride.mod.common.pokemon

enum class RiderOffsetType {
    DEFAULT,
    WALKING,
    SWIMMING,
    FLOATING,
    DIVING,
    SUSPENDED,
    FLYING,
    HOVERING
}

inline fun <reified T : Enum<T>> printAllValues() {
    println(enumValues<T>().joinToString { it.name })
}