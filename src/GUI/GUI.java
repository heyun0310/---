package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{

    private DrawCoordinateSystem drawCoordinateSystem;

    private final JPanel panel = new JPanel();
//    private final JPanel buttonPanel = new JPanel();
    private final JButton trainingSampleButton = new JButton();
    private final JButton samplesToBeClassifiedButton = new JButton();
    private final JButton clearCoordinatesButton = new JButton();
    private final JButton perception1Button = new JButton();
    private final JButton perception3Button = new JButton();
    private final JButton nonLinearButton = new JButton();
    private final JPanel voidPanel1 = new JPanel();
    private final JPanel voidPanel2= new JPanel();
    private final FileDialog fileDlg = new FileDialog(this, "Open File", FileDialog.LOAD);

    public GUI()
    {
        super("Exercise2");

        //使用网格包布局管理器
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(layout);

        drawCoordinateSystem = new DrawCoordinateSystem();
        drawCoordinateSystem.setBackground(Color.gray);

        trainingSampleButton.setText("显示训练样本");
        trainingSampleButton.setFont(new Font("宋体", Font.PLAIN, 15));

        samplesToBeClassifiedButton.setText("显示待分类样本");
        samplesToBeClassifiedButton.setFont(new Font("宋体", Font.PLAIN, 15));

        clearCoordinatesButton.setText("清空所有坐标点");
        clearCoordinatesButton.setFont(new Font("宋体", Font.PLAIN, 15));

        perception1Button.setText("感知器多类1分类");
        perception1Button.setFont(new Font("宋体", Font.PLAIN, 15));

        perception3Button.setText("感知器多类3分类");
        perception3Button.setFont(new Font("宋体", Font.PLAIN, 15));

        nonLinearButton.setText("非线性分类");
        nonLinearButton.setFont(new Font("宋体", Font.PLAIN, 15));

        //为Button设置监听
        ButtonHandler handler = new ButtonHandler();
        trainingSampleButton.addActionListener(handler);
        samplesToBeClassifiedButton.addActionListener(handler);
        clearCoordinatesButton.addActionListener(handler);
        perception1Button.addActionListener(handler);
        perception3Button.addActionListener(handler);
        nonLinearButton.addActionListener(handler);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 6;
        constraints.gridheight = 10;
        constraints.insets = new Insets(5,5,5,5);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        layout.setConstraints(drawCoordinateSystem, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.insets = new Insets(5,5,5,5);
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.gridx = 6;
        constraints.gridy = 0;
        layout.setConstraints(voidPanel1, constraints);

        constraints.gridy = 2;
        constraints.gridheight = 1;
        layout.setConstraints(trainingSampleButton, constraints);

        constraints.gridy = 3;
        layout.setConstraints(samplesToBeClassifiedButton, constraints);

        constraints.gridy = 4;
        layout.setConstraints(perception1Button, constraints);

        constraints.gridy = 5;
        layout.setConstraints(perception3Button, constraints);

        constraints.gridy = 6;
        layout.setConstraints(nonLinearButton, constraints);

        constraints.gridy = 7;
        layout.setConstraints(clearCoordinatesButton, constraints);

        constraints.gridy = 8;
        constraints.weighty = 2;
        layout.setConstraints(voidPanel2, constraints);

        panel.add(drawCoordinateSystem);
        panel.add(trainingSampleButton);
        panel.add(samplesToBeClassifiedButton);
        panel.add(clearCoordinatesButton);
        panel.add(perception1Button);
        panel.add(perception3Button);
        panel.add(nonLinearButton);
        panel.add(voidPanel1);
        panel.add(voidPanel2);

        add(panel);

    }

    public static void main(String[] args)
    {
        GUI gui = new GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(630,460);
        gui.setVisible(true);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == trainingSampleButton) {
                fileDlg.setVisible(true);
                String fileName = fileDlg.getFile();
                String path = fileDlg.getDirectory();
                if (fileName != null) {
                    drawCoordinateSystem.ReadPointAndClassify(path + fileName);
                }
            }else if (event.getSource() == samplesToBeClassifiedButton){
                fileDlg.setVisible(true);
                String fileName = fileDlg.getFile();
                String path = fileDlg.getDirectory();
                if (fileName != null) {
                    drawCoordinateSystem.ReadPoint(path + fileName);
                }
            }else if (event.getSource() == clearCoordinatesButton){
                drawCoordinateSystem.clearCoordinates();
            }else if (event.getSource() == perception1Button){
                drawCoordinateSystem.perception(1);
            }else if (event.getSource() == perception3Button){
                drawCoordinateSystem.perception(3);
            }else if (event.getSource() == nonLinearButton){
                drawCoordinateSystem.nonLinear();
            }
        }
    }
}


