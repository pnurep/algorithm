package course1


/**
 * 10부제
 *
 */

fun main() {

	require(
		solve(1, listOf(1, 2, 3, 4, 5)) == 1
	)

	require(
		solve(3, listOf(1, 2, 3, 5, 3)) == 2
	)

	require(
		solve(5, listOf(1, 3, 0, 7, 4)) == 0
	)
}

private tailrec fun solve(day: Int, cars: List<Int>, acc: Int = 0): Int = when {
	cars.isEmpty() ->
		acc
	else -> {
		val head = cars.first()

		solve(
			day,
			cars.drop(1),
			if (day == head) acc + 1
			else acc
		)
	}
}
