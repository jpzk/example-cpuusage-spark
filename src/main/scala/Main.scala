import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.apache.spark.sql.functions._

case class Measurement(time: Int, clientId: String, payloadId: String, usage: Int)

/**
  * This spark driver aggregates the raw CPU usage information to hourly information
  */
object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("cpuusage-aggregation").setMaster("localhost")
    val sc = new SparkContext(conf)

    // sc is an existing SparkContext.
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    // this is used to implicitly convert an RDD to a DataFrame.
    import sqlContext.implicits._

    val fmt = DateTimeFormat.forPattern("yyyyMMdd-hh")
    val time = DateTime.now().toString(fmt)

    val dir = Config.CSVDirectory
    val outdir = Config.OutDirectory
    val input = s"file://$dir/cpuusage-$time.csv"
    val output = s"file://$outdir/agg/cpuusage-$time.json"

    val csv = sc.textFile(input)
    val splitted = csv.map(_.split(","))
    val measurements = splitted.map(p => Measurement(p(0).toInt, p(1), p(2), p(3).toInt)).toDF()

    val results = measurements.registerTempTable("measurements")
    val json = measurements
      .groupBy("clientId")
      .agg(min("time"), max("time"), count("usage"), min("usage"), max("usage"), avg("usage"))
      .write.json(output)
  }

}
