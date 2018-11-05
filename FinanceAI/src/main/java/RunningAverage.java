import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by DJ Yuhn on 11/4/2018
 */
public class RunningAverage {
    public static void main(String [ ] args)
    {
        // The name of the file to open.
        String fileName = "data/combine/combined.txt";

        // This will reference one line at a time
        String line = null;

        ArrayList<Integer> avg2DayAL = new ArrayList<>();
        ArrayList<Integer> avg3DayAL = new ArrayList<>();
        ArrayList<Integer> avg4DayAL = new ArrayList<>();
        ArrayList<Integer> avg5DayAL = new ArrayList<>();
        ArrayList<Integer> avg6DayAL = new ArrayList<>();
        ArrayList<Integer> avg7DayAL = new ArrayList<>();

        int counter2 = 0;
        Integer runningSentSum2 = 0;
        int counter3 = 0;
        Integer runningSentSum3 = 0;
        int counter4 = 0;
        Integer runningSentSum4 = 0;
        int counter5 = 0;
        Integer runningSentSum5 = 0;
        int counter6 = 0;
        Integer runningSentSum6 = 0;
        int counter7 = 0;
        Integer runningSentSum7 = 0;


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) {
                counter2++;
                counter3++;
                counter4++;
                counter5++;
                counter6++;
                counter7++;
                String[] splitLine = line.split("\t");
                Integer sentiment = Integer.valueOf(splitLine[1]);
                runningSentSum2 += sentiment;
                runningSentSum3 += sentiment;
                runningSentSum4 += sentiment;
                runningSentSum5 += sentiment;
                runningSentSum6 += sentiment;
                runningSentSum7 += sentiment;

                if (counter2 == 2) {
                    avg2DayAL.add((runningSentSum2 / 2));
                    runningSentSum2 = 0;
                    counter2 = 0;
                }
                if (counter3 == 3) {
                    avg3DayAL.add((runningSentSum3 / 3));
                    runningSentSum3 = 0;
                    counter3 = 0;
                }
                if (counter4 == 4) {
                    avg4DayAL.add((runningSentSum4 / 4));
                    runningSentSum4 = 0;
                    counter4 = 0;
                }
                if (counter5 == 5) {
                    avg5DayAL.add((runningSentSum5 / 5));
                    runningSentSum5 = 0;
                    counter5 = 0;
                }
                if (counter6 == 6) {
                    avg6DayAL.add((runningSentSum6 / 6));
                    runningSentSum6 = 0;
                    counter6 = 0;
                }
                if (counter7 == 7) {
                    avg7DayAL.add((runningSentSum7 / 7));
                    runningSentSum7 = 0;
                    counter7 = 0;
                }

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

        String fileLoc = "data/average/dayAverage_";

        try {
            fileWrite(avg2DayAL, fileLoc + "2.txt");
            fileWrite(avg3DayAL, fileLoc + "3.txt");
            fileWrite(avg4DayAL, fileLoc + "4.txt");
            fileWrite(avg5DayAL, fileLoc + "5.txt");
            fileWrite(avg6DayAL, fileLoc + "6.txt");
            fileWrite(avg7DayAL, fileLoc + "7.txt");

        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }

    }

    static void fileWrite(ArrayList<Integer> aList, String fileName) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));

        for (Integer average:aList) {
            bufferedWriter.append(average.toString()).append("\n");
        }

        bufferedWriter.close();
    }

}
