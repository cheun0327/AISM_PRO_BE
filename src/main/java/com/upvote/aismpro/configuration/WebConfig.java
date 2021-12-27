package com.upvote.aismpro.configuration;

import com.upvote.aismpro.interceptor.HttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/swagger-ui/index.html");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOrigins("http://localhost:3000", "http://141.164.62.192:3000","http://141.164.62.192:80")
//                .allowCredentials(true);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new HttpRequestInterceptor());
//    }
}
