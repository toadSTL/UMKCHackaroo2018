import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.JavaConversions._



object SentimentAnalyzer {
  val props = new Properties
  props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
  val pipeline = new StanfordCoreNLP(props)

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils")


    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val inputf = sc.wholeTextFiles("data/redditTexts", 50)
    val input = inputf.map(abs => {
      abs._2
    }).cache()

    val sentimentAnalysis = input.flatMap(lines => {lines.split("[\r\n]+")}).map(line => {
      val splitLine = line.split("\t")
      val sentiment = getSentiment(splitLine(1))
      (splitLine(0), sentiment)
    }).reduceByKey(_+_).sortByKey(true)

    sentimentAnalysis.saveAsTextFile("output/sentimentAnalysis")
  }

  def getSentiment(line: String): Int = {
    var mainSentiment = 0
    var runningSentiment = 0
    if (line != null && line.length > 0) {
      val annotation = pipeline.process(line)
      for (sentence <- annotation.get(classOf[CoreAnnotations.SentencesAnnotation])) {
        val tree = sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])
        val sentiment = RNNCoreAnnotations.getPredictedClass(tree)
        runningSentiment+=sentiment
      }
    }
    return runningSentiment
  }


}
