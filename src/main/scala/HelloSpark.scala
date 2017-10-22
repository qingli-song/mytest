import org.apache.spark.SparkConf
import org.apache.spark.SparkContext



object HelloSpark {
  def main(args:Array[String]) {
    val conf = new SparkConf().setAppName("HelloSpark-app")
    val sc = new SparkContext(conf)
    println("Hello World!")

    sc.stop()
  }


}


