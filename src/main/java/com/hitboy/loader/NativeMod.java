package com.hitboy.loader;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NativeMod {
    String name();
    String version();
    ModType type() default ModType.CLIENT;
    String[] dependsOn() default {};
}