import java.io.{File, PrintWriter}

import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.mllib.feature.{HashingTF, IDF}

object SentimentAnalyzer {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    //val sqlContext - new SQLContext(sc)


    val sc = new SparkContext(sparkConf)

    //import sqlContext.implicits._

    val textRDD = sc.textFile("data/RedditNews.csv")

    val test = textRDD.map(line => {
      var arr = line.split(",")
      arr.foreach(print)
      println(" ")
      arr
    }).map()

    test.foreach(println);

  }


}
