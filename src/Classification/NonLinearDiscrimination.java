package Classification;

import Perception.Perception;
import Samples.TrainingSample;
import Utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class NonLinearDiscrimination {

    List<TrainingSample> trainingSamples;
    List<List<TrainingSample>> trainingSamplesClassified;
    List<DiscriminantFunction> functions;
    ReadFile readFile = new ReadFile();

    public NonLinearDiscrimination(List<TrainingSample> samplesToBeClassified)
    {
        //将大类划分成若干子类
        getSamples();
        divideSubclasses();

        //为每一个子类定义一个判别函数
        Perception perception = new Perception();
        perception.perception1(trainingSamplesClassified);
        functions = perception.getFunctions();

        classify(samplesToBeClassified);

    }

    private void getSamples()
    {
        trainingSamples = readFile.getSamplesFromFile(".\\test\\trainingSamples.txt");
    }

    private void divideSubclasses()
    {
        trainingSamplesClassified = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            trainingSamplesClassified.add(new ArrayList<>());
        }

        for (TrainingSample sample: trainingSamples) {
            if (sample.getX() == 2 && sample.getY() == 8 )
            {
                sample.setClassNum(0);
                trainingSamplesClassified.get(0).add(sample);
            }else if (sample.getX() == 5 && sample.getY() == 13 )
            {
                sample.setClassNum(1);
                trainingSamplesClassified.get(1).add(sample);
            }else if (sample.getX() == 12 && sample.getY() == 19 )
            {
                sample.setClassNum(2);
                trainingSamplesClassified.get(2).add(sample);
            }else if (sample.getX() == 17 && sample.getY() == 13)
            {
                sample.setClassNum(3);
                trainingSamplesClassified.get(3).add(sample);
            }else if (sample.getX() == 7 && sample.getY() == 2)
            {
                sample.setClassNum(4);
                trainingSamplesClassified.get(4).add(sample);
            }else if (sample.getX() == 14 && sample.getY() == 2)
            {
                sample.setClassNum(5);
                trainingSamplesClassified.get(5).add(sample);
            }
        }
    }

    private void classify(List<TrainingSample> samplesToBeClassified)
    {
        LinearDiscrimination linearDiscrimination = new LinearDiscrimination();
        linearDiscrimination.start3(samplesToBeClassified, functions);

        //分大类：
        // 0，1--0；
        // 2，3--1；
        // 4，5--2；
        for (TrainingSample sample: samplesToBeClassified) {
//            if (sample.getClassNum() !=  0 && sample.getClassNum() != 4)
//                System.out.println(sample.getClassNum());
            if (sample.getClassNum() != Integer.MAX_VALUE)
            {
                sample.setClassNum(sample.getClassNum() / 2);
                System.out.println(sample.getClassNum());
            }
        }
        System.out.println();
    }

    /**
     * 以子类中最大值判别函数所对应的子类判别函数作为该类的判别函数
     */
//    private void getClassFunctions()
//    {
//        int validatedX1 = 0;
//        int validatedX2 = 0;
//        for (TrainingSample sample: samples2) {
//            double x21 = ((-1) * sample.getX() * functions.get(1).getWeight1() + (-1) * functions.get(1).getWeight3()) / functions.get(1).getWeight2();
//            double x22 = ((-1) * sample.getX() * functions.get(2).getWeight1() + (-1) * functions.get(2).getWeight3()) / functions.get(2).getWeight2();
//            if (x21 > x22 && sample.getX() > validatedX1)
//                validatedX1 = sample.getX();
//            if (x21 < x22 && sample.getX() > validatedX2)
//                validatedX2 = sample.getX();
//        }
//        if (validatedX1 < validatedX2)
//        {
//            functions.get(1).setValidatedXFrom(0);
//            functions.get(1).setValidatedXTo(validatedX1);
//            functions.get(2).setValidatedXFrom(validatedX1 + 1);
//            functions.get(2).setValidatedXTo(validatedX2);
//        }
//        else if (validatedX1 > validatedX2)
//        {
//            functions.get(1).setValidatedXFrom(0);
//            functions.get(1).setValidatedXTo(validatedX2);
//            functions.get(2).setValidatedXFrom(validatedX2 + 1);
//            functions.get(2).setValidatedXTo(validatedX1);
//        }
//    }


//    public static void main(String[] args)
//    {
//        NonLinearDiscrimination nonLinearDiscrimination = new NonLinearDiscrimination();
//    }
}
