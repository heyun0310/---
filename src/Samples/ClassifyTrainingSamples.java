package Samples;

import Utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class ClassifyTrainingSamples {

    List<TrainingSample> rawTrainingSamples;
    List<List<TrainingSample>> classifiedTrainingSamples;

    public ClassifyTrainingSamples(String filePath) {
        ReadFile readFile = new ReadFile();
        rawTrainingSamples = readFile.getSamplesFromFile(filePath);

        classifiedTrainingSamples = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<TrainingSample> samplesInOneClass = new ArrayList<>();
            classifiedTrainingSamples.add(samplesInOneClass);
        }

        for (TrainingSample sample : rawTrainingSamples) {
//            if ((sample.getX() >= 2 && sample.getX() <= 4) && (sample.getY() >= 8 && sample.getY() <= 10)) {
//                sample.setClassNum(0);
//                classifiedTrainingSamples.get(0).add(sample);
//            } else if ((sample.getX() >= 7 && sample.getX() <= 9) && (sample.getY() >= 13 && sample.getY() <= 15)) {
//                sample.setClassNum(1);
//                classifiedTrainingSamples.get(1).add(sample);
//            } else if ((sample.getX() >= 12 && sample.getX() <= 14) && (sample.getY() >= 8 && sample.getY() <= 10)) {
//                sample.setClassNum(2);
//                classifiedTrainingSamples.get(2).add(sample);
//            } else if ((sample.getX() >= 17 && sample.getX() <= 19) && (sample.getY() >= 13 && sample.getY() <= 15)) {
//                sample.setClassNum(3);
//                classifiedTrainingSamples.get(3).add(sample);
//            } else if ((sample.getX() >= 7 && sample.getX() <= 9) && (sample.getY() >= 2 && sample.getY() <= 4)) {
//                sample.setClassNum(4);
//                classifiedTrainingSamples.get(4).add(sample);
//            } else if ((sample.getX() >= 12 && sample.getX() <= 14) && (sample.getY() >= 2 && sample.getY() <= 4)) {
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
    }

    public List<List<TrainingSample>> getClassifiedTrainingSamples() {
        return classifiedTrainingSamples;
    }
}
