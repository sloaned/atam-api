package assessment.controllers;

import assessment.entities.FailedResponse;
import assessment.entities.Response;
import assessment.entities.SuccessfulResponse;
import assessment.services.interfaces.IBaseService;
import assessment.utilities.Utils;
import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * Base abstract controller with basic CRUD that other controllers extend from
 */
public abstract class BaseController<T> {

    protected IBaseService<T> service;

    protected Logger logger = LogManager.getLogger(this.getClass());

    /**
     * A constructor that takes a BaseService, implemented classes should autowire this
     * @param service The service to use, should be autowired
     */
    public BaseController(IBaseService<T> service) {
        this.service = service;
    }

    /**
     * Method to get all of type T
     * @return A response entity with a list of T objects
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Response> getAll() {
        System.out.println("in the getAll method");
        try {
            List<T> entities = service.getAll();
            if (entities.isEmpty()) {
                System.out.println("Got an empty list");
                logger.info("Got an empty list");
            } else {
                System.out.println("got a list! " + entities);
                logger.debug("Got list: " + entities);
                logger.info("Got list of: " + entities.get(0).getClass().getSimpleName());
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(entities), HttpStatus.OK);
        } catch (HttpException e) {
            System.out.println("HttpException error: " + e);
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    /**
     * Method to get individual object
     * @param id The id of the object
     * @return A response entity with the object in it
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> get(@PathVariable String id) {
        try {
            T entity = service.get(id);
            logger.debug("Got " + entity.getClass().getSimpleName() + ": " + entity);
            logger.info("Got a " + entity.getClass().getSimpleName());
            return new ResponseEntity<>(new SuccessfulResponse<>(entity), HttpStatus.OK);
        } catch (HttpException e) {
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    /**
     * A method to create a new object
     * @param entity The object to create
     * @return A response entity with the created object
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Response> create(@RequestBody T entity) {
        try {
            T theEntity = service.create(entity);
            logger.debug("Created " + entity.getClass().getSimpleName() + ": " + entity);
            logger.info("Created a " + entity.getClass().getSimpleName());
            return new ResponseEntity<>(new SuccessfulResponse<>(theEntity), HttpStatus.CREATED);
        } catch (HttpException e) {
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    /**
     * A method to update an existing object
     * @param id The id of the object to update
     * @param entity The updated version of the object
     * @return A response entity with the new version of the object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> update(@PathVariable String id, @RequestBody T entity) {

        try {
            T theEntity = service.update(id, entity);
            logger.debug("Updated " + entity.getClass().getSimpleName() + ": " + entity);
            logger.info("Updated a " + entity.getClass().getSimpleName());
            return new ResponseEntity<>(new SuccessfulResponse<>(theEntity), HttpStatus.OK);
        } catch (HttpException e) {
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    protected ResponseEntity<Response> createFailedResponse(String message) {
        HttpStatus status = Utils.getHttpErrorStatus(message);
        FailedResponse response = new FailedResponse(status);
        return new ResponseEntity<>(response, status);
    }
}
