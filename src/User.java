
//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class User {
    private int userID;
    private Accommodation avgAcc;
    private double[] weights;

    private Accommodation lastLiked;

    // Unutrasnja klasa range
    public static class Range{
        public Range(double min,double max){
            this.max = max;
            this.min = min;

        }
        public double min;
        public double max;
    }
    
    private Range[] ranges;

    public Range getRange(int index){
        return ranges[index];
    }
    
    public void setRange(Range range, int index){
        ranges[index] = range;
    }

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

    public Accommodation getLast(){
        return lastLiked;
    }

    public void setLast(Accommodation accommodation){
        lastLiked = accommodation;
    }
}
