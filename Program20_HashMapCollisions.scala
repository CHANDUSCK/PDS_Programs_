//> using scala 2.13

object Program20_HashMapCollisions {
  def main(args: Array[String]): Unit = {
    // Step 1: Create a HashMap of student names and grades
    import scala.collection.mutable

    val grades = mutable.HashMap[String, Int](
      "Arun" -> 85,
      "Bhavya" -> 92,
      "Charan" -> 78,
      "Divya" -> 88
    )
    println("Initial HashMap = " + grades)

    // Step 2: Insert more entries, including a duplicate key
    grades.put("Esha", 81)
    grades.put("Arun", 90)   // duplicate key: old value 85 is replaced
    println("After insertion    = " + grades)
    println("Arun's grade       = " + grades("Arun"))

    // Step 3: Merge two maps, keeping the higher grade on a conflict
    def mergeMaps(m1: Map[String, Int],
                  m2: Map[String, Int]): Map[String, Int] = {
      m2.foldLeft(m1) { case (merged, (name, grade)) =>
        merged.get(name) match {
          case Some(old) => merged.updated(name, math.max(old, grade))
          case None      => merged + (name -> grade)
        }
      }
    }

    val sectionA = Map("Arun" -> 90, "Bhavya" -> 92, "Charan" -> 78)
    val sectionB = Map("Arun" -> 84, "Charan" -> 83, "Farhan" -> 76)
    println("Section A = " + sectionA)
    println("Section B = " + sectionB)
    println("Merged    = " + mergeMaps(sectionA, sectionB))

    // Step 4: Handle collisions by chaining (a list in each bucket)
    val tableSize = 5
    val buckets = Array.fill[List[(String, Int)]](tableSize)(Nil)

    def bucketIndex(key: String): Int = math.abs(key.hashCode) % tableSize

    def insertChained(key: String, value: Int): Unit = {
      val idx = bucketIndex(key)
      val chain = buckets(idx).filterNot(_._1 == key)  // replace same key
      buckets(idx) = (key, value) :: chain              // else chain it
    }

    val entries = List("Arun" -> 90, "Bhavya" -> 92, "Charan" -> 78,
                       "Divya" -> 88, "Esha" -> 81, "Farhan" -> 76,
                       "Gita" -> 95)
    entries.foreach { case (k, v) => insertChained(k, v) }

    for (i <- buckets.indices) {
      println("Bucket " + i + " -> " + buckets(i))
    }

    // Step 5: Store several values for one key using lists
    val subjectMarks = mutable.HashMap[String, List[Int]]()

    def addMark(student: String, mark: Int): Unit = {
      subjectMarks(student) = mark :: subjectMarks.getOrElse(student, Nil)
    }

    addMark("Arun", 85); addMark("Arun", 90)
    addMark("Bhavya", 92); addMark("Arun", 78)

    subjectMarks.toSeq.sortBy(_._1).foreach { case (k, v) =>
      println(k + " -> " + v)
    }
  }
}
