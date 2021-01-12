package land.bruinkool.garzweiler.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class QuoteRequest {
    @NotBlank(message = "Please enter a quote")
    @Size(min = 3, max = 250)
    private String text;
}
