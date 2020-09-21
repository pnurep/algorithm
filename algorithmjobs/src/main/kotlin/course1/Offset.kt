package course1

import safeGet


fun main() {
	val input = listOf(
		listOf(3, 4, 1, 4, 9),
		listOf(2, 9, 4, 5, 8),
		listOf(9, 0, 8, 2, 1),
		listOf(7, 0, 2, 8, 4),
		listOf(2, 7, 2, 1, 4)
	)

	println(solve(input))
}

tailrec fun solve(
	map: List<List<Int>>,
	rowIdx: Int = 0,
	acc: List<List<String>> = listOf()
): List<List<String>> = when {
	rowIdx + 1 > map.size ->
		acc
	else -> {
		val previousRow = map.safeGet(rowIdx - 1)
		val currentRow = map[rowIdx]
		val nextRow = map.safeGet(rowIdx + 1)

		val rowResult = solveHelper(
			previousRow, currentRow, nextRow
		)

		solve(map, rowIdx + 1, acc + listOf(rowResult))
	}
}

private tailrec fun solveHelper(
	previousRow: List<Int>?,
	currentRow: List<Int>,
	nextRow: List<Int>?,
	currentIdx: Int = 0,
	acc: List<String> = listOf()
): List<String> = when {
	currentIdx >= currentRow.size ->
		acc
	else -> {
		val tempResult = mutableListOf<Boolean>()

		val top = previousRow?.safeGet(currentIdx)
		val bottom = nextRow?.safeGet(currentIdx)

		val current = currentRow[currentIdx]

		val left = currentRow.safeGet(currentIdx - 1)
		val right = currentRow.safeGet(currentIdx + 1)

		// 실제로 current 가 상하좌우보다 작거나 null 과 비교했을시에는 true 리턴
		// 아니면 false

		if (top == null || top > current) tempResult.add(true)
		else tempResult.add(false)

		if (bottom == null || bottom > current) tempResult.add(true)
		else tempResult.add(false)

		if (left == null || left > current) tempResult.add(true)
		else tempResult.add(false)

		if (right == null || right > current) tempResult.add(true)
		else tempResult.add(false)

		val result = tempResult.find { !it }

		solveHelper(
			previousRow,
			currentRow,
			nextRow,
			currentIdx + 1,
			if (result == null) acc + listOf("*")
			else acc + listOf(current.toString())
		)
	}
}
