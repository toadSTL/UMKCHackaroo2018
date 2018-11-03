import java.io.{BufferedWriter, FileWriter}

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * Created by DJ Yuhn on 11/3/2018
  */
object CSVToText {
  def main(args: Array[String]) {

    // For Windows Users
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    // Configuration
    val spark = SparkSession.builder()
      .appName("CSVToText")
      .master("local")
      .getOrCreate()

    val csvRDD = spark.read
      .format("csv")
      .option("header", "true") //reading the headers
      .option("mode", "DROPMALFORMED")
      .load("data/RedditNews.csv").rdd

    val stopWordsFile = spark.sparkContext.textFile("data/stopwords.txt").collect()
    val stopWordsBroadcast = spark.sparkContext.broadcast(stopWordsFile)

    val removeStopWords = csvRDD.map(row => {
      val lineArr = row.mkString("\t").split("\t")
      var output = ""
      if (lineArr.length == 2) {
        val headlineNoStops = lineArr(1).split(" ").filter(!stopWordsBroadcast.value.contains(_)).mkString(" ")
        output = lineArr(0) + "\t" + headlineNoStops
      }
      output
    })

    removeStopWords.saveAsTextFile("output/redditNews")

    val col = removeStopWords.collect()

    var count = 0
    val outputArray: ArrayBuffer[StringBuilder] = ArrayBuffer()
    var stringBuilder = new StringBuilder()
    while( count < col.length ){
      if (count % 1000 == 1 && count != 1) {
          outputArray.append(stringBuilder)
          stringBuilder = new StringBuilder()
      }
      stringBuilder.append(col(count) + "\n")
      count = count + 1
    }

    var fileCount = 1
    outputArray.foreach(builder => {
      val redditWriter = new BufferedWriter(new FileWriter("data/redditTexts/redditTexts_" + fileCount + ".txt"))
      redditWriter.append(builder.toString())
      redditWriter.close()
      fileCount = fileCount + 1
    })


  }
}
