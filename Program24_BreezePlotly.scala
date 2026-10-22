//> using scala 2.13
//> using dep org.scalanlp::breeze:2.1.0
//> using dep org.plotly-scala::plotly-render:latest.release

object Program24_BreezePlotly {
  def main(args: Array[String]): Unit = {
    // Step 1: Price and quantity matrices (rows = fruits, cols = months)
    import breeze.linalg._

    val fruits = Array("Apple", "Banana", "Orange", "Mango")

    val price = DenseMatrix(
      (120.50, 125.25, 118.75, 130.00, 128.40, 135.60),
      (40.00, 42.50, 38.25, 45.00, 44.75, 41.50),
      (60.00, 58.50, 62.25, 65.00, 63.75, 66.50),
      (90.00, 95.50, 110.25, 130.00, 125.50, 100.75))

    val quantity = DenseMatrix(
      (200.0, 180.0, 220.0, 190.0, 210.0, 230.0),
      (300.0, 320.0, 310.0, 290.0, 330.0, 340.0),
      (150.0, 160.0, 140.0, 170.0, 155.0, 165.0),
      (80.0, 90.0, 120.0, 140.0, 130.0, 100.0))

    println("Price matrix (" + price.rows + " x " + price.cols + "):")
    println(price)
    println("Quantity matrix (" + quantity.rows + " x " +
      quantity.cols + "):")
    println(quantity)

    // Step 2: Revenue = price * quantity (element-wise multiplication)
    val revenue: DenseMatrix[Double] = price *:* quantity
    println("Revenue matrix:")
    println(revenue)

    // Step 3: Total revenue for each fruit type (sum of every row)
    val totalRevenue: DenseVector[Double] = sum(revenue(*, ::))

    for (i <- fruits.indices) {
      println(f"${fruits(i)}%-7s total revenue = Rs. " +
        f"${totalRevenue(i)}%,.2f")
    }
    println("Overall revenue = Rs. " + f"${sum(totalRevenue)}%,.2f")

    // Step 4: Bar chart of the total revenue using Plotly.scala
    import plotly._, plotly.element._, plotly.layout._, plotly.Plotly._

    val trace = Bar(
      x = fruits.toSeq,
      y = totalRevenue.toArray.toSeq
    )

    Seq(trace).plot(
      path = "total_revenue.html",
      title = "Total Revenue per Fruit (Jan - Jun 2026)",
      openInBrowser = false)
    println("Plotly chart saved as total_revenue.html")
  }
}
