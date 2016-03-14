import java.io._
import com.typesafe.config.ConfigFactory

object Config {
  val config = ConfigFactory.load()
  def CSVDirectory = config.getString("csv-directory")
  def OutDirectory = config.getString("output-directory")
}
