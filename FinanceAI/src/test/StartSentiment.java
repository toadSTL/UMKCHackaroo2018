import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StartSentiment {

    public static DoccatModel model = null;
    public static String[] analyzedTexts = {"I hate Mondays!"/*, "Electricity outage, this is a nightmare"/*, "I love it"*/};

    public static void main(String[] args) throws IOException {


        //     begin of sentiment analysis
        trainModel();
        for(int i=0; i<analyzedTexts.length;i++){
            classifyNewText(analyzedTexts[i]);
        }

    }

    private static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public static void trainModel() {
        MarkableFileInputStreamFactory  dataIn = null;
        try {
            dataIn = new MarkableFileInputStreamFactory(
                    new File("bin/text.txt"));

            ObjectStream<String> lineStream = null;
            lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters tp = new TrainingParameters();
            tp.put(TrainingParameters.CUTOFF_PARAM, "2");
            tp.put(TrainingParameters.ITERATIONS_PARAM, "30");

            DoccatFactory df = new DoccatFactory();
            model = DocumentCategorizerME.train("en", sampleStream, tp, df);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dataIn != null) {
                try {
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

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
