package GUI;

import Classification.DiscriminantFunction;
import Classification.LinearDiscrimination;
import Classification.NonLinearDiscrimination;
import Perception.Perception;
import Samples.ClassifyTrainingSamples;
import Samples.TrainingSample;
import Utils.ReadFile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawCoordinateSystem extends JPanel{

    final private  int rowNum = 20;

    ReadFile readFile = new ReadFile();
    List<TrainingSample> samples_1 = new ArrayList<>();
    List<TrainingSample> samples_2;
    List<TrainingSample> rawTrainingSamples = new ArrayList<>();
    List<List<TrainingSample>> classifiedTrainingSamples = new ArrayList<>();
    List<DiscriminantFunction> functions = new ArrayList<>();

    @Override
    protected void  paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //绘制坐标系
        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < rowNum; i++) {
            g.drawLine(20,20 + 20 * i,400,20 + 20 * i);
        }

        for (int i = 0; i < rowNum; i++) {
            g.drawLine(20 + 20 * i,20, 20 + 20 * i,400);
        }

        DrawSub(g);
        drawFunctions(g);
    }

    private void DrawSub(Graphics g)
    {
        if (!classifiedTrainingSamples.isEmpty())
        {
            //绘制坐标点
            Color[] colors = new Color[classifiedTrainingSamples.size()];
            if (colors.length >= 6)
            {
                colors[0] = Color.red;
                colors[1] = Color.magenta;
                colors[2] = Color.yellow;
                colors[3] = Color.orange;
                colors[4] = Color.blue;
                colors[5] = Color.cyan;
                if (colors.length == 7)
                    colors[6] = Color.lightGray;
            }

            if (classifiedTrainingSamples.size() > 0) {
                for (int i = 0; i < classifiedTrainingSamples.size(); i++) {
                    for (TrainingSample sample: classifiedTrainingSamples.get(i)) {
                        //坐标点为( 400 - sample.getX * 20, 400 - sample.getY * 20 )
                        g.setColor(colors[i]);
                        g.drawOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
                        g.fillOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
                    }
                }
            }
        }else
        {
            for (TrainingSample sample: rawTrainingSamples) {
                g.setColor(Color.lightGray);
                g.drawOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
                g.fillOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
            }
        }

    }

    private void drawFunctions(Graphics g)
    {
        if (!functions.isEmpty())
        {
            //函数起始坐标(0,-function.getWeight3()/function.getWeight2()), (20, -(20 * function.getWeight1() + function.getWeight3())/ function.getWeight2())
            //(i, -(i * function.getWeight1() + function.getWeight3())/ function.getWeight2())
            for (DiscriminantFunction function: functions) {
                g.setColor(Color.green);
                int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
                boolean y1isSet = false;
                for (int i = 0; i < 20; i++) {
                    if (function.getWeight2() != 0)
                    {
                        int y = (int)(-(i * function.getWeight1() + function.getWeight3())/ function.getWeight2());
                        if (y1isSet == false){
                            if ((400 - 20 * y) >= 20 && (400 - 20 * y) <= 420)
                            {
                                x1 = i;
                                y1 = y;
                                y1isSet = true;
                            }
                        }else
                        {
                            if ((400 - 20 * y) >= 20 && (400 - 20 * y) <= 420)
                            {
                                x2 = i;
                                y2 = y;
                            }
                        }
                    }else
                    {
                        x1 = x2 = i;
                        y1 = 20;
                        y2 = 420;
                    }
                }
                g.drawLine(20 + 20 * x1, 400 - 20 * y1, 20 + 20 * x2, 400 - 20 * y2);
            }
        }
    }

    public void ReadPointAndClassify(String file) {
        if (!classifiedTrainingSamples.isEmpty())
            classifiedTrainingSamples.clear();

        if (!rawTrainingSamples.isEmpty())
            rawTrainingSamples.clear();

        try {
            clearCoordinates();
            rawTrainingSamples = readFile.getSamplesFromFile(file);
            ClassifyTrainingSamples classifyTrainingSamples = new ClassifyTrainingSamples(".\\test\\trainingSamples.txt");
            classifiedTrainingSamples = classifyTrainingSamples.getClassifiedTrainingSamples();
            repaint();
        }
        catch (Exception e) {

        }
    }

    public void ReadPoint(String file) {
        if (!rawTrainingSamples.isEmpty())
            rawTrainingSamples.clear();

        try {
            clearCoordinates();
            rawTrainingSamples = readFile.getSamplesFromFile(file);
            repaint();
        }
        catch (Exception e) {

        }
    }

    public void clearCoordinates(){
        if (!rawTrainingSamples.isEmpty())
            rawTrainingSamples.clear();
        if (!classifiedTrainingSamples.isEmpty())
            classifiedTrainingSamples.clear();
        if (!functions.isEmpty())
            functions.clear();
        repaint();
    }

    public void perception(int situation){
        if (!rawTrainingSamples.isEmpty())
        {
            ClassifyTrainingSamples classifyTrainingSamples = new ClassifyTrainingSamples(".\\test\\trainingSamples.txt");
            classifiedTrainingSamples = classifyTrainingSamples.getClassifiedTrainingSamples();

            for (TrainingSample sample: rawTrainingSamples) {
                sample.setClassNum(Integer.MAX_VALUE);
            }

            Perception perception = new Perception();
            if (situation == 1)
                perception.perception1(classifiedTrainingSamples);
            else if (situation == 3)
                perception.perception3(classifiedTrainingSamples);

            LinearDiscrimination linearDiscrimination = new LinearDiscrimination();
            if (situation == 1)
                linearDiscrimination.start1(rawTrainingSamples, perception.getFunctions());
            else if (situation == 3)
                linearDiscrimination.start3(rawTrainingSamples, perception.getFunctions());

//            rawTrainingSamples.clear();
            rawTrainingSamples = linearDiscrimination.getSamples();

            List<TrainingSample> temp = new ArrayList<>();

            for (TrainingSample sample: rawTrainingSamples) {
                temp.add(sample);
            }

            clearCoordinates();
            rawTrainingSamples = temp;

            if (situation == 1)
                functions = perception.getFunctions();
            else if (situation == 3)
            {
                List<DiscriminantFunction> tempFunctions;
                tempFunctions = perception.getFunctions();

                functions = new ArrayList<>();
                for (int i = 0; i < tempFunctions.size(); i++) {
                    for (int j = i + 1; j < tempFunctions.size(); j++) {
                        double weight1 = tempFunctions.get(i).getWeight1() - tempFunctions.get(j).getWeight1();
                        double weight2 = tempFunctions.get(i).getWeight2() - tempFunctions.get(j).getWeight2();
                        double weight3 = tempFunctions.get(i).getWeight3() - tempFunctions.get(j).getWeight3();
                        functions.add(new DiscriminantFunction(weight1, weight2, weight3));
                    }
                }
            }

            for (int i = 0; i < 7; i++)
            {
                classifiedTrainingSamples.add(new ArrayList<>());
            }

            for (TrainingSample sample: rawTrainingSamples) {
                if (sample.getClassNum() != Integer.MAX_VALUE)
                    classifiedTrainingSamples.get(sample.getClassNum()).add(sample);
                else
                    classifiedTrainingSamples.get(6).add(sample);
            }

            repaint();

        }
    }

    public void nonLinear()
    {
        if (!rawTrainingSamples.isEmpty())
        {
            NonLinearDiscrimination nonLinearDiscrimination = new NonLinearDiscrimination(rawTrainingSamples);
            for (int i = 0; i < 7; i++) {
                classifiedTrainingSamples.add(new ArrayList<>());
            }
            for (TrainingSample sample: rawTrainingSamples) {
                if (sample.getClassNum() != Integer.MAX_VALUE)
                    classifiedTrainingSamples.get(sample.getClassNum() * 2).add(sample);
                else
                    classifiedTrainingSamples.get(6).add(sample);
            }
            repaint();
        }
    }
}

