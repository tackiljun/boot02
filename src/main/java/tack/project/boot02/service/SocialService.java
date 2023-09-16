package tack.project.boot02.service;

import jakarta.transaction.Transactional;


@Transactional
public interface SocialService {

    //////////////////////////////////////
    String getKakaoEmail(String authCode);
    
}
