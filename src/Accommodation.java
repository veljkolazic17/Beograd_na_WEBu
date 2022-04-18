//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class Accommodation {
    private double[] attributes;

    public Accommodation(){
        attributes = new double[4];
    }

    public double[] getAttributes(){
        return attributes;
    }

    public double getAttribute(int index){
        return attributes[index];
    }

    public void setAtribute(double val, int index){
        attributes[index] = val;
    }

}
