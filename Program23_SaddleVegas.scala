//> using scala 2.13
//> using dep io.github.pityka::saddle-core:latest.release
//> using dep io.github.pityka::saddle-stats:latest.release
//> using dep org.vegas-viz::vegas:latest.release

object Program23_SaddleVegas {
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

    // Step 2: Filter the rows that belong to apples
    val isApple: Vec[Boolean] = fruit.map(f => f == "Apple")
    val apples: Frame[Int, String, Double] = data.rwhere(isApple)
    println("Apple records:")
    println(apples)

    // Step 3: Calculate the average price of apples
    val applePrices: Series[Int, Double] = apples.firstCol("Price")
    val avgApplePrice: Double = applePrices.toVec.mean
    println(f"Average price of apples = Rs. $avgApplePrice%.2f per kg")
    println("Column means of the apple data:")
    println(apples.mean)

    // Step 4: Visualize the apple price trend over time using Vegas
    import vegas._
    import java.nio.file.{Files, Paths}

    val appleRows = (0 until apples.numRows).map { i =>
      Map(
        "Date"  -> ("2026-0" + apples.firstCol("Month").at(i).get.toInt
                    + "-01"),
        "Price" -> apples.firstCol("Price").at(i).get
      )
    }

    val plot = Vegas("Apple Price Trend (Jan - Jun 2026)").
      withData(appleRows).
      encodeX("Date", Temp, title = "Month").
      encodeY("Price", Quant, title = "Price (Rs/kg)").
      mark(Line)

    Files.write(Paths.get("apple_price_trend.html"),
      plot.html.pageHTML().getBytes("UTF-8"))
    println("Vegas chart saved as apple_price_trend.html")
  }
}
