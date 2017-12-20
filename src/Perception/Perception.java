package Perception;

import Classification.DiscriminantFunction;
import Samples.AugmentedVector;
import Samples.TrainingSample;
import Utils.ReadFile;

import java.util.*;

public class Perception {

    List<List<TrainingSample>> trainingSamples = new ArrayList<>();
    List<List<AugmentedVector>> augmentedVectors = new ArrayList<>();
    List<DiscriminantFunction> functions = new ArrayList<>();
    int correctionIncrement = 1;
    int[] d;
    int[] satisfied1;
    int[][] satisfied3;
    static int iterNum = 0;
    int classNumOrChildClassNum = 0;

    public void perception1(List<List<TrainingSample>> allSamples)
    {
        initTrainingSamples(allSamples);
        getAugmentedVectors();
        initWeightVector();
        iterate1();
    }

    public void perception3(List<List<TrainingSample>> allSamples)
    {
        initTrainingSamples(allSamples);
        getAugmentedVectors();
        initWeightVector();
        iterate3();
    }

    //初始化trainingSamples
    private void initTrainingSamples(List<List<TrainingSample>> allSamples)
    {
        //若是非线性函数判别调用此感知器算法，则首先根据子类进行学习；否则根据大类进行学习
        for (int i = 0; i < allSamples.size(); i++) {
            for (int j = i + 1; j < allSamples.size(); j++) {
                if (allSamples.get(i).get(0).getClassNum() == allSamples.get(j).get(0).getClassNum())
                    classNumOrChildClassNum = 1;
            }
        }

        trainingSamples = allSamples;
    }

    //第一步，将训练样本写成增广向量形式
    private void getAugmentedVectors()
    {
        int newClassNum = 0;
        for (List<TrainingSample> samples: trainingSamples) {
            List<AugmentedVector> vectors = new ArrayList<>();
            if (classNumOrChildClassNum == 0)
            {
                for (TrainingSample sample: samples) {
                    AugmentedVector vector = new AugmentedVector(sample.getX(), sample.getY(), 1);
                    vector.setClassNum(sample.getClassNum());
                    vectors.add(vector);
                }
            }else
            {
                for (TrainingSample sample: samples) {
                    AugmentedVector vector = new AugmentedVector(sample.getX(), sample.getY(), 1);
                    vector.setClassNum(newClassNum);
                    vectors.add(vector);
                }
                newClassNum++;
            }
            augmentedVectors.add(vectors);
        }

        for (List<AugmentedVector> vectors: augmentedVectors) {
            for (AugmentedVector vector: vectors) {
                System.out.println("增广向量为" + "(" + vector.getX() + "," + vector.getY() + "," + vector.getZ() + ")");
            }
        }
    }

    //初始化权向量
    private void initWeightVector()
    {
        for (int i = 0; i < augmentedVectors.size(); i++) {
            DiscriminantFunction initialFunction = new DiscriminantFunction(0, 0, 0);
            initialFunction.setClassNum(i);
            functions.add(initialFunction);
        }
    }

    //第二步，迭代，多类情况1
    private void iterationBody1(int functionNum)
    {
            iterNum++;
            satisfied1[functionNum] = 0;

            //对于除了当前判别函数对应分类之外的其他分类中的样本点做规范化处理
            List<AugmentedVector> normalizedSamples = new ArrayList<>();
            for (int j = 0; j < augmentedVectors.size(); j++) {
                if (functionNum == j)
                {
                    for (AugmentedVector vector: augmentedVectors.get(j)) {
                        normalizedSamples.add(new AugmentedVector(vector.getX(), vector.getY(), vector.getZ()));
                    }
                }else
                {
                    for (AugmentedVector vector: augmentedVectors.get(j)) {
                        normalizedSamples.add(new AugmentedVector((-1)* vector.getX(), (-1) * vector.getY(), (-1) * vector.getZ()));
                    }
                }
            }

            double calculation;
        for (int i = 0; i < normalizedSamples.size(); i++) {
            calculation = functions.get(functionNum).getWeight1() * normalizedSamples.get(i).getX() +
                    functions.get(functionNum).getWeight2() * normalizedSamples.get(i).getY() +
                    functions.get(functionNum).getWeight3() * normalizedSamples.get(i).getZ();
            if (calculation <= 0)
            {
                System.out.println(i);
                functions.get(functionNum).setWeight1((functions.get(functionNum).getWeight1() + normalizedSamples.get(i).getX()));
                functions.get(functionNum).setWeight2((functions.get(functionNum).getWeight2() + normalizedSamples.get(i).getY()));
                functions.get(functionNum).setWeight3((functions.get(functionNum).getWeight3() + normalizedSamples.get(i).getZ()));
            }else
                satisfied1[functionNum]++;
        }
    }

