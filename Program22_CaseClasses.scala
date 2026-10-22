//> using scala 2.13

object Program22_CaseClasses {
  def main(args: Array[String]): Unit = {
    // Step 1: Case class and a list of five Person objects
    case class Person(name: String, age: Int, city: String)

    val people: List[Person] = List(
      Person("Arun", 28, "Chennai"),
      Person("Bhavya", 35, "Bengaluru"),
      Person("Charan", 42, "Chennai"),
      Person("Divya", 31, "Hyderabad"),
      Person("Esha", 24, "Bengaluru")
    )
    people.foreach(println)

    // Step 2: Filter the people who are older than 30
    def olderThan(list: List[Person], limit: Int): List[Person] = {
      list.filter(p => p.age > limit)
    }

    println("People older than 30:")
    olderThan(people, 30).foreach(println)

    // Step 3: Group the people by their city
    def groupByCity(list: List[Person]): Map[String, List[Person]] = {
      list.groupBy(p => p.city)
    }

    groupByCity(people).foreach { case (city, group) =>
      println(city + " -> " + group.map(_.name).mkString(", "))
    }

    // Step 4: Transform each Person into a descriptive string
    def describe(list: List[Person]): List[String] = {
      list.map(p => p.name + " is " + p.age +
        " years old and lives in " + p.city)
    }

    describe(people).foreach(println)

    // Step 5: Sort the people by age in ascending order
    def sortByAge(list: List[Person]): List[Person] = {
      list.sortBy(p => p.age)
    }

    sortByAge(people).foreach(println)
  }
}
