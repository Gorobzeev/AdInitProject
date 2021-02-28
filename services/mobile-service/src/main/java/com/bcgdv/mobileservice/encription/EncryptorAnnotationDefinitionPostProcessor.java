package com.bcgdv.mobileservice.encription;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class EncryptorAnnotationDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor {

    private static final String ENCRYPTION_KEY_INJECTOR = "encryptionKeyInjector";

    private String valueForReplace = "";
    private String packageForScan = "";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String beanName : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() == null) {
                continue;
            }

            try {
                Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                if (beanClass.isAnnotationPresent(CreateBeanBefore.class)) {
                    String[] dependantBeanNames = beanClass.getAnnotation(CreateBeanBefore.class).value();
                    for (String dependantBeanName : dependantBeanNames) {
                        BeanDefinition dependantBeanDefinition = registry.getBeanDefinition(dependantBeanName);
                        dependantBeanDefinition.setDependsOn(beanName);
                    }
                }
                if (beanClass.isAnnotationPresent(EnableEncryptInjection.class)) {
                    valueForReplace = beanClass.getAnnotation(EnableEncryptInjection.class).valueForReplace();
                    packageForScan = beanClass.getAnnotation(EnableEncryptInjection.class).packageForScan();
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ENCRYPTION_KEY_INJECTOR.equalsIgnoreCase(beanName)) {
            Class<?> aClass = bean.getClass();
            try {
                Field fieldValueForReplace = aClass.getDeclaredField("valueForReplace");
                fieldValueForReplace.setAccessible(true);
                fieldValueForReplace.set(bean, valueForReplace);

                Field fieldPackageForScan = aClass.getDeclaredField("packageForScan");
                fieldPackageForScan.setAccessible(true);
                fieldPackageForScan.set(bean, packageForScan);
            } catch (Exception e) {
                throw new RuntimeException("Can not inject 'valueForReplace' and 'packageForScan' value to encryptionKeyInjector.");
            }
        }
        return bean;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
