package de.vexxes.penaltycatalog.util

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

fun SortOrder.toValue(): Int {
    return if(this == SortOrder.ASCENDING) 1 else -1
}