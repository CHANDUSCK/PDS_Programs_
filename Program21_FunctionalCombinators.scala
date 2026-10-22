//> using scala 2.13

object Program21_FunctionalCombinators {
  def main(args: Array[String]): Unit = {
    // Step 1: Create a list of integers from 1 to 10
    val numbers: List[Int] = (1 to 10).toList
    println("Original list = " + numbers)

    // Step 2: filter - keep only the even numbers
    val evens = numbers.filter(n => n % 2 == 0)
    println("Even numbers = " + evens)

    // Step 3: map - multiply every element by 3
    val tripled = numbers.map(n => n * 3)
    println("Multiplied by 3 = " + tripled)

    // Step 4: flatMap - pair each integer with its square
    val pairs: List[(Int, Int)] = numbers.flatMap(n => List((n, n * n)))
    println("(n, n^2) pairs = " + pairs)

    // Step 5: foldLeft - product of all the elements
    val product = numbers.foldLeft(1)((acc, n) => acc * n)
    println("Product of all elements = " + product)
  }
}