    private void iterate1()
    {
        satisfied1 = new int[functions.size()];
        int sampleNum = 0;
        for (int i = 0; i < trainingSamples.size(); i++) {
            sampleNum += trainingSamples.get(i).size();
        }
        for (int i = 0; i < functions.size(); i++) {
            while (satisfied1[i] != sampleNum)
                iterationBody1(i);
        }

        for (DiscriminantFunction function: functions) {
            System.out.println(function.getWeight1() + " " + function.getWeight2() + " " + function.getWeight3());
        }
    }

    //第二步，迭代，多类情况3
    private void iterationBody3()
    {
        double[] d = new double[functions.size()];

        for (int i = 0; i < augmentedVectors.size(); i++) {
            for (int j = 0; j < augmentedVectors.get(i).size(); j++) {

                satisfied3[i][j] = 0;

                for (int k = 0; k < d.length; k++) {
                    d[k] = functions.get(k).getWeight1() * augmentedVectors.get(i).get(j).getX()
                            + functions.get(k).getWeight2() * augmentedVectors.get(i).get(j).getY()
                            + functions.get(k).getWeight3() * augmentedVectors.get(i).get(j).getZ();
                }

                //修改权向量
                //判断d[j]是否是d中最大的
                boolean djIsLargest = true;
                for (int k = 0; k < d.length; k++) {
                    if (i != k) {
                        if (d[i] <= d[k])
                            djIsLargest = false;
                    }
                }
                //若d[j]不是d[]中最大的，则functions[j]需增加,比d[j]大的d需减少
                if (djIsLargest == false){
                    functions.get(i).setWeight1(functions.get(i).getWeight1() + 0.2 * augmentedVectors.get(i).get(j).getX());
                    functions.get(i).setWeight2(functions.get(i).getWeight2() + 0.2 * augmentedVectors.get(i).get(j).getY());
                    functions.get(i).setWeight3(functions.get(i).getWeight3() + 0.2 * augmentedVectors.get(i).get(j).getZ());

                    for (int k = 0; k < d.length; k++) {
                        if (j != k)
                            if (d[j] <= d[k]){
                                functions.get(k).setWeight1(functions.get(k).getWeight1() - 0.2 * augmentedVectors.get(i).get(j).getX());
                                functions.get(k).setWeight2(functions.get(k).getWeight2() - 0.2 * augmentedVectors.get(i).get(j).getY());
                                functions.get(k).setWeight3(functions.get(k).getWeight3() - 0.2 * augmentedVectors.get(i).get(j).getZ());
                            }
                    }
                }else{
                    //若d[j]是d中最大的则说明样本分类正确
                    satisfied3[i][j] = 1;
                }

            }
        }
    }

    private void iterate3()
    {
        satisfied3 = new int[augmentedVectors.size()][augmentedVectors.get(0).size()];
        boolean satisfied = false;

        while (satisfied == false)
        {
            iterationBody3();
            int count = 0;
            for (int i = 0; i < satisfied3.length; i++) {
                for (int j = 0; j < satisfied3[i].length; j++) {
                    count += satisfied3[i][j];
                }
            }
            System.out.println(count);
            if (count == trainingSamples.size())
                satisfied = true;
        }

        for (DiscriminantFunction function: functions) {
            System.out.println(function.getWeight1() + " " + function.getWeight2() + " " + function.getWeight3());
        }
    }

    public List<DiscriminantFunction> getFunctions() {
        return functions;
    }

}
