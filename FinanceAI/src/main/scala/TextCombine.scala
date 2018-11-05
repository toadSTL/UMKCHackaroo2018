import java.io.{BufferedWriter, FileWriter}
import java.util.Properties

import edu.stanford.nlp.pipeline.StanfordCoreNLP
import org.apache.spark.{SparkConf, SparkContext}

object TextCombine {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val dowChange = sc.textFile("data/combine/DowChange.txt")
    val sentTotal = sc.textFile("data/combine/SentimentTotals.txt")

    val sentMod = sentTotal.map(line => {
      val splitLine = line.split("\t")
      val dateSplit = splitLine(0).split("/")

      if (dateSplit(0).length == 1) {
        dateSplit(0) = "0" + dateSplit(0)
      }
      if (dateSplit(1).length == 1) {
        dateSplit(1) = "0" + dateSplit(1)
      }

      val dateCombined = dateSplit(2) + "-" + dateSplit(0) + "-" + dateSplit(1)

      (dateCombined, splitLine(1))
    })

    val sentModMap = sc.broadcast(sentMod.collectAsMap()).value

    val combined = dowChange.map(line => {
      val splitLine = line.split("\t")
      val sentiment = sentModMap(splitLine(0))
      val combined = splitLine(0) + "\t" + sentiment + "\t" + splitLine(1)

      combined
    })

    val combineWriter = new BufferedWriter(new FileWriter("data/combine/combined.txt"))

    combined.collect().foreach(line => {
      combineWriter.append(line).append("\n")
    })

    combineWriter.close()

  }
}
