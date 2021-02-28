package com.bcgdv.mobileservice.encription;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

@Component("encryptionKeyInjector")
@CreateBeanBefore(value = {"entityManagerFactory"})
@Log4j2
public class EncryptionKeyInjector {

    private String valueForReplace = "";
    private String packageForScan = "";


    @Value("${spring.database.encryption-key}")
    private String secretKey;

    @PostConstruct
    public void postConstruct() {
        log.info("'EncryptionKeyInjector' post construct with package for scan {},", packageForScan);

        try (ScanResult scanResult = new ClassGraph().acceptPackages(packageForScan).scan()) {
            scanResult.getAllClasses().loadClasses().forEach(this::setKey);
        }
    }

    private void setKey(Class<?> aClass) {
        log.info("'EncryptionKeyInjector' post construct - start setting key to class {},",
                aClass.getName());

        String columnName = "";
        try {
            for (Field field : aClass.getDeclaredFields()) {
                columnName = field.getName();
                ColumnTransformer columnTransformer = field.getDeclaredAnnotation(ColumnTransformer.class);
                if (columnTransformer != null) {
                    updateAnnotationValue(columnTransformer, "read");
                    updateAnnotationValue(columnTransformer, "write");
                }
            }
        } catch (Exception e) {
            String message = String.format("Encryption key cannot be loaded into class '%s' column '%s'.", aClass.getName(), columnName);
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateAnnotationValue(Annotation annotation, String annotationProperty) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field memberValuesField;
        Map<String, Object> memberValues;

        try {
            memberValuesField = handler.getClass().getDeclaredField("memberValues");
            memberValuesField.setAccessible(true);
            memberValues = (Map<String, Object>) memberValuesField.get(handler);
        } catch (Exception e) {
            String message = String.format("'EncryptionKeyInjector' post construct - updateAnnotationValue failed. Reason: %s", e.getMessage());
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        Object oldValue = memberValues.get(annotationProperty);
        if (!(oldValue instanceof String)) {
            String message = String.format("Annotation value should be String. Current value is of type %s", oldValue.getClass().getName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        String oldValueString = oldValue.toString();
        String newValueString = oldValueString.replace(valueForReplace, secretKey);
        memberValues.put(annotationProperty, newValueString);
    }
}
