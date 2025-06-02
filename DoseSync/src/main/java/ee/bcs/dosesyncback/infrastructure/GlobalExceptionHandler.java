//package ee.bcs.dosesyncback.infrastructure;
//
//import ee.bcs.dosesyncback.infrastructure.error.ApiError;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//public class GlobalExceptionHandler  {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        String errorMessage = ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
//        ApiError apiError = new ApiError();
//        apiError.setStatus(HttpStatus.BAD_REQUEST);
//        apiError.setErrorCode(HttpStatus.BAD_REQUEST.value());
//        apiError.setMessage(errorMessage);
//        apiError.setPath(request.getRequestURI());
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//}
