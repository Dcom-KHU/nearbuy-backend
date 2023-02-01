package dcom.nearbuybackend.api.global.security.config;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
public class Token {

    private String accessToken;
    private String refreshToken;

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
