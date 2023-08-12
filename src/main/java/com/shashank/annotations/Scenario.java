package com.shashank.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scenario {

  String tester() default "Shashank Shekhar";

  String module() default "Payment Module";

  String id() default "";

  String description() default "Testing Project";
}
