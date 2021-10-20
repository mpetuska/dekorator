package dev.petuska.dekorator.int

import dev.petuska.dekorator.Dekorator
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

public class PropertyDekoratorDelegate<O, T : Any>(
    dekorator: Dekorator.Property<O, T>,
    thisRef: O,
    propertyName: String,
    propertyType: KClass<T>,
    initialValue: T,
) : ReadOnlyProperty<O, T> {
  private val descriptor: Dekorator.Property.Descriptor<O, T> =
      dekorator.dekorate(thisRef, propertyName, propertyType, initialValue)
  override fun getValue(thisRef: O, property: KProperty<*>): T = descriptor.getter.invoke(thisRef)
}

public class MutablePropertyDekoratorDelegate<O, T : Any>(
    dekorator: Dekorator.MutableProperty<O, T>,
    thisRef: O,
    propertyName: String,
    propertyType: KClass<T>,
    initialValue: T,
) : ReadWriteProperty<O, T> {
  private val descriptor: Dekorator.MutableProperty.Descriptor<O, T> =
      dekorator.dekorate(thisRef, propertyName, propertyType, initialValue)
  override fun getValue(thisRef: O, property: KProperty<*>): T = descriptor.getter.invoke(thisRef)
  override fun setValue(thisRef: O, property: KProperty<*>, value: T): Unit =
      descriptor.setter.invoke(thisRef, value)
}
