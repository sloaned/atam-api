package assessment.entities;

/**
 * Generic response class
 */
public abstract class Response {

    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
