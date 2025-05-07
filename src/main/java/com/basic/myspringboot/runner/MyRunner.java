package com.basic.myspringboot.runner;


import com.basic.myspringboot.config.CustomerVO;
import com.basic.myspringboot.property.MyBootProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
//@Slf4j
public class MyRunner implements ApplicationRunner {

    @Value("${myboot.name}")
    private String name;

    @Value("${myboot.age}")
    private int age;

    @Autowired
    private Environment environment;

    @Autowired
    MyBootProperties myBootProperties;

    // profile 어노테이션 데이터 확인하기
    @Autowired
    CustomerVO customerVO;

    private Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Logger 구현체 => " + logger.getClass().getName());
        logger.debug("${myboot.name} = {}", name);
        logger.debug("${myboot.age} = {}", age);

        // Environment 사용해서 application.properties 값 가져오기
        logger.debug("env ${myboot.fullName} = {}", environment.getProperty("myboot.fullnames"));

        // ConfigurationProperties 어노테이션 사용해서 properties 값 가져오기
        logger.info("MyBootProperties getName() = {}", myBootProperties.getName());
        logger.info("MyBootProperties getAge() = {}", myBootProperties.getAge());

        logger.info("설정된 Port 번호 = {}", environment.getProperty("local.server.port"));

        // profile 어노테이션 데이터 확인하기
        logger.info("현재 활성화된 CustomerVO Bean = {}", customerVO);

        // foo 라는 VM 아규먼트 있는지 확인
        logger.debug("VM 아규먼트 foo : {}", args.containsOption("foo"));
        // bar라는 Program 아규먼트 있는지 확인
        logger.debug("Program 아규먼트 bar : {}", args.containsOption("bar"));

        /*
            Iterable forEach(consumer)
            Consumer은 함수형 인터페이스 void accept(T t)
            Consumer의 추상 메서드를 오버라이딩 하는 구문을 람다식으로 작성
         */
        // 아규먼트 목록 출력
        args.getOptionNames() // Set<String>
                .forEach(name -> System.out.println(name));

    }
}
