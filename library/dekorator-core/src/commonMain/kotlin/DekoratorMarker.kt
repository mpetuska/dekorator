package dev.petuska.dekorator

import kotlin.reflect.KClass

/**
 * Annotation to mark other annotations as Dekorators to be picked up and processed by the compiler
 * plugin
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
public annotation class DekoratorMarker

/** Dekorates a generic element [with] a provided dekorator */
@DekoratorMarker
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
public annotation class Dekorate(val with: KClass<out Dekorator<*>>)

/** Dekorates a property [with] a provided dekorator */
@DekoratorMarker
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
public annotation class DekorateProperty(val with: KClass<out Dekorator.Property<*, *>>)

/** Dekorates a mutable property [with] a provided dekorator */
@DekoratorMarker
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
public annotation class DekorateMutableProperty(
    val with: KClass<out Dekorator.MutableProperty<*, *>>
)
