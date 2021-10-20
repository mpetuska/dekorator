package dev.petuska.dekorator

import dev.petuska.dekorator.int.MutablePropertyDekoratorDelegate
import dev.petuska.dekorator.int.PropertyDekoratorDelegate
import dev.petuska.klip.api.assertKlip
import kotlin.reflect.KClass
import kotlin.test.Test

class TestTarget {
  private class PrefixDekorator : Dekorator.Property<TestTarget, String> {
    override fun dekorate(
        thisRef: TestTarget,
        propertyName: String,
        propertyType: KClass<String>,
        initialValue: String
    ): Dekorator.Property.Descriptor<TestTarget, String> {
      return Dekorator.Property.Descriptor(getter = { "Prefix[$propertyName]: $initialValue" })
    }
  }

  private class PrefixMutableDekorator : Dekorator.MutableProperty<TestTarget, String> {
    override fun dekorate(
        thisRef: TestTarget,
        propertyName: String,
        propertyType: KClass<String>,
        initialValue: String
    ): Dekorator.MutableProperty.Descriptor<TestTarget, String> {
      var value = initialValue
      return Dekorator.MutableProperty.Descriptor(
          getter = { "Prefix[$propertyName]: $value" }, setter = { value = it })
    }
  }

  @DekorateProperty(PrefixDekorator::class)
  private val immutable: String by
      PropertyDekoratorDelegate(PrefixDekorator(), this, "immutable", String::class, "hello")

  @DekorateMutableProperty(PrefixMutableDekorator::class)
  private var mutable: String by
      MutablePropertyDekoratorDelegate(
          PrefixMutableDekorator(), this, "mutable", String::class, "hello")

  @Test
  fun test() {
    immutable.assertKlip()

    mutable.assertKlip()
    mutable = "not hello"
    mutable.assertKlip()
  }
}
