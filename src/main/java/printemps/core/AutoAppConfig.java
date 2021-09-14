package printemps.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 탐색 위치 지정
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class,
        // 자동 스캔 대상 필터(AppConfig.java 제외)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {


}
