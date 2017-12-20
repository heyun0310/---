import Classification.DiscriminantFunction;
import Classification.LinearDiscrimination;
import Perception.Perception;
import Samples.TrainingSample;
import Utils.ReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    List<TrainingSample> rawTrainingSamples;
    List<List<TrainingSample>> classifiedTrainingSamples;
    List<DiscriminantFunction> functions;

    public static void main(String[] args) throws IOException{
        Main test1 = new Main();
        test1.test1();
//        writeFile(".\\test\\samplesToBeClassified.txt");
    }

    private static void writeFile(String filePath) throws IOException
    {
        FileOutputStream fileOutputStream = null;
        PrintWriter printWriter = null;

        try
        {
            String string = "";
            StringBuffer buffer = new StringBuffer();
            File file = new File(filePath);

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    string += "(" + i +"," + j + ")" + "\n";
                }
            }

            buffer.append(string);
            fileOutputStream = new FileOutputStream(file);
            printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(buffer.toString().toCharArray());
            printWriter.flush();
        }catch (Exception e)
        {
            if (printWriter != null)
                printWriter.close();
            if (fileOutputStream != null)
                fileOutputStream.close();
        }
    }

    private void test1()
    {
        ReadFile readFile = new ReadFile();
        rawTrainingSamples = readFile.getSamplesFromFile(".\\test\\trainingSamples.txt");

        classifiedTrainingSamples = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<TrainingSample> samplesInOneClass = new ArrayList<>();
            classifiedTrainingSamples.add(samplesInOneClass);
        }

        for (TrainingSample sample: rawTrainingSamples) {
//            if ((sample.getX() >= 2 && sample.getX() <= 4) && (sample.getY() >= 8 && sample.getY() <= 10))
//            {
//                sample.setClassNum(0);
//                classifiedTrainingSamples.get(0).add(sample);
//            }else if ((sample.getX() >= 5 && sample.getX() <= 9) && (sample.getY() >= 13 && sample.getY() <= 15))
//            {
//                sample.setClassNum(1);
//                classifiedTrainingSamples.get(1).add(sample);
//            }else if ((sample.getX() >= 12 && sample.getX() <= 14) && (sample.getY() >= 8 && sample.getY() <= 10))
//            {
//                sample.setClassNum(2);
//                classifiedTrainingSamples.get(2).add(sample);
//            }else if ((sample.getX() >= 17 && sample.getX() <= 19) && (sample.getY() >= 13 && sample.getY() <= 15))
//            {
//                sample.setClassNum(3);
//                classifiedTrainingSamples.get(3).add(sample);
//            }else if ((sample.getX() >= 7 && sample.getX() <= 9) && (sample.getY() >= 2 && sample.getY() <= 4))
//            {
//                sample.setClassNum(4);
//                classifiedTrainingSamples.get(4).add(sample);
//            }else if ((sample.getX() >= 12 && sample.getX() <= 14) && (sample.getY() >= 2 && sample.getY() <= 4))
//            {
//                sample.setClassNum(5);
//                classifiedTrainingSamples.get(5).add(sample);
//            }
            if (sample.getX() == 2 && sample.getY() == 8 )
            {
                sample.setClassNum(0);
                classifiedTrainingSamples.get(0).add(sample);
            }else if (sample.getX() == 5 && sample.getY() == 13 )
            {
                sample.setClassNum(1);
                classifiedTrainingSamples.get(1).add(sample);
            }else if (sample.getX() == 12 && sample.getY() == 19 )
            {
                sample.setClassNum(2);
                classifiedTrainingSamples.get(2).add(sample);
            }else if (sample.getX() == 17 && sample.getY() == 13 )
            {
                sample.setClassNum(3);
                classifiedTrainingSamples.get(3).add(sample);
            }else if (sample.getX() == 7 && sample.getY() == 2)
            {
                sample.setClassNum(4);
                classifiedTrainingSamples.get(4).add(sample);
            }else if (sample.getX() == 14 && sample.getY() == 2)
            {
                sample.setClassNum(5);
                classifiedTrainingSamples.get(5).add(sample);
            }
        }

        //使用感知器算法获得判别函数
        Perception perception = new Perception();
        perception.perception3(classifiedTrainingSamples);
        functions = perception.getFunctions();

        LinearDiscrimination linearDiscrimination = new LinearDiscrimination();
        linearDiscrimination.start3(rawTrainingSamples, functions);

    }
}
