package assessment.controllers;

import assessment.entities.*;
import assessment.services.TokenService;
import assessment.services.interfaces.ITokenApiService;
import assessment.utilities.Utils;
import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dsloane on 6/14/2016.
 */
@RestController
@RequestMapping(value = "/tokens")
public class TokenController extends BaseController<FCMTokenApi> {

    @Autowired
    public TokenController(ITokenApiService service) {
        super(service);
    }

    protected Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping(value="/delete/{userId}", method=RequestMethod.PUT)
    public ResponseEntity<Response> deleteUserToken(@PathVariable String userId, @RequestBody String token) {
        TokenService tokenService = new TokenService();
        try {
            Boolean response = tokenService.deleteUserToken(userId, token);
            return new ResponseEntity<>(new SuccessfulResponse<>(response), HttpStatus.OK);
        } catch (HttpException e) {
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    @RequestMapping(value="/addtoken/{userId}", method=RequestMethod.PUT)
    public ResponseEntity<Response> addTokenForUser(@PathVariable String userId, @RequestBody FCMTokenApi token) {
        TokenService tokenService = new TokenService();
        try {
            FCMTokenApi theToken = tokenService.addTokenForUser(userId, token);
            logger.debug("Updated " + token.getClass().getSimpleName() + ": " + token);
            logger.info("Updated a " + token.getClass().getSimpleName());
            return new ResponseEntity<>(new SuccessfulResponse<>(token), HttpStatus.OK);
        } catch (HttpException e) {
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

    @RequestMapping(value="/user/{id}", method= RequestMethod.GET)
    public ResponseEntity<Response> getTokensByUser(@PathVariable String id) {
        try {
            TokenService tokenService = new TokenService();
            FCMTokenApi token = tokenService.getTokenByUser(id);
            if (token == null) {
                System.out.println("Got null for token by user");
                logger.info("Got null for token by user");
            } else {
                System.out.println("got a token! " + token);
                logger.debug("Got token: " + token);
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(token), HttpStatus.OK);
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
