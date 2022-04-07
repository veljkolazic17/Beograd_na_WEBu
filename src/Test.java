//Klasa za testiranje algoritma
public class Test {
    public static void main(String[] args){
        User user1 = new User();

        user1.setWeights(new double[]{1.0, 0.8, 0.3, 0.2});
        user1.setPriceRange(new User.Range(230,350));
        user1.setSizeRange(new User.Range(45,90));
        user1.setFloorRange(new User.Range(2,5));
        user1.setNumOfRoomsRange(new User.Range(1.5,2.5));
        Accommodation a1 = new Accommodation();
        a1.setNumOfRooms(2);
        a1.setPrice(250);
        a1.setFloor(0);
        a1.setSize(60);
        user1.setAvgAcc(a1);
        Accommodation test1 = new Accommodation();
        test1.setSize(60);
        test1.setPrice(450);
        test1.setNumOfRooms(2);
        test1.setFloor(9);
        RecommenderImpl r = new RecommenderImpl();
        System.out.println(r.recommend(user1,test1));
    }
}
