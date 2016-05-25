package assessment.entities;

/**
 * Response to be returned if call is successful
 * @param <T> The type
 */
public class SuccessfulResponse<T> extends Response {

    private T result;

    public SuccessfulResponse() {
        status = "success";
    }

    public SuccessfulResponse(T result) {
        this();
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
