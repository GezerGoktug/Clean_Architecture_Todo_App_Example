package com.todoapp.infrastructure.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

@Configuration
public class CustomBeanRegistrationConfig {

    @Bean
    public static BeanFactoryPostProcessor customBeanFactoryPostProcessor(ApplicationContext context) {
        return beanFactory -> {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((AnnotationConfigServletWebServerApplicationContext) context)
                    .getBeanFactory();
            registerCustomBeans(registry);
        };
    }

    private static void registerCustomBeans(BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        scanner.addIncludeFilter(includeUseCaseInteractorFactoryFilter());
        scanner.scan("com.todoapp"); // <- senin package'ını doğru ver
    }

    private static TypeFilter includeUseCaseInteractorFactoryFilter() {
        return (MetadataReader mr, MetadataReaderFactory mrf) -> {
            String className = mr.getClassMetadata().getClassName();
            return className.endsWith("Interactor") ||
                    className.endsWith("UseCaseImpl") ||
                    className.endsWith("FactoryImpl");
        };
    }
}
