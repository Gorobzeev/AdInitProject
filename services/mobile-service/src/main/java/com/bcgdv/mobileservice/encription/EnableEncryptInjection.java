package com.bcgdv.mobileservice.encription;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({EncryptorAnnotationDefinitionPostProcessor.class})
public @interface EnableEncryptInjection {

    String valueForReplace() default "{encryption.key}";

    String packageForScan();
}
