package Samples;

public class TrainingSample {

    private int x;
    private int y;
    private int classNum = Integer.MAX_VALUE;
    private int subClassNum = 0;
    private boolean ir = false;

    public TrainingSample()
    {
        this.x = 0;
        this.y = 0;
    }

    public TrainingSample(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getIR() {
        return ir;
    }

    public void setIR(boolean ir) {
        this.ir = ir;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public int getSubClassNum() {
        return subClassNum;
    }

    public void setSubClassNum(int subClassNum) {
        this.subClassNum = subClassNum;
    }
}
