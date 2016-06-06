package assessment.services;

import assessment.entities.KudoApi;
import assessment.entities.kudo.Kudo;
import assessment.entities.user.User;
import assessment.services.interfaces.IKudoApiService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import assessment.utilities.httpclient.jsonparser.IJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hmccardell on 4/29/2016.
 */
@Service
public class KudoService extends BaseService<KudoApi> implements IKudoApiService {

    public KudoService() {
        super(UrlConstants.DATA_URL_KUDO, KudoApi.class, new DataJsonParser());
    }

    @Override
    public List<KudoApi> getAll() throws HttpException {

        ArrayList<KudoApi> kudos = new ArrayList<KudoApi>();

        List<Kudo> kudoList = client.getAll(url + "?size=3000000", Kudo.class);

        for (Kudo kudo : kudoList) {
            KudoApi kudoApi = new KudoApi();

            kudoApi.setId(kudo.getId());
            kudoApi.setComment(kudo.getComment());
            kudoApi.setReviewedId(kudo.getReviewedId());
            kudoApi.setSubmittedDate(kudo.getSubmittedDate());
            kudoApi.setVersion(kudo.getVersion());

            UserService userService = new UserService();
            kudoApi.setReviewer(userService.get(kudo.getReviewerId()));


            kudos.add(kudoApi);

        }
        return kudos;

    }


    public List<KudoApi> getKudosByUser(String id) throws HttpException {

        ArrayList<KudoApi> kudos = new ArrayList<KudoApi>();

        List<Kudo> kudoList = client.getAll(url + "?size=3000000", Kudo.class);

        for (Kudo kudo : kudoList) {

            if (kudo.getReviewedId().equals(id)) {

                KudoApi kudoApi = new KudoApi();

                kudoApi.setId(kudo.getId());
                kudoApi.setComment(kudo.getComment());
                kudoApi.setReviewedId(kudo.getReviewedId());
                kudoApi.setSubmittedDate(kudo.getSubmittedDate());
                kudoApi.setVersion(kudo.getVersion());

                UserService userService = new UserService();
                kudoApi.setReviewer(userService.get(kudo.getReviewerId()));


                kudos.add(kudoApi);
            }
        }
        return kudos;
    }

    @Override
    public KudoApi get(String id) throws HttpException {
        KudoApi kudoApi = new KudoApi();

        Kudo kudo = client.get(url + "/" + id, Kudo.class);

        kudoApi.setId(kudo.getId());
        kudoApi.setComment(kudo.getComment());
        kudoApi.setReviewedId(kudo.getReviewedId());
        kudoApi.setSubmittedDate(kudo.getSubmittedDate());
        kudoApi.setVersion(kudo.getVersion());

        UserService userService = new UserService();
        kudoApi.setReviewer(userService.get(kudo.getReviewerId()));

        return kudoApi;

    }

    @Override
    public KudoApi create(KudoApi kudoApi) throws HttpException {

        System.out.println("in kudoapi create, kudoApi reviewed id = " + kudoApi.getReviewedId());
        System.out.println("reviewer id = " + kudoApi.getReviewer().getId());
        System.out.println("comment = " + kudoApi.getComment());

        UserService userService = new UserService();
        User reviewed = userService.get(kudoApi.getReviewedId());
        User reviewer = userService.get(kudoApi.getReviewer().getId());

        System.out.println("The person being reviewed = " + reviewed.getFirstName() + " " + reviewed.getLastName());
        System.out.println("The reviewer = " + reviewer.getFirstName() + " " + reviewer.getLastName());

        Kudo kudo = new Kudo(kudoApi.getReviewer().getId(), kudoApi.getReviewedId(), kudoApi.getComment(), new Date());
        Kudo returnKudo = client.post(url, kudo, Kudo.class);

        System.out.println("returnKudo = " + returnKudo);
        System.out.println("returnKudo reviewerId = " + returnKudo.getReviewerId());
        System.out.println("returnKudo reviewedId = " + returnKudo.getReviewedId());
        System.out.println("returnKudo  date = " + returnKudo.getSubmittedDate());
        System.out.println("returnKudo comment = " + returnKudo.getComment());

        return kudoApi;
      /*  if (returnKudo == kudo) {
            return kudoApi;
        }
        return null; */
    }

    @Override
    public KudoApi update(String id, KudoApi kudoApi) throws HttpException {
        //Need to make sure it exists otherwise mongo will create it with the id because mongo is the worst
        try {
            client.get(url + "/" + id, type);
        } catch (HttpException e) {
            logger.error(e);
            throw e;
        }
        Kudo kudo = new Kudo(kudoApi.getReviewer().getId(), kudoApi.getReviewedId(), kudoApi.getComment(), kudoApi.getSubmittedDate());
        Kudo returnKudo = client.post(url, kudo, Kudo.class);

        if (returnKudo == kudo) {
            return kudoApi;
        }
        return null;
    }
}
