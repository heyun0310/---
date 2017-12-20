package Classification;

public class DiscriminantFunction {

    double weight1;
    double weight2;
    double weight3;
    int classNum;

    //分段线性函数使用
    int validatedXFrom;
    int validatedXTo;

    public DiscriminantFunction(double weight1, double weight2, double weight3)
    {
        this.weight1 = weight1;
        this.weight2 = weight2;
        this.weight3 = weight3;
    }

    public double getWeight1() {
        return weight1;
    }

    public void setWeight1(double weight1) {
        this.weight1 = weight1;
    }

    public double getWeight2() {
        return weight2;
    }

    public void setWeight2(double weight2) {
        this.weight2 = weight2;
    }

    public double getWeight3() {
        return weight3;
    }

    public void setWeight3(double weight3) {
        this.weight3 = weight3;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public int getValidatedXFrom() {
        return validatedXFrom;
    }

    public void setValidatedXFrom(int validatedXFrom) {
        this.validatedXFrom = validatedXFrom;
    }

    public int getValidatedXTo() {
        return validatedXTo;
    }

    public void setValidatedXTo(int validatedXTo) {
        this.validatedXTo = validatedXTo;
    }


}
