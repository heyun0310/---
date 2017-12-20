package GUI;


import Samples.TrainingSample;
import Utils.ReadFile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawSamples extends JPanel
{

    ReadFile readFile = new ReadFile();
    List<TrainingSample> samples;

    @Override
    protected void  paintComponent(Graphics g)
    {
        //绘制坐标点
        samples = readFile.getSamplesFromFile(".\\perception.\\trainingSamples.txt");
        for (TrainingSample sample: samples) {
            //坐标点为( 400 - sample.getX * 20, 400 - sample.getY * 20 )
            g.drawOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
            g.setColor(Color.black);
            g.fillOval(20 + sample.getX() * 20 - 5, 400 - sample.getY() * 20 - 5, 10, 10);
        }
    }
}
