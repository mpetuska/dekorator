package sandbox.test

import dev.petuska.dekorator.DekorateMutableProperty
import dev.petuska.dekorator.DekorateProperty
import dev.petuska.dekorator.Dekorator
import dev.petuska.dekorator.int.MutablePropertyDekoratorDelegate
import dev.petuska.dekorator.int.PropertyDekoratorDelegate
import kotlin.reflect.KClass

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
  val immutable: String by
      PropertyDekoratorDelegate(PrefixDekorator(), this, "immutable", String::class, "hello")

  @DekorateMutableProperty(PrefixMutableDekorator::class)
  var mutable: String by
      MutablePropertyDekoratorDelegate(
          PrefixMutableDekorator(), this, "mutable", String::class, "hello")
}
