package grid.bit.exceptionhandlers.advices;

import grid.bit.exceptionhandlers.bean.ApiErrorResponse;
import grid.bit.exceptionhandlers.bean.ApiErrorsListResponse;
import grid.bit.exceptionhandlers.exceptions.IdConstraintExceptions;
import grid.bit.exceptionhandlers.exceptions.StringConstraintExceptions;
import grid.bit.exceptionhandlers.exceptions.ValueForCellException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Данный класс будет перехватывать exceptions, который возникают во время обработки данных
 */
@ControllerAdvice
public class CommonRestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Обработка исключиения типа ViolationException, которое бросается, когда происходит
     * откат транзакции, в случае неудачной работы с базой данных.
     *
     * @param ex - объект типа DataIntegrityViolationException, который является оболочкой для вложенного ViolationException
     *           данный параметр передается методу, когда происходит перехват исключения
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException ex) {
        logger.info(ex.getClass().getName());

        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {

            ConstraintViolationException consEx = (ConstraintViolationException) cause;

            final ApiErrorResponse apiError = ApiErrorResponse.newBuilder()
                    .message(consEx.getLocalizedMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
        }

        final ApiErrorResponse apiError = ApiErrorResponse.newBuilder()
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * один из вариантов обработки ViolationException
     */

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Object());
    }


    @ExceptionHandler(StringConstraintExceptions.class)
    protected ResponseEntity<ApiErrorResponse> handleExceptionsConstraintStr(StringConstraintExceptions e) {

        final ApiErrorResponse apiError = ApiErrorResponse.newBuilder()
                .message(e.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }



    @ExceptionHandler(ValueForCellException.class)
    protected ResponseEntity<ApiErrorResponse> handleValueConstraintCellSize(ValueForCellException e) {

        final ApiErrorResponse apiError = ApiErrorResponse.newBuilder()
                .message(e.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(IdConstraintExceptions.class)
    protected ResponseEntity<ApiErrorResponse> handleExceptionsConstraintId(IdConstraintExceptions e) {

        final ApiErrorResponse apiError = ApiErrorResponse.newBuilder()
                .message(e.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        // let's get all validation error and send it across
        List<String> errorMsg = ex.getBindingResult()
                .getFieldErrors().
                        stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiErrorsListResponse response = ApiErrorsListResponse.newBuilder()
                .status(HttpStatus.NOT_ACCEPTABLE)
                .errorMessage(errorMsg)
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }


    /**
     * обработка exception, который возникает на уровне dao, когда просходит попытка сохранения данных.
     * Если какие-то поля не соответсвтуют установленным ограничениями, тогда формируются сообщения
     *
     * @param ex - объект типа RollbackException, который появляется в момент ошибки выполнения транзакции
     * @return - возвращается объект, который затем будет использован для формирования response клиенту,
     * в случае ошибки
     */
    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<ApiErrorsListResponse> handleNotValidException(RollbackException ex) {

        String errMessage = ex.getCause().getMessage();

        List<String> listErrMessage = getListErrMessage(errMessage);
        ApiErrorsListResponse response = ApiErrorsListResponse.newBuilder()
                .status(HttpStatus.NOT_ACCEPTABLE)
                .errorMessage(listErrMessage)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);

    }


    private List<String> getListErrMessage(String msg) {

        Stream<String> stream = Arrays.stream(msg.split("\n"))
                .filter(s -> s.contains("\t"))
                .map(s -> s.replaceAll("^([^{]+)\\{", ""))
                .map(s -> s.replaceAll("[\"]", ""))
                .map(s -> s.replaceAll("=", ":"))
                .map(s -> s.replaceAll("interpolatedMessage", "message"))
                .map(s -> s.replaceAll("\\{|}(, *)?", ""));

        return stream.collect(Collectors.toList());
    }

}


