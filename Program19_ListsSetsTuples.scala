//> using scala 2.13

object Program19_ListsSetsTuples {
  def main(args: Array[String]): Unit = {
    // Step 1: Create a list of the first five prime numbers
    val primes: List[Int] = List(2, 3, 5, 7, 11)
    println("First five primes = " + primes)

    // Step 2: Function that returns a new list with each element squared
    def squareList(nums: List[Int]): List[Int] = {
      nums.map(n => n * n)
    }

    val squares = squareList(primes)
    println("Squared list = " + squares)

    // Step 3: Create a set of unique characters from "hello world"
    val charSet: Set[Char] = "hello world".toSet
    println("Unique characters = " + charSet)
    println("Number of unique characters = " + charSet.size)

    // Step 4: Function that returns the intersection of two sets
    def intersection(a: Set[Char], b: Set[Char]): Set[Char] = {
      a.intersect(b)
    }

    val otherSet: Set[Char] = "scala world".toSet
    println("Second set = " + otherSet)
    println("Intersection = " + intersection(charSet, otherSet))

    // Step 5: Create a tuple (Int, String, Boolean), access each item
    val record: (Int, String, Boolean) = (101, "Scala", true)

    println("Tuple = " + record)
    println("First element (Int)      = " + record._1)
    println("Second element (String)  = " + record._2)
    println("Third element (Boolean)  = " + record._3)
  }
}
