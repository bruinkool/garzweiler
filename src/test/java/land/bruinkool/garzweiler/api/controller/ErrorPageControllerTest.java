package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorPageControllerTest {
    private final ErrorPageController controller = new ErrorPageController();

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getErrorPath() {
        assertEquals("/error", controller.getErrorPath());
    }

    @Test
    void handleError() {
        Mockito.when(mockRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn("404");
        assertEquals(
                ResponseEntity.status(404).body(new ErrorResponse(404)),
                controller.handleError(mockRequest)
        );
    }

    @Test
    void handleErrorNullAttribute() {
        assertEquals(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500)),
                controller.handleError(mockRequest)
        );
    }
}