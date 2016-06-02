package assessment.services.interfaces;

import assessment.entities.SearchResult;
import org.apache.http.HttpException;

import java.util.List;

/**
 * Created by dsloane on 6/1/2016.
 */
public interface ISearchService {

    List<SearchResult> search(String searchTerm) throws HttpException;
}
