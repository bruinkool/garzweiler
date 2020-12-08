package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.response.IndexResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    void apiStatus() throws UnknownHostException {
        assertEquals(
                ResponseEntity.ok(new IndexResponse(true, InetAddress.getLocalHost().getHostName())),
                indexController.apiStatus()
        );
    }
}