package com.thiru.configuration;

/*
 * package com.thiru.configuration;
 * 
 *   here   we can use @profle annotation for to setting the  diff env  for our requirement  
 *  
 *  or
 *  
 *  directly we can set the env in application.properties file : spring.profiles.active=dev or spring.profiles.active=prod


 * import javax.sql.DataSource;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Profile; import
 * org.springframework.jdbc.datasource.DriverManagerDataSource;
 * 
 * @Configuration public class ProfilesDataSourceConfig {
 * 
 * @Bean
 * 
 * @Profile("dev") public DataSource h2DataSource() { DriverManagerDataSource
 * dataSource = new DriverManagerDataSource();
 * dataSource.setDriverClassName("org.h2.Driver");
 * dataSource.setUrl("jdbc:h2:mem:studentdb"); dataSource.setUsername("thiru");
 * dataSource.setPassword("thiru@9505"); return dataSource; }
 * 
 * @Bean
 * 
 * @Profile("prod") public DataSource mysqlDataSource() {
 * DriverManagerDataSource dataSource = new DriverManagerDataSource();
 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
 * dataSource.setUrl("jdbc:mysql://localhost:3306/studentdb");
 * dataSource.setUsername("thiru"); dataSource.setPassword("thiru@9505");
 * return dataSource; } }
 */