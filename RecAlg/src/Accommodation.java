//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class Accommodation {
    private double[] attributes;

    public Accommodation() {
        /*
            0 -> price
            1 -> area
            2 -> floor
            3 -> numOfRooms
        */
        attributes = new double[4];
    }

    public Accommodation(double price, double area, double floor, double numOfRooms) {
        attributes = new double[4];
        attributes[0] = price;
        attributes[1] = area;
        attributes[2] = floor;
        attributes[3] = numOfRooms;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public double getAttribute(int index) {
        return attributes[index];
    }

    public void setAtribute(double val, int index) {
        attributes[index] = val;
    }

    @Override
    public String toString() {
        return "Price: " + attributes[0] + " Area: " + attributes[1] + " Floor: " + attributes[2] + " NumOfRooms: " + attributes[3] + "\n";
    }

}
