/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs koji mora svaka klasa za JDBC da implementira; Sadrzi CRUD operacije
 * @param <T>
 */
public interface DAO<T> {
    List<T> list();

    void create(T t);

    Optional<T> get(int id);

    void update(T t,int id);

    void delete(int id);

}
