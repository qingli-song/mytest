/**
  * Created by itversity on 20/03/17.
  */

/* build.sbt
name := "retail"
version := "1.0"
scalaVersion := "2.10.6"
libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.2"
//libraryDependencies += "org.apache.spark" % "spark-streaming-flume_2.10" % "1.6.2"
//libraryDependencies += "org.apache.spark" % "spark-streaming-flume-sink_2.10" % "1.6.2"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.2"
*/

/* spark submit command
spark-submit --class StreamingDepartmentAnalysis \
  --master yarn \
  --conf spark.ui.port=22231 \
  --jars "/usr/hdp/2.5.0.0-1245/spark/lib/spark-streaming_2.10-1.6.2.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/spark-streaming-kafka_2.10-1.6.2.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/kafka_2.10-0.8.2.1.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/metrics-core-2.2.0.jar" \
  retail_2.10-1.0.jar /user/dgadiraju/streaming/streamingdepartmentanalysis
*/

/* flume and kafka integration configuration file
# Name the components on this agent
kandf.sources = logsource
kandf.sinks = ksink
kandf.channels = mchannel

# Describe/configure the source
kandf.sources.logsource.type = exec
kandf.sources.logsource.command = tail -F /opt/gen_logs/logs/access.log

# Describe the sink
kandf.sinks.ksink.type = org.apache.flume.sink.kafka.KafkaSink
kandf.sinks.ksink.brokerList = nn02.itversity.com:6667
kandf.sinks.ksink.topic = kafkadg

# Use a channel which buffers events in memory
kandf.channels.mchannel.type = memory
kandf.channels.mchannel.capacity = 1000
kandf.channels.mchannel.transactionCapacity = 100

# Bind the source and sink to the channel
kandf.sources.logsource.channels = mchannel
kandf.sinks.ksink.channel = mchannel
*/
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka._

object MyStreaming {
  def main(args: Array[String]) {
    /*test2*/
    val sparkConf = new SparkConf().setAppName("MyStreaming").setMaster("local[2]") //.setMaster("yarn-client")
    println("aaaaaaaaaaaaaaaaaaaaaaaa")
    val ssc = new StreamingContext(sparkConf, Seconds(60))
    println("bbbbbbbbbbbbbbbbbbbbbbbb")
    val topicsSet = "MyTopic3".split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "192.168.56.101:9092", "auto.offset.reset" -> "largest")
    val messages: InputDStream[(String, String)] =
      KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
    println("cccccccccccccccccccccccc")

    val lines = messages.map(_._2)
    println("ddddddddddddddddddddddddd")
    lines.print()
    println("eeeeeeeeeeeeeeeeeeeeeeeee")
    /*
    val linesFiltered = lines.filter(rec => rec.contains("A"))
    val countByDepartment = linesFiltered.
      map(rec => (rec.split(" ")(6).split("/")(2), 1)).
      reduceByKey(_ + _)
    //        reduceByKeyAndWindow((a:Int, b:Int) => (a + b), Seconds(300), Seconds(60))
    //    countByDepartment.saveAsTextFiles(args(0))
    // Below function call will save the data into HDFS
    countByDepartment.saveAsTextFiles(args(0))
 */
    ssc.start()
    println("fffffffffffffffffffffffff")
    ssc.awaitTermination()
    println("gggggggggggggggggggggggggggggggg")
  }
}