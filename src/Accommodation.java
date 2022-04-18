//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class Accommodation {
    private double price;
    private double size;
    private double numOfRooms;
    private double floor;

    private static double[] maxDiff;

    public static double[] getMaxDiff(){
        return maxDiff;
    }

    // Poziva se na svakih 10 minuta za svaki od atributa
    public static void calcMaxDiff(){

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(double numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public double getFloor() {
        return floor;
    }

    public void setFloor(double floor) {
        this.floor = floor;
    }


}
