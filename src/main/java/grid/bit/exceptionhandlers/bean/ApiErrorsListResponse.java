package grid.bit.exceptionhandlers.bean;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * модель объекта, который будет аккумулировать список ошибок,
 * в случае отправки клиенту.
 */
public class ApiErrorsListResponse {

    /**
     *    http status code (код статуса обработки HTTP-запроса)
     */
    private HttpStatus status;


    /**
     *     Список Пользовательских сообщений об ошибке, который вы
     *   отравляете клиенту вашего API
     */
    private List<String> errorMessage;

    public ApiErrorsListResponse() {
    }

    private ApiErrorsListResponse(Builder builder) {
        setStatus(builder.status);
        setErrorMessage(builder.errorMessage);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ApiErrorsListResponse{" +
                "status=" + status +
                ", errorMessage=" + errorMessage +
                '}';
    }


    public static final class Builder {
        private HttpStatus status;
        private List<String> errorMessage;

        private Builder() {
        }

        public Builder status(HttpStatus val) {
            status = val;
            return this;
        }

        public Builder errorMessage(List<String> val) {
            errorMessage = val;
            return this;
        }

        public ApiErrorsListResponse build() {
            return new ApiErrorsListResponse(this);
        }
    }
}
