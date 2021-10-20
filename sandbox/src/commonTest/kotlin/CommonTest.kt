package sandbox.test

import dev.petuska.klip.api.assertKlip
import kotlin.test.Test

class CommonTest {
  @Test
  fun test() =
      with(TestTarget()) {
        immutable.assertKlip()

        mutable.assertKlip()
        mutable = "not hello"
        mutable.assertKlip()
      }
}
