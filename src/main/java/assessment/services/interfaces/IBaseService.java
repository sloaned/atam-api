package assessment.services.interfaces;

import org.apache.http.HttpException;

import java.util.List;

/**
 * A baser service interface that other interfaces extend from
 * @param <T> The type of object
 */
public interface IBaseService<T> {

    /**
     * Gets all of type T
     * @return A list of T objects
     * @throws HttpException
     */
    List<T> getAll() throws HttpException;

    /**
     * Gets a specific object
     * @param id The id of the object to get
     * @return The object requested
     * @throws HttpException
     */
    T get(String id) throws HttpException;

    /**
     * Creates a new object of type T
     * @param obj The object to create
     * @return The newly created object
     * @throws HttpException
     */
    T create(T obj) throws HttpException;

    /**
     * Updates an existing object of type T
     * @param id The id of the object to update
     * @param obj The new version of the object
     * @return The updated object
     * @throws HttpException
     */
    T update(String id, T obj) throws HttpException;
}
