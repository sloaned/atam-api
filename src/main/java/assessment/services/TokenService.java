package assessment.services;

import assessment.entities.FCMToken;
import assessment.entities.FCMTokenApi;
import assessment.services.interfaces.ITokenApiService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 6/14/2016.
 */
@Service
public class TokenService extends BaseService<FCMTokenApi> implements ITokenApiService {

    public TokenService() {
        super(UrlConstants.TOKEN_URL_TOKENS, FCMTokenApi.class, new DataJsonParser());
    }

    public FCMTokenApi getTokenByUser(String id) throws HttpException {

        ArrayList<FCMToken> tokens = new ArrayList<FCMToken>();

        List<FCMToken> tokenList = client.getAll(url + "?size=3000000", FCMToken.class);

        for (FCMToken token: tokenList) {

            if (token.getUserId().equals(id)) {

                FCMTokenApi returnToken = new FCMTokenApi(id, token.getTokens());
                return returnToken;

            }
        }
        return null;
    }

    public FCMTokenApi addTokenForUser(String userId, FCMTokenApi token) throws HttpException {
       // FCMTokenApi userToken = getTokenByUser(userId);
        FCMToken userToken = null;
        ArrayList<FCMToken> tokens = new ArrayList<FCMToken>();

        List<FCMToken> tokenList = client.getAll(url + "?size=3000000", FCMToken.class);

        for (FCMToken ft: tokenList) {
            if (ft.getUserId().equals(userId)) {
                userToken = ft;
            }
        }

        // user has no fcm tokens yet, create one
        if (userToken == null) {
            System.out.println("userToken was null, creating new FCMToken");
            FCMToken newToken = new FCMToken(userId, token.getTokens());
            FCMToken returnToken = client.post(url, newToken, FCMToken.class);

            return token;
        }
        // update existing token

        List<String> currentTokens = userToken.getTokens();
        for (String s : token.getTokens()) {
            currentTokens.add(s);
        }
        userToken.setTokens(currentTokens);

        FCMToken returnToken = client.put(url + "/" + userToken.getId(), userToken, FCMToken.class);

        return token;
    }

    public Boolean deleteUserToken(String userId, String token) throws HttpException {
        FCMToken userToken = null;
        ArrayList<FCMToken> tokens = new ArrayList<FCMToken>();

        List<FCMToken> tokenList = client.getAll(url + "?size=3000000", FCMToken.class);

        for (FCMToken ft: tokenList) {
            if (ft.getUserId().equals(userId)) {
                userToken = ft;
            }
        }

        // user has no fcm tokens yet, and yet a delete request references them,
        // so this shouldn't happen
        if (userToken == null) {
            return true;
        }
        // update existing token

        List<String> currentTokens = userToken.getTokens();
        List<String> newTokens = new ArrayList<String>();
        for (String s : currentTokens) {
            if (!s.equals(token)) {
                newTokens.add(s);
            }
        }
        userToken.setTokens(newTokens);

        FCMToken returnToken = client.put(url + "/" + userToken.getId(), userToken, FCMToken.class);

        return true;
    }
}
