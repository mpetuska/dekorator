package dev.petuska.dekorator

import kotlin.reflect.KClass

public sealed interface Dekorator<OWNER> {
  public fun interface Property<O, T : Any> : Dekorator<O> {
    public fun dekorate(
        thisRef: O,
        propertyName: String,
        propertyType: KClass<T>,
        initialValue: T,
    ): Descriptor<O, T>

    public interface Descriptor<O, T : Any> {
      public val getter: O.() -> T

      public companion object {
        public operator fun <O, T : Any> invoke(getter: O.() -> T): Descriptor<O, T> =
            object : Descriptor<O, T> {
              override val getter: O.() -> T = getter
            }
      }
    }
  }

  public fun interface MutableProperty<O, T : Any> : Property<O, T> {
    public override fun dekorate(
        thisRef: O,
        propertyName: String,
        propertyType: KClass<T>,
        initialValue: T,
    ): Descriptor<O, T>

    public interface Descriptor<O, T : Any> : Property.Descriptor<O, T> {
      public val setter: O.(T) -> Unit

      public companion object {
        public operator fun <O, T : Any> invoke(
            getter: O.() -> T,
            setter: O.(T) -> Unit,
        ): Descriptor<O, T> =
            object : Descriptor<O, T> {
              override val getter: O.() -> T = getter
              override val setter: O.(T) -> Unit = setter
            }
      }
    }
  }
}
