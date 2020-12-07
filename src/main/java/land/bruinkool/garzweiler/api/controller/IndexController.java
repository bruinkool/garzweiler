package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.response.IndexResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexController {
    @GetMapping("/")
    @io.swagger.v3.oas.annotations.Operation(tags = {"app"})
    public ResponseEntity<IndexResponse> apiStatus(HttpServletRequest request) throws UnknownHostException {
        return ResponseEntity.ok(new IndexResponse(true, InetAddress.getLocalHost().getHostName()));
    }
}
