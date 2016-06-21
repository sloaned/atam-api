package assessment.services;

import assessment.entities.Period;
import assessment.services.interfaces.IBaseService;
import assessment.services.interfaces.IPeriodService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.springframework.stereotype.Service;

/**
 * Created by dsloane on 6/20/2016.
 */
@Service
public class PeriodService extends BaseService<Period> implements IPeriodService {

    public PeriodService() {
        super(UrlConstants.DATA_URL_PERIODS, Period.class, new DataJsonParser());
    }
}
