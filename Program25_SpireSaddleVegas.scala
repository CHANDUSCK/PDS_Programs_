//> using scala 2.13
//> using dep org.typelevel::spire:0.18.0
//> using dep io.github.pityka::saddle-core:latest.release
//> using dep io.github.pityka::saddle-stats:latest.release
//> using dep org.vegas-viz::vegas:latest.release

object Program25_SpireSaddleVegas {
  def main(args: Array[String]): Unit = {
    // Step 1: Fruit price (Rs/kg) and quantity (kg), Jan-Jun 2026
    import org.saddle._

    val monthNo: Vec[Double] = Vec(1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6,
                                   1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6)
    val fruit: Vec[String] = Vec(
      "Apple", "Apple", "Apple", "Apple", "Apple", "Apple",
      "Banana", "Banana", "Banana", "Banana", "Banana", "Banana",
      "Orange", "Orange", "Orange", "Orange", "Orange", "Orange",
      "Mango", "Mango", "Mango", "Mango", "Mango", "Mango")
    val price: Vec[Double] = Vec(
      120.50, 125.25, 118.75, 130.00, 128.40, 135.60,
      40.00, 42.50, 38.25, 45.00, 44.75, 41.50,
      60.00, 58.50, 62.25, 65.00, 63.75, 66.50,
      90.00, 95.50, 110.25, 130.00, 125.50, 100.75)
    val quantity: Vec[Double] = Vec(
      200, 180, 220, 190, 210, 230,
      300, 320, 310, 290, 330, 340,
      150, 160, 140, 170, 155, 165,
      80, 90, 120, 140, 130, 100)

    // Numeric columns are stored in a Saddle Frame (row index 0 to 23)
    val data: Frame[Int, String, Double] =
      Frame("Month" -> monthNo, "Price" -> price, "Quantity" -> quantity)
    println("Dataset loaded: " + data.numRows + " rows x " +
      data.numCols + " columns")
    println(data)

    // Step 2: Use Saddle to split the price column fruit by fruit
    val fruitNames = List("Apple", "Banana", "Orange", "Mango")

    val pricesByFruit: Map[String, Vec[Double]] = fruitNames.map { f =>
      val rows = data.rwhere(fruit.map(name => name == f))
      f -> rows.firstCol("Price").values
    }.toMap

    pricesByFruit.foreach { case (f, v) =>
      println(f + " -> " + v.length + " prices: " +
        v.toSeq.mkString(", "))
    }

    // Step 3: Use Spire (exact Rational numbers) for mean and variance
    import _root_.spire.math.Rational

    def toRational(x: Double): Rational = Rational(BigDecimal(x.toString))

    def exactMean(xs: Seq[Rational]): Rational =
      xs.reduce(_ + _) / xs.size

    def exactVariance(xs: Seq[Rational]): Rational = {
      val m = exactMean(xs)
      val ss = xs.map(x => (x - m) * (x - m)).reduce(_ + _)
      ss / (xs.size - 1)                          // sample variance
    }

    for (f <- fruitNames) {
      val xs = pricesByFruit(f).toSeq.map(toRational)
      val m = exactMean(xs)
      val v = exactVariance(xs)
      println(f + ":")
      println("  mean     = " + m + "  (" + m.toDouble + ")")
      println("  variance = " + v + "  (" + v.toDouble + ")")
    }

    // Step 4: Cross-check with Saddle's own double-precision statistics
    import org.saddle.stats._

    println(f"${"Fruit"}%-8s${"Mean"}%12s${"Variance"}%14s")
    for (f <- fruitNames) {
      val v = pricesByFruit(f)
      println(f"$f%-8s${v.mean}%12.4f${v.variance}%14.4f")
    }

    // Step 5: Visualize the price trend of every fruit using Vegas
    import vegas._
    import java.nio.file.{Files, Paths}

    val allRows = (0 until data.numRows).map { i =>
      Map(
        "Date"  -> ("2026-0" + data.firstCol("Month").at(i).get.toInt
                    + "-01"),
        "Fruit" -> fruit.at(i),
        "Price" -> data.firstCol("Price").at(i).get
      )
    }

    val plot = Vegas("Fruit Price Trends (Jan - Jun 2026)").
      withData(allRows).
      encodeX("Date", Temp, title = "Month").
      encodeY("Price", Quant, title = "Price (Rs/kg)").
      encodeColor("Fruit", Nom).
      mark(Line)

    Files.write(Paths.get("fruit_price_trends.html"),
      plot.html.pageHTML().getBytes("UTF-8"))
    println("Vegas chart saved as fruit_price_trends.html")
  }
}
