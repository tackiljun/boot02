package tack.project.boot02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import tack.project.boot02.controller.interceptor.JWTInterceptor;


@Log4j2
@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {

    //////////////////////////////////////////////////////////////////////////////////////
    private final JWTInterceptor jwtInterceptor;

    //////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/member/login", "/api/member/refresh");

    }
    
}
