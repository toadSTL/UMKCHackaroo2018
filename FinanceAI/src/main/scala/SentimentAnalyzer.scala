import java.io.{File, PrintWriter}
import com.opencsv._
import java.io.FileReader
import java.util

import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.mllib.feature.{HashingTF, IDF}

import collection.JavaConverters._

object SentimentAnalyzer {

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

    val lineList = readerCSV.getArrFromDataByline("data/mini.csv")

    val rdd = sc.parallelize(lineList.toArray.map(x => x.toString())).map(line => {
      val arr = line.split("\\^")
      //if the first char of arr(1) is a 'b' we should remove it
      //also should remove new line chars
      (arr(0),arr(1),SentimentCoreNLP.getSentiment(arr(1)))
    })

    val dayVal = rdd.map(x=>(x._1,x._3)).reduceByKey(_+_)

    dayVal.coalesce(1).saveAsTextFile("data/day")

    dayVal.foreach(println)

    rdd.take(5).foreach(x =>{
      println(x._1+"\t"+x._2+"\t"+x._3)
    })

    rdd.coalesce(1).saveAsTextFile("data/out")
  }
}
