package Classification;

import Samples.TrainingSample;
import Utils.ReadFile;
//import org.jcp.xml.dsig.internal.dom.DOMUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LinearDiscrimination {

    int functionNum;
    List<DiscriminantFunction> functions = new ArrayList<>();
    List<TrainingSample> samples = new ArrayList<>();
    List<List<TrainingSample>> sampleCollections = new ArrayList<>();

    public void start1(List<TrainingSample> rawSamples, List<DiscriminantFunction> functions)
    {
        getDiscriminantFunctions(functions);

        ReadFile readFile = new ReadFile();
        samples = rawSamples;

        classify1();
        for (int i = 0; i < functionNum; i++) {
            for (TrainingSample sample: samples) {
                if (sample.getClassNum() == i){
                    System.out.println("第" + i + "个聚类中的元素有：");
                    System.out.println("(" + sample.getX() + "," + sample.getY() + ")");
                }
            }
        }
    }

    public void start3(List<TrainingSample> rawSamples, List<DiscriminantFunction> functions)
    {
        getDiscriminantFunctions(functions);

        samples = rawSamples;

        classify3();
    }

    private void getDiscriminantFunctions(List<DiscriminantFunction> functions)
    {
        functionNum = functions.size();
        this.functions = functions;

//        System.out.println("请输入判别函数个数（即聚类个数）：");
//        Scanner scanNum = new Scanner(System.in);
//        String functionNumStr = "";
//        functionNumStr += scanNum.nextLine();
//        functionNum = Integer.parseInt(functionNumStr);
//
//        System.out.println("请输入判别函数系数（如判别函数为d(X)=x1+x2+1则输入‘1 1 1’");
//        for (int i = 0; i < functionNum; i++) {
//            System.out.println("请输入：");
//            Scanner scanner = new Scanner(System.in);
//            String input = "";
//            input += scanner.nextLine();
//            String[] charInput = input.split(" ");
//            int[] weights = new int[3];
//            for (int j = 0; j < charInput.length; j++) {
//                weights[j] = Integer.parseInt(charInput[j]);
//            }
//            functions.add(new DiscriminantFunction(weights[0], weights[1], weights[2]));
//        }
    }

    private void classify1()
    {
        double calculation = 0;
        for (TrainingSample sample: samples) {
            for (int i = 0; i < functions.size(); i++) {
                //避免出现function全部权重均为负数的情况
                if (functions.get(i).getWeight1() < 0 && functions.get(i).getWeight2() < 0 && functions.get(i).getWeight3() < 0 )
                {
                    functions.get(i).setWeight1((-1) * functions.get(i).getWeight1());
                    functions.get(i).setWeight2((-1) * functions.get(i).getWeight2());
                    functions.get(i).setWeight3((-1) * functions.get(i).getWeight3());
                }
                calculation = sample.getX() * functions.get(i).getWeight1() + sample.getY() * functions.get(i).getWeight2() + functions.get(i).getWeight3();
                if (sample.getIR() == false)
                {
                    if (calculation > 0 && sample.getClassNum() != Integer.MAX_VALUE)
                    {
                        sample.setIR(true);
                    }
                    if (calculation > 0 && sample.getClassNum() == Integer.MAX_VALUE)
                    {
                        sample.setClassNum(i);
                    }
                    if (calculation == 0)
                        sample.setIR(true);
                }
            }
            if (sample.getIR() == true)
                sample.setClassNum(Integer.MAX_VALUE);
        }
    }

    private void classify3()
    {
        double[] d = new double[functions.size()];
        for (TrainingSample sample: samples) {
            for (int i = 0; i < functions.size(); i++) {
                d[i] = sample.getX() * functions.get(i).getWeight1() + sample.getY() * functions.get(i).getWeight2() + functions.get(i).getWeight3();
            }
            double max = Integer.MIN_VALUE;
            int classNum = 0;
            for (int i = 0; i < d.length; i++) {
                if (d[i] == max)
                    sample.setIR(true);
                else if (d[i] > max)
                {
                    max = d[i];
                    classNum = i;
                }
            }
            if (sample.getIR() == false)
                sample.setClassNum(classNum);
        }
    }

    public List<TrainingSample> getSamples() {
        return samples;
    }
}
