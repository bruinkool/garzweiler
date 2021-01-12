package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.request.QuoteRequest;
import land.bruinkool.garzweiler.api.response.QuoteResponse;
import land.bruinkool.garzweiler.entity.Quote;
import land.bruinkool.garzweiler.repository.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {
    @Mock
    private QuoteRepository quoteRepository;
    private QuoteController quoteController;

    @BeforeEach
    void setUp() {
        quoteController = new QuoteController(quoteRepository);
    }

    @Test
    void listQuotes() {
        List<Quote> quotes = Arrays.asList(new Quote("test"), new Quote("other quote"));
        Mockito.when(quoteRepository.findAll()).thenReturn(quotes);

        assertEquals(
                ResponseEntity.ok(quotes.stream().map(QuoteResponse::new).collect(Collectors.toList())),
                quoteController.listQuotes()
        );
    }

    @Test
    void getQuote() {
        Quote quote = new Quote("Do you know what is also beautiful about Rotterdam?");
        assertEquals(
                ResponseEntity.ok(new QuoteResponse(quote)),
                quoteController.getQuote(Optional.of(quote))
        );
    }

    @Test
    void getQuoteNotFound() {
        assertEquals(
                HttpStatus.NOT_FOUND,
                assertThrows(
                        ResponseStatusException.class,
                        () -> quoteController.getQuote(Optional.empty())
                ).getStatus()
        );
    }

    @Test
    void addQuote() {
        String quoteText = "..is that you can just throw a pan of noodles out the window!";
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setText(quoteText);

        Quote expected = new Quote(quoteText);
        expected.setId(2);

        Mockito.when(quoteRepository.save(Mockito.any())).thenReturn(expected);

        assertEquals(
                ResponseEntity.ok(new QuoteResponse(expected)),
                quoteController.addQuote(quoteRequest)
        );
    }

    @Test
    void deleteQuote() {
        Quote quote = new Quote("* throws pan of noodles *");

        assertEquals(
                ResponseEntity.noContent().build(),
                quoteController.deleteQuote(Optional.of(quote))
        );
    }

    @Test
    void deleteQuoteNotFound() {
        assertEquals(
                HttpStatus.NOT_FOUND,
                assertThrows(
                        ResponseStatusException.class,
                        () -> quoteController.deleteQuote(Optional.empty())
                ).getStatus()
        );
    }
}
