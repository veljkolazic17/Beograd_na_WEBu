//Klasa za testiranje algoritma
public class Test {

    static User.Range[] ranges = new User.Range[4];

    public static void initRanges() {
        /*
            0 -> price
            1 -> area
            2 -> floor
            3 -> numOfRooms
        */
        ranges[0] = new User.Range(100, 2000);
        ranges[1] = new User.Range(10, 300);
        ranges[2] = new User.Range(0, 10);
        ranges[3] = new User.Range(1, 6);
    }


    public static Accommodation generateRandomAccomodation() {
        Accommodation accommodation = new Accommodation();
        for (int i = 0; i < accommodation.getAttributes().length; i++) {
            accommodation.setAtribute(
                    Math.ceil(Math.random() * (ranges[i].max - ranges[i].min) + ranges[i].min)
                    , i);

        }
        return accommodation;
    }

    public static void main(String[] args) {
        User user = new User();
        MMLVRecommenderImpl MMLVRecommenderImpl = new MMLVRecommenderImpl();
        initRanges();
        for (int i = 0; i < 20; i++) {
            Accommodation accommodation = generateRandomAccomodation();
            MMLVRecommenderImpl.update(user, accommodation);
            System.out.println(accommodation + " User Weigts: " + user.getWeights()[0] + " " + user.getWeights()[1] + " " + user.getWeights()[2] + " " + user.getWeights()[3]);
        }
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < 20; i++) {
            Accommodation accommodation = generateRandomAccomodation();
            MMLVRecommenderImpl.recommend(user, accommodation);
            System.out.print(accommodation);
            System.out.println("Average: " + user.getAvgAcc());
        }
    }
}
