package land.bruinkool.garzweiler.api.response;

import land.bruinkool.garzweiler.entity.Quote;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuoteResponse {
    private Integer id;
    private String text;
    private LocalDateTime created;

    public QuoteResponse(Quote quote) {
        this.id = quote.getId();
        this.text = quote.getText();
        this.created = quote.getCreated();
    }
}
