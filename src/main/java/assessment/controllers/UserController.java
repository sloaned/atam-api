package assessment.controllers;

import assessment.entities.Profile;
import assessment.entities.Response;
import assessment.entities.SuccessfulResponse;
import assessment.entities.user.User;
import assessment.services.UserService;
import assessment.services.interfaces.IUserService;
import com.google.common.collect.ObjectArrays;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController<User> {

    @Autowired
    ResourceServerTokenServices tokenServices;

    @Autowired
    public UserController(IUserService service) {
        super(service);
    }

    @RequestMapping(value="/{id}/profile", method=RequestMethod.GET)
    public ResponseEntity<Response> getProfile(@PathVariable String id) {
        try {
            UserService userService = new UserService();

            Profile profile = userService.getProfile(id);
            if (profile == null) {
                System.out.println("error getting current user");
            } else {
                System.out.println("profile = " + profile);
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(profile), HttpStatus.OK);
        } catch (HttpException e) {
            System.out.println("HttpException error: " + e);
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }


    @RequestMapping(value="/current", method= RequestMethod.GET)
    public ResponseEntity<Response> getCurrentUser(Principal principal) {

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)( (OAuth2Authentication) principal).getDetails();
        System.out.println(details.getTokenValue());

        HashMap userDetails = (HashMap) tokenServices.readAccessToken(details.getTokenValue()).getAdditionalInformation().get("userDetails");
        System.out.println("userDetails: " + userDetails.toString());
        String email;
        String username = (userDetails.get("userName")).toString();
        if (username.equals("atamldap")) {
            email = "dsloane@catalystdevworks.com";
        } else {
            email = (userDetails.get("email")).toString();
        }
       // String email = (userDetails.get("email")).toString();

        System.out.println("email = " + email);

        try {
            UserService userService = new UserService();


            User user = userService.getUserByEmail(email);
            if (user == null) {
                System.out.println("error getting current user");
            } else {
                System.out.println("user = " + user);
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(user), HttpStatus.OK);
        } catch (HttpException e) {
            System.out.println("HttpException error: " + e);
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

}
