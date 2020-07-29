package ar.com.jlv.api.user.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE = "Unexpected error";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public ErrorDTO() {
        super();
        timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        if (Objects.nonNull(this.message)) {
            return this.message;
        }
        return DEFAULT_MESSAGE;
    }

}
