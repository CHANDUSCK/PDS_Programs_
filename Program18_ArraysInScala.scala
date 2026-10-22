//> using scala 2.13

object Program18_ArraysInScala {
  def main(args: Array[String]): Unit = {
    // Step 1: Create an array of integers
    val numbers: Array[Int] = Array(2, 5, 9, 14, 20)
    println("Integer array = " + numbers.mkString("Array(", ", ", ")"))

    // Step 2: Function that returns the sum of an integer array
    def sumArray(arr: Array[Int]): Int = {
      var total = 0
      for (x <- arr) {
        total += x
      }
      total
    }

    println("Sum of array elements = " + sumArray(numbers))

    // Step 3: Create an array of strings with five different fruits
    val fruits: Array[String] =
      Array("Apple", "Banana", "Mango", "Orange", "Grapes")
    println("Fruit array = " + fruits.mkString("Array(", ", ", ")"))

    // Step 4: Function that concatenates all elements separated by commas
    def concatFruits(arr: Array[String]): String = {
      arr.mkString(", ")
    }

    println("Concatenated fruits = " + concatFruits(fruits))

    // Step 5: Iterate over the integer array and print each element
    println("Elements of the integer array:")
    for (n <- numbers) {
      println(n)
    }
  }
}
