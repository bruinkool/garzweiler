package land.bruinkool.garzweiler.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import land.bruinkool.garzweiler.api.request.QuoteRequest;
import land.bruinkool.garzweiler.api.response.QuoteResponse;
import land.bruinkool.garzweiler.entity.Quote;
import land.bruinkool.garzweiler.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "quotes")
@RequestMapping(
        value = "/api/v1/quotes",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class QuoteController {
    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteController(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<QuoteResponse>> listQuotes() {
        return ResponseEntity.ok(quoteRepository.findAll()
                .stream()
                .map(QuoteResponse::new)
                .collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    public ResponseEntity<QuoteResponse> getQuote(
            @PathVariable("id") @Parameter(schema = @Schema(type = "integer")) Optional<Quote> quote
    ) {
        return ResponseEntity.ok(new QuoteResponse(quote.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        )));
    }

    @PostMapping("/")
    public ResponseEntity<QuoteResponse> addQuote(@Valid @RequestBody QuoteRequest quoteRequest) {
        Quote quote = new Quote(quoteRequest.getText());
        return ResponseEntity.ok(new QuoteResponse(quoteRepository.save(quote)));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteQuote(
            @PathVariable("id") @Parameter(schema = @Schema(type = "integer")) Optional<Quote> quote
    ) {
        quoteRepository.delete(quote.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ));
        return ResponseEntity.noContent().build();
    }
}
