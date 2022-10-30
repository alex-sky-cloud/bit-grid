package grid.bit.exceptionhandlers.bean;

import org.springframework.http.HttpStatus;


/**
 * модель объекта, который будет аккумулировать ошибку,
 * в случае отправки клиенту.
 */
public class ApiErrorResponse {

    /**
     *    http status code (код статуса обработки HTTP-запроса)
     */
    private HttpStatus status;

    /**
     * Пользовательское сообщение об ошибке, который вы
     * отравляете клиенту вашего API*/
    private String message;

    private ApiErrorResponse(Builder builder) {
        setStatus(builder.status);
        setMessage(builder.message);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }


    public static final class Builder {
        private HttpStatus status;
        private String message;

        private Builder() {
        }

        public Builder status(HttpStatus val) {
            status = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this);
        }
    }
}
