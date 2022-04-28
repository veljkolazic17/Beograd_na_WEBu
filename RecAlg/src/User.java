
//Ova klasa je reprezentativna, i sluzi za testiranje klase RecommenderImpl
public class User {

    private int userID;
    private Accommodation sumAcc;
    private double[] weights;
    private Range[] ranges;
    private Accommodation lastLiked;

    /*
     * Simulation
     * */
    public int numOfLikes;


    public User() {

        numOfLikes = 0;

        sumAcc = new Accommodation();
        lastLiked = null;
        ranges = new Range[4];
        weights = new double[4];
        for (int i = 0; i < 4; i++) {
            weights[i] = 0.5;
            ranges[i] = null;
        }
    }

    // Unutrasnja klasa range
    public static class Range {
        public Range(double min, double max) {
            this.max = max;
            this.min = min;

        }

        public double min;
        public double max;
    }


    public Range getRange(int index) {
        return ranges[index];
    }

    public Range[] getRanges() {
        return ranges;
    }

    public void setRange(Range range, int index) {
        ranges[index] = range;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Accommodation getSumAcc() {
        return sumAcc;
    }

    public void setSumAcc(Accommodation sumAcc) {
        this.sumAcc = sumAcc;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public Accommodation getLast() {
        return lastLiked;
    }

    public void setLast(Accommodation accommodation) {
        lastLiked = accommodation;
    }

    public Accommodation getAvgAcc() {
        if (numOfLikes == 0) return null;
        return new Accommodation(sumAcc.getAttribute(0) / numOfLikes, sumAcc.getAttribute(1) / numOfLikes, sumAcc.getAttribute(2) / numOfLikes, sumAcc.getAttribute(3) / numOfLikes);
    }

}
