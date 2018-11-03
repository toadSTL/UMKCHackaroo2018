import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.*;

public class readerCSV {
    //session.read.csv
    public static Map<String,String> readDataLineByLine(String file) {

        try {
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // filereader as parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            Map<String,String> lines =  new HashMap<String, String>();

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                lines.put(nextRecord[0], nextRecord[1]);

                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();

            }
            return lines;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> getListFromDataByline(String file) {

        try {
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // filereader as parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            String temp = "";
            List<String> lines = new ArrayList<String>();
            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    //System.out.print(cell + "\t");
                    temp += cell + "^";
                }
                //System.out.println();
                lines.add(temp);
                temp = "";
            }
            return lines;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
