package tp.api.model

data class ItemList<E>(
    val items: List<E>
) {
    companion object {
        fun <T> emptyItems() = ItemList<T>(listOf())
    }
}

