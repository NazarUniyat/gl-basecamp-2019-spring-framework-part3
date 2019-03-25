package owu.wire;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GameResponse {
    public String result;
    public int guess;
}
