package land.bruinkool.garzweiler.api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import land.bruinkool.garzweiler.api.response.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
@Profile("!dev")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ErrorPageController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    @Hidden
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            return ResponseEntity.status(statusCode).body(new ErrorResponse(statusCode));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500));
    }
}
