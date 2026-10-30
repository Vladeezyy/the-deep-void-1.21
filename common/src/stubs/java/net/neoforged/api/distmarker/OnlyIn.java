package net.neoforged.api.distmarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Compile-time stub of NeoForge's OnlyIn (NeoForge strips the members on a dedicated server; not in the jars) */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE})
public @interface OnlyIn {
	Dist value();

	Class<?> _interface() default Object.class;
}
