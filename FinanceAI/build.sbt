name := "FinanceAI"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/edu.stanford.nlp/stanford-corenlp
// https://mvnrepository.com/artifact/com.opencsv/opencsv
libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" classifier "models",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2",
  "edu.stanford.nlp" % "stanford-parser" % "3.9.2",
  "org.apache.spark" %% "spark-core" % "2.3.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.3.1",
  "org.apache.spark" %% "spark-mllib" % "2.3.1",
  "com.opencsv" % "opencsv" % "4.3.2",
  "org.apache.opennlp" % "opennlp-tools" % "1.9.0"
)
