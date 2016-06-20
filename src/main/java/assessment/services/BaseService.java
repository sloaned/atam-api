package assessment.services;

import assessment.services.interfaces.IBaseService;
import assessment.utilities.httpclient.HttpClient;
import assessment.utilities.httpclient.jsonparser.IJsonParser;
import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

/**
 * Abstract implementation of IBaseService
 *
 * @param <T> The type
 */
public abstract class BaseService<T> implements IBaseService<T> {

    protected Logger logger = LogManager.getLogger(this.getClass());

    //type, client, and parser for client need to be set in constructor of impl
    protected Class<T> type;
    protected HttpClient client;
    protected String url;

    public BaseService(String url, Class<T> type, IJsonParser parser) {
        this.url = url;
        this.type = type;
        client = new HttpClient(parser);
    }

    @Override
    public List<T> getAll() throws HttpException {
        return client.getAll(url + "?size=300000", type);
    }

    @Override
    public T get(String id) throws HttpException {
        return client.get(url + "/" + id, type);
    }

    @Override
    public T create(T obj) throws HttpException {
        return client.post(url, obj, type);
    }

    @Override
    public T update(String id, T obj) throws HttpException {
        //Need to make sure it exists otherwise mongo will create it with the id because mongo is the worst
        try {
            client.get(url + "/" + id, type);
        } catch (HttpException e) {
            logger.error(e);
            throw e;
        }
        return client.put(url + "/" + id, obj, type);
    }


    public void setClient(HttpClient client) {
        this.client = client;
    }
}
