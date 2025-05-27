package com.ittory.infra.oauth.kakao;

import com.ittory.infra.oauth.exception.OAuthException.OAuthServerException;
import com.ittory.infra.oauth.exception.OAuthException.SocialMemberNoInfoException;
import com.ittory.infra.oauth.exception.OAuthException.UnauthorizedTokenException;
import com.ittory.infra.oauth.kakao.dto.KaKaoMemberInfo;
import com.ittory.infra.oauth.kakao.dto.KaKaoTokenResponse;
import com.ittory.infra.oauth.kakao.dto.MemberInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ittory.common.constant.TokenConstant.ACCESS_TOKEN_HEADER;
import static com.ittory.common.constant.TokenConstant.TOKEN_TYPER;

@Component
public class KaKaoPlatformClient {

    private static final String ACCESS_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String MEMBER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${kakao.clientId}")
    private String CLIENT_ID;

    @Value("${kakao.redirectUri}")
    private String REDIRECT_URI;

    @Value("${kakao.clientSecret}")
    private String CLIENT_SECRET;

    public KaKaoTokenResponse getKakaoAccessToken(String authorizationCode, String origin) {
        WebClient client = WebClient.create();

        Mono<KaKaoTokenResponse> kaKaoTokenResponseMono = client.post()
                .uri(ACCESS_TOKEN_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", GRANT_TYPE)
                        .with("client_id", CLIENT_ID)
                        .with("redirect_uri", origin + REDIRECT_URI)
                        .with("code", authorizationCode)
                        .with("client_secret", CLIENT_SECRET)
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new UnauthorizedTokenException(authorizationCode)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new OAuthServerException(ACCESS_TOKEN_URL)))
                .bodyToMono(KaKaoTokenResponse.class);

        return kaKaoTokenResponseMono.blockOptional().orElseThrow(SocialMemberNoInfoException::new);
    }

    public MemberInfo getMemberInfo(String accessToken) {
        WebClient client = WebClient.create();

        Mono<KaKaoMemberInfo> memberInfo = client.get()
                .uri(MEMBER_INFO_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(ACCESS_TOKEN_HEADER, TOKEN_TYPER + " " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new OAuthServerException(MEMBER_INFO_URL)))
                .bodyToMono(KaKaoMemberInfo.class);

        KaKaoMemberInfo kaKaoMemberInfo = memberInfo.blockOptional().orElseThrow(SocialMemberNoInfoException::new);

        return MemberInfo.from(kaKaoMemberInfo);
    }
}
