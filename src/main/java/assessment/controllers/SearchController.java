package assessment.controllers;

import assessment.entities.FailedResponse;
import assessment.entities.Response;
import assessment.entities.SearchResult;
import assessment.entities.SuccessfulResponse;
import assessment.services.SearchService;
import assessment.services.interfaces.ISearchService;
import assessment.utilities.Utils;
import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dsloane on 6/1/2016.
 */

@RestController
@RequestMapping(value = "search")
public class SearchController {

    @Autowired
    protected ISearchService service;

    protected Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping(value="/{searchterm}", method= RequestMethod.GET)
    public ResponseEntity<Response> search(@PathVariable String searchterm) {
        try {
            List<SearchResult> results = service.search(searchterm);
            if (results.isEmpty()) {
                System.out.println("got no results");
                logger.info("got no search results");
            } else {
                System.out.println("got results! " + results);
                logger.debug("Got results: " + results);
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(results), HttpStatus.OK);
        } catch (HttpException e) {
            System.out.println("HttpException error: " + e);
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
