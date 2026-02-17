package cl.home.erpfinanzas.web.response;

public class ApiResponse<T> {

    private Status status;
    private boolean result;
    private T data;
    private ApiError error;

    public ApiResponse() {
    }

    public ApiResponse(Status status, boolean result, T data, ApiError error) {
        this.status = status;
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }
}
