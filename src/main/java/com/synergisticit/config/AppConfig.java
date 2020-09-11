package com.synergisticit.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.synergisticit.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import sun.tools.java.Environment;

@Configuration
@PropertySource(value = {"classpath:db.properties"})
@ComponentScan(basePackages = {"com.synergisticit"})
@EnableJpaRepositories(basePackages = {"com.synergisticit.repository"})
@EnableAspectJAutoProxy
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	Environment env;
	
	@Bean
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("driver"));
		dataSource.setUsername(env.getProperty("dbUsername"));
		dataSource.setPassword(env.getProperty("dbPassword"));
		
		return dataSource;
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("com.synergisticit.domain");				//scan packages that have @Entity
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setJpaProperties(jpaProperties());
		
		return entityManagerFactory;
	}
	
	public Properties jpaProperties() {
		
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.show_SQL", "true");
		
		return jpaProperties;
	}
	
	
	@Bean
	@Qualifier(value = "transactionManager")
	public JpaTransactionManager jpaTransactionManager() {
		
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(dataSource());
		
		return jpaTransactionManager;
	}
	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager() {
		
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setDataSource(dataSource());
		hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
		
		return hibernateTransactionManager;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setAnnotatedClasses(User.class, Role.class);
		sessionFactory.setPackagesToScan("com.synergisticit.domain");
		sessionFactory.setHibernateProperties(jpaProperties());
		
		return sessionFactory;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		
		return viewResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages/message");
		
		return messageSource;
	}
	
	@Bean
	public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
		
		return new Jaxb2RootElementHttpMessageConverter();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		
		return new MappingJackson2HttpMessageConverter();
	}
	
	@Bean
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {
		
		return new MappingJackson2XmlHttpMessageConverter();
	}
	
	@Override	// useful when you want to use the img and css in your web pages but you are not using spring boot
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public JavaMailSender mailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		//mailSender.setUsername();
		//mailSender.setPassword();
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.debug", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
}














