import java.io.{File, PrintWriter}

import com.opencsv._
import java.io.FileReader
import java.util
import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import edu.stanford.nlp.trees.Tree
import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.mllib.feature.{HashingTF, IDF}

import collection.JavaConverters._

object SentimentAnalyzer {

  val props = new Properties
  props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
  val pipeline = new StanfordCoreNLP(props)

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    //val sqlContext - new SQLContext(sc)
    //import sqlContext.implicits._

    val sc = new SparkContext(sparkConf)



    //val keyVal = readerCSV.readDataLineByLine("data/RedditNews.csv")

    //val textRDD = sc.textFile("data/RedditNews.csv")

    //val test = textRDD.map(line => {

    //})

    /*
        val test = textRDD.map(line => {
          var arr = line.split(",")
          arr.foreach(println)
         // println(" ")
          arr
        })

        test.foreach(println);
        */

    //val filereader = new FileReader("data/RedditNews.csv")

    // val csvReader = new CSVReader(filereader);

    val lineList = readerCSV.getListFromDataByline("data/moderate.csv")

    val rdd = sc.parallelize(lineList.toArray.map(x => x.toString())).map(line => {
      val arr = line.split("\\^")
      var b = ""
      if(arr(1).charAt(0) == "b"){
        b = arr(1).replace("b","")
      }else{
        b = arr(1)
      }
     var temp = b.replaceAll("\n", "")
      //(arr(0),temp,1)
      (arr(0),temp,getSentiment(temp))
    })

    val dayVal = rdd.map(x=>(x._1,x._3)).reduceByKey(_+_)

    val dayOut = dayVal.map(x=>{
      val ret = x._1+"\t"+x._2
      ret
    })

    dayOut.coalesce(1).saveAsTextFile("output/dayTest")
    //dayVal.coalesce(1).saveAsTextFile("output/day")

    //dayVal.foreach(println)

//    rdd.take(5).foreach(x =>{
//      println(x._1+"\t"+x._2+"\t"+x._3)
//    })

    val output = rdd.map(x =>{
      val ret  = x._1+"\t"+x._2+"\t"+x._3
      ret
    })

    output.coalesce(1).saveAsTextFile("output/outTest")

    //rdd.coalesce(1).saveAsTextFile("output/sentimentNews")
  }


  def getSentiment(line: String): Int = {
    var runningSentiment = 0
    if (line != null && line.length > 0) {

      val annotation = pipeline.process(line)
      import scala.collection.JavaConversions._
      for (sentence <- annotation.get(classOf[CoreAnnotations.SentencesAnnotation])) {
        val tree = sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])
        val sentiment = RNNCoreAnnotations.getPredictedClass(tree)
        runningSentiment+=sentiment
      }
    }
    return runningSentiment
  }

}
