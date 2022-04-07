
//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class User {
    private int userID;
    private Accommodation avgAcc;
    private double[] weights;
    public static class Range{
        public Range(double min,double max){
            this.max = max;
            this.min = min;

        }
        public double min;
        public double max;
    }
    private Range priceRange;
    private Range sizeRange;

    public Range getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Range priceRange) {
        this.priceRange = priceRange;
    }

    public Range getSizeRange() {
        return sizeRange;
    }

    public void setSizeRange(Range sizeRange) {
        this.sizeRange = sizeRange;
    }

    public Range getNumOfRoomsRange() {
        return numOfRoomsRange;
    }

    public void setNumOfRoomsRange(Range numOfRoomsRange) {
        this.numOfRoomsRange = numOfRoomsRange;
    }

    public Range getFloorRange() {
        return floorRange;
    }

    public void setFloorRange(Range floorRange) {
        this.floorRange = floorRange;
    }

    private Range numOfRoomsRange;
    private Range floorRange;


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Accommodation getAvgAcc() {
        return avgAcc;
    }

    public void setAvgAcc(Accommodation avgAcc) {
        this.avgAcc = avgAcc;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }


}
