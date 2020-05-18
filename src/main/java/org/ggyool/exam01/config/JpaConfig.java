package org.ggyool.exam01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
// 스프링 부트 자동 설정, bean 읽기와 생성 자동 설정
// 이 클래스는 프로젝트의 최상단에 위치해야 함
public class JpaConfig {
}
