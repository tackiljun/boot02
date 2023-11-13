package tack.project.boot02.service;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class SocialServiceImpl implements  SocialService {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Value("${org.zerock.kakao.token_url}")
    private String tokenURL;
    @Value("${org.zerock.kakao.rest_key}")
    private String clientId;
    @Value("${org.zerock.kakao.redirect_uri}")
    private String redirectURI;
    @Value("${org.zerock.kakao.get_user}")
    private String getUser;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getKakaoEmail(String authCode) {

        log.info("==============================");
        log.info("authCode: " + authCode);
        log.info(tokenURL);
        log.info(clientId);
        log.info(redirectURI);
        log.info(getUser);

        String accessToken = getAccessToken(authCode);

        log.info("==========accessToken==========: " + accessToken);

        String email = getEmailFromAccessToken(accessToken);

        log.info("==========email==========: " + email);

        return email;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // accessToken을 가져오는 메소드.
    private String getAccessToken(String authCode) {

        String accessToken = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // react API의 param을 쿼리param으로 바꿔주는것.
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenURL)
        .queryParam("grant_type","authorization_code")
        .queryParam("client_id",clientId)
        .queryParam("redirect_uri", redirectURI)
        .queryParam("code", authCode)
        .build(true);

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
            uriComponents.toString(), HttpMethod.POST, entity, LinkedHashMap.class);

        log.info("----------==========----------");
        log.info(response);

        LinkedHashMap<String, String> bodyMap = response.getBody();

        accessToken = bodyMap.get("access_token");

        log.info("Access Token: " + accessToken);

        return accessToken;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String getEmailFromAccessToken(String accessToken) {

        if(accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(getUser).build();

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
            uriBuilder.toString(),
            HttpMethod.GET,
            entity,
            LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("----------==========----------");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");

    }
    
}
