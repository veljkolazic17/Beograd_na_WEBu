import java.util.Arrays;


//Implementacija klase za preporucivanje smestaja
public class RecommenderImpl implements Recommender{
    //TODO Implementirati metodu
    @Override
    public User update(User user, Accommodation liked) {
        return null;
    }

    @Override
    public boolean recommend(User user, Accommodation accommodation) {

        //Skaliranje svakog atributa smestaja na vrednosti [0.0 - 10.0]
        double scaledPrice = 10 - Math.abs(accommodation.getPrice() - user.getAvgAcc().getPrice())
                /((user.getPriceRange().max - user.getPriceRange().min)*(1+1/user.getWeights()[0])/10);
        double scaledSize = 10 - Math.abs(accommodation.getSize() - user.getAvgAcc().getSize())
                /((user.getSizeRange().max - user.getSizeRange().min)*(1+1/user.getWeights()[1])/10);
        double scaledNumOfRooms = 10 - Math.abs(accommodation.getNumOfRooms() - user.getAvgAcc().getNumOfRooms())
                /((user.getNumOfRoomsRange().max - user.getNumOfRoomsRange().min)*(1+1/user.getWeights()[2])/10);
        double scaledFloor =10 - Math.abs(accommodation.getFloor() - user.getAvgAcc().getFloor())
                /((user.getFloorRange().max - user.getFloorRange().min)*(1+1/user.getWeights()[3])/10);
        //Racunanje konacne ocene na osnovu svih skaliranih vrednosti atributa i tezina tih atributa(njihov prioritet)
        double rating = (scaledPrice * user.getWeights()[0] + scaledSize * user.getWeights()[1]
                + scaledNumOfRooms * user.getWeights()[2] + scaledFloor * user.getWeights()[3])/
                (Arrays.stream(user.getWeights()).sum());
        System.out.println(rating);
        //Ukoliko je ocena veca ili jednaka od 7.5, smestaj se preporucuje korisniku
        return rating >= 7.5;



    }




}
