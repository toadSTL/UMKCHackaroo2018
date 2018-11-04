import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class SentimentCoreNLP {

    public static int getSentiment(String line){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        int runningSentiment = 0;
        Annotation annotation = pipeline.process(line);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            runningSentiment += RNNCoreAnnotations.getPredictedClass(tree);
        }
        return runningSentiment;
    }
/*
    public static void classifyNewText(String text){
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

        double[] outcomes = myCategorizer.categorize(new String[]{text});
        String category = myCategorizer.getBestCategory(outcomes);

        if (category.equalsIgnoreCase("1")){
            System.out.print("The text is positive");
        } else {
            System.out.print("The text is negative");
        }

    }
    */

}

