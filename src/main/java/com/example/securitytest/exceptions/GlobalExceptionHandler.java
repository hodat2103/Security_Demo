package com.example.securitytest.exceptions;
import com.example.securitytest.dtos.response.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
/**
 * GlobalExceptionHandler noi xu ly chung cac van de lien quan den exception va tra ve cac ma loi, thong diep cho frontend.
 * @ControllerAdvice  Neu co exception bat cu dau thi bat lai se
 * redirect toi ExceptionHandler de xu ly voi tinh huong tuong ung
 * response (http code, message) tuong ung voi tung ket qua phu hop
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // xu ly ngoai le voi DataNotFoundException
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseError> handleDataNotFoundException(DataNotFoundException ex) {
        ResponseError response = new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.error("Error 404 - Url not found or data failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // xu ly ngoai le voi InvalidParamException
    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ResponseError> handleInvalidParamException(InvalidParamException ex) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        log.error("Error 400 - invalid param exception{}: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // xu ly ngoai le voi MethodArgumentNotValidException cho viec validate du lieu
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.error("Error 400 method agrument not valid exception");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    /**
     * xu ly ngoai le voi GeneralSecurityException cho viec ma hoa, giai ma hay cac thao tac lien quan den qua trinh bao mat
     * @param  ex GeneralSecurityException
     * @return return ma loi 400
     */
    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<ResponseError> handleGeneralSecurityException(GeneralSecurityException ex) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        log.error("Error 400 - Decryption failed:{}: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * xu ly ngoai le voi chua ro rang
     * @param ex RuntimeException
     * @return ma loi 500 tu phia server va tra ve thong diep tai sao nem ra exception
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntimeException(RuntimeException ex) {
        ResponseError response = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "A server error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(XSSServletException.class)
    public ResponseError handleSQLException(HttpServletRequest request,
                                            Exception ex){
        ResponseError errorResponse = new ResponseError(HttpStatus.FORBIDDEN.value(),ex.getMessage());
//
//        errorResponse.setStatus();
//        errorResponse.setMessage();

        return errorResponse;
    }
}

