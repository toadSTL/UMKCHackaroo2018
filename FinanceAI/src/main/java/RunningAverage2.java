import java.io.*;
import java.util.ArrayList;

/**
 * Created by DJ Yuhn on 11/4/2018
 */
public class RunningAverage2 {
    public static void main(String [ ] args)
    {
        // The name of the file to open.
        String fileName = "data/combine/combined.txt";

        // This will reference one line at a time
        String line = null;

        ArrayList<Integer> day1Arr = new ArrayList<>();
        ArrayList<Integer> day2Arr = new ArrayList<>();
        ArrayList<Integer> day3Arr = new ArrayList<>();

        int day1 = -1;
        Integer day1Sent = 0;
        int day2 = -2;
        Integer day2Sent = 0;
        int day3 = -3;
        Integer day3Sent = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split("\t");
                Integer sentiment = Integer.valueOf(splitLine[1]);

                if (day1 < 3 && day1 >= 0) {
                    day1Sent += sentiment;
                } else if (day1 == 3) {
                    day1 = 0;
                    day1Arr.add(day1Sent / 3);
                    day1Sent = 0;
                    day1Sent += sentiment;
                }
                if (day2 < 3 && day2 >= 0) {
                    day2Sent += sentiment;
                } else if (day2 == 3) {
                    day2 = 0;
                    day2Arr.add(day2Sent / 3);
                    day2Sent = 0;
                    day2Sent += sentiment;
                }
                if (day3 < 3 && day3 >= 0) {
                    day3Sent += sentiment;
                } else if (day3 == 3) {
                    day3 = 0;
                    day3Arr.add(day3Sent / 3);
                    day3Sent = 0;
                    day3Sent += sentiment;
                }
                day1++;
                day2++;
                day3++;

            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        String fileLoc = "data/average/3dayAverageSent.txt";

        try {
            fileWrite(day1Arr, day2Arr, day3Arr, fileLoc );


        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }

    }

    static void fileWrite(ArrayList<Integer> aList1, ArrayList<Integer> aList2, ArrayList<Integer> aList3, String fileName) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));

        int largestSize = aList1.size();

        if(largestSize < aList2.size()) {
            largestSize = aList2.size();
            if(largestSize < aList3.size()) {
                largestSize = aList3.size();
            }
        }
        else if (largestSize < aList3.size()) {
            largestSize = aList3.size();
        }

        for (int i = 0; i < largestSize; i++) {
            if (aList1.size() > i && aList2.size() > i && aList3.size() > i) {
                bufferedWriter.append(aList1.get(i).toString())
                        .append("\n")
                        .append(aList2.get(i).toString())
                        .append("\n")
                        .append(aList3.get(i).toString())
                        .append("\n");
            }
        }

        bufferedWriter.close();
    }

}
