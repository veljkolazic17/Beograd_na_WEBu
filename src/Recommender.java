public interface Recommender {
    User update(User user,Accommodation liked);
    boolean recommend(User user,Accommodation accommodation);
}
