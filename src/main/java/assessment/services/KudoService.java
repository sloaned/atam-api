package assessment.services;

import assessment.entities.kudo.Kudo;
import assessment.services.interfaces.IKudoService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import assessment.utilities.httpclient.jsonparser.IJsonParser;
import org.springframework.stereotype.Service;

/**
 * Created by hmccardell on 4/29/2016.
 */
@Service
public class KudoService extends BaseService<Kudo> implements IKudoService {

    public KudoService() {
        super(UrlConstants.DATA_URL_KUDO, Kudo.class, new DataJsonParser());
    }
}
