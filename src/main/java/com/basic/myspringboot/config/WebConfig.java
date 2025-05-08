package com.basic.myspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
 @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
  registry.addResourceHandler("/mobile/**")
          //반드시 mobile 다음에 / 을 주어야 한다.
          // target/classes/mobile이 있기 때문에 classpath:를 적어준다
          // 디렉토리의 위치를 알려주는 것
          .addResourceLocations("classpath:/mobile/")
          .setCachePeriod(20);//20초
 }
}