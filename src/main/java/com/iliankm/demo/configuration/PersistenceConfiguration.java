package com.iliankm.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.iliankm.demo.entity.repository")
class PersistenceConfiguration {
}
