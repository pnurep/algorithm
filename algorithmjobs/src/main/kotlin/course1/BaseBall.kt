package course1

import splitIgnoreEmpty

/**
 * 야구게임
 * [1, 2, 3, 4, 5, 6, 7, 8, 9] 9개의 숫자 중 3개를 선택한 순열의 목록과 [123, 356, 327, 489]를 비교한다.
 * 각각의 비교에서 스트라이크와 볼이 같다면 해당하는 순열이 가능한 정답이 된다.
 *
 * ex) 324 와 [123, 356, 327, 489] 를 비교
 */
fun main() {

	println(solveHelper(
		listOf(
			Input(123, 1, 1),
			Input(356, 1, 0),
			Input(327, 2, 0),
			Input(489, 0, 1)
		)
	))
}

private fun solveHelper(
	inputs: List<Input>
) = solve(inputs).size

private fun solve(
	answers: List<Input>
): List<Int> {
	val acc = mutableListOf<Int>()
	val firstMap = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
	val secondMap = firstMap.drop(1)
	val thirdMap = secondMap.drop(1)
	var temp = listOf<Int>()

	firstMap.forEach { a ->
		if (temp.isNotEmpty()) temp = listOf()

		temp = listOf(a)

		secondMap.forEach { b ->
			if (temp.size >= 2) temp = temp.dropLast(2)

			temp = temp + listOf(b)

			thirdMap.forEach { c ->
				if (temp.size >= 3) temp = temp.dropLast(1)

				temp = temp + listOf(c) // 세자리 수 완성됨

				// 이제 그 세자리 수를 input 과 비교
				if (assert(temp, answers))
					acc.add(temp.joinToString("").toInt())
			}
		}
	}

	return acc
}

private fun assert(answer: List<Int>, givenAnswer: List<Input>): Boolean {
	var strikeAssert = true
	var ballAssert = true
	givenAnswer.forEach { input ->
		if (input.strike != getStrike(answer, input.answer.toString().splitIgnoreEmpty("").map { it.toInt() }))
			strikeAssert = false

		if (input.ball != getBall(answer, input.answer.toString().splitIgnoreEmpty("").map { it.toInt() }))
			ballAssert = false
	}

	return strikeAssert && ballAssert
}

private fun getStrike(answer: List<Int>, givenAnswer: List<Int>): Int {
	var count = 0
	answer.zip(givenAnswer) { p1, p2 ->
		if (p1 == p2) count += 1
	}

	return count
}

private fun getBall(answer: List<Int>, givenAnswer: List<Int>, pointer: Int = 0, count: Int = 0): Int = when {
	pointer >= 3 ->
		count
	else -> {
		var tempCount = 0
		val head = answer[pointer]

		givenAnswer.forEachIndexed { idx, elem ->
			if (elem == head && idx != pointer) tempCount += 1
		}

		getBall(answer, givenAnswer, pointer + 1, count + tempCount)
	}
}

private data class Input(
	val answer: Int,
	val strike: Int,
	val ball: Int
)
