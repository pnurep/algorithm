

fun <T> List<T>.safeGet(index: Int): T? {
	if (index >= this.size || index < 0)
		return null

	return this[index]
}