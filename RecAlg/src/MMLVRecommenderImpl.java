import java.util.Arrays;

//Implementacija klase za preporucivanje smestaja
public class MMLVRecommenderImpl implements Recommender {
        @Override
        public void update(User user, Accommodation liked) {
                user.numOfLikes++;

                for (int i = 0; i < 4; i++) {
                        user.getSumAcc().setAtribute(user.getSumAcc().getAttribute(i) + liked.getAttribute(i), i);
                }

                updateRange(user, liked);
                if (user.getLast() == null) {
                        user.setLast(liked);
                        return;
                }
                Accommodation accommodation = user.getLast();
                double delta;
                for (int i = 0; i < liked.getAttributes().length; i++) {
                        delta = (5 - 10 * Math.abs(accommodation.getAttribute(i) - liked.getAttribute(i)) / (user.getRange(i).max - user.getRange(i).min)) / 45;
                        if (user.getWeights()[i] + delta > 1) user.getWeights()[i] = 1;
                        else if (user.getWeights()[i] + delta < 0.1) user.getWeights()[i] = 0.1;
                        else user.getWeights()[i] += delta;
                }
                user.setLast(liked);
        }
        @Override
        public boolean recommend(User user, Accommodation accommodation) {
                // Skaliranje svakog atributa smestaja na vrednosti [0.0 - 10.0]

                double scaledAttribute;
                double rating = 0;
                for (int i = 0; i < accommodation.getAttributes().length; i++) {
                        scaledAttribute = 10 - 10 * Math.abs(accommodation.getAttribute(i) - user.getAvgAcc().getAttribute(i))
                                / ((user.getRange(i).max - user.getRange(i).min));
//                                        * (1 + 1 / user.getWeights()[i]));

                        // Racunanje konacne ocene na osnovu svih skaliranih vrednosti atributa i tezina
                        // tih atributa(njihov prioritet)
                        rating += scaledAttribute * user.getWeights()[i];
                }
                rating /= Arrays.stream(user.getWeights()).sum();

                System.out.println(rating);
                // Ukoliko je ocena veca ili jednaka od 7.5, smestaj se preporucuje korisniku
                return rating >= 7.5;
        }
        @Override
        public void updateRange(User user, Accommodation accommodation) {
                if (user.getRange(0) == null) {
                        for (int i = 0; i < user.getRanges().length; i++) {
                                user.setRange(new User.Range(accommodation.getAttribute(i), accommodation.getAttribute(i)), i);
                        }
                } else {
                        for (int i = 0; i < user.getRanges().length; i++) {
                                if (accommodation.getAttribute(i) > user.getRange(i).max) {
                                        user.setRange(new User.Range(user.getRange(i).min, accommodation.getAttribute(i)), i);
                                } else if (accommodation.getAttribute(i) < user.getRange(i).min) {
                                        user.setRange(new User.Range(accommodation.getAttribute(i), user.getRange(i).max), i);
                                }
                        }
                }
        }
}
