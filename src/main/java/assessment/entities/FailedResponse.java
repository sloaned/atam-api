package assessment.entities;

import org.springframework.http.HttpStatus;

/**
 * Created by jjacobson on 4/12/16.
 */
public class FailedResponse extends Response {

    private int error;
    private String message;

    public FailedResponse() {
        status = "fail";
    }

    public FailedResponse(HttpStatus error) {
        this();
        this.error = error.value();
        this.message = error.getReasonPhrase();
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
