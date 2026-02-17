package cl.home.erpfinanzas.web.response;

import org.springframework.http.HttpStatus;

public class ResponseFactory {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                new Status(String.valueOf(HttpStatus.OK.value()), "Respuesta exitosa"),
                true,
                data,
                null
        );
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(
                new Status(String.valueOf(status.value()), status.getReasonPhrase()),
                false,
                null,
                new ApiError(message)
        );
    }
}
