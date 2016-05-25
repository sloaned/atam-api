package assessment.utilities;

import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String errorCodeRegex = "^Error: (\\d+)";

    public static HttpStatus getHttpErrorStatus(String message) {
        Pattern pattern = Pattern.compile(errorCodeRegex);
        Matcher matcher = pattern.matcher(message);

        int code = -1;
        //apparently without the for loop it breaks
        while (matcher.find()) {
            code = Integer.parseInt(matcher.group(1));
        }
        return HttpStatus.valueOf(code);
    }
}
