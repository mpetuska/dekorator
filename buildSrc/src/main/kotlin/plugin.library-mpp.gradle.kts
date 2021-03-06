import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinNativeCompile
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager
import util.CI
import util.SANDBOX
import util.buildHost

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  id("com.android.library")
  id("dev.petuska.klip")
  id("plugin.common")
}

android {
  compileSdkVersion(31)
  defaultConfig {
    minSdkVersion(1)
    targetSdkVersion(31)
  }
}

kotlin {
  explicitApi()

  android()
  jvm()
  js {
    useCommonJs()
    nodejs()
  }
  macosX64()
  macosArm64()
  linuxX64()
  mingwX64()
  iosArm32()
  iosArm64()
  iosX64()
  iosSimulatorArm64()
  watchosX86()
  watchosX64()
  watchosArm64()
  watchosArm32()
  watchosSimulatorArm64()
  tvosArm64()
  tvosX64()
  tvosSimulatorArm64()
  androidNativeArm32()
  androidNativeArm64()
  mingwX86()
  linuxArm32Hfp()
  linuxMips32()
  linuxMipsel32()
  linuxArm64()

  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-annotations-common"))
        implementation("dev.petuska:klip:_")
      }
    }
    val nativeMain by creating { dependsOn(commonMain) }
    val mingwMain by creating { dependsOn(nativeMain) }
    val unixMain by creating { dependsOn(nativeMain) }
    val linuxMain by creating { dependsOn(unixMain) }
    val appleMain by creating { dependsOn(unixMain) }

    val nativeTest by creating { dependsOn(commonTest) }
    val mingwTest by creating { dependsOn(nativeTest) }
    val unixTest by creating { dependsOn(nativeTest) }
    val linuxTest by creating { dependsOn(unixTest) }
    val appleTest by creating { dependsOn(unixTest) }

    targets.withType<KotlinNativeTarget> {
      val main = compilations["main"].defaultSourceSet
      val test = compilations["test"].defaultSourceSet
      when {
        konanTarget.family == Family.MINGW -> {
          main.dependsOn(mingwMain)
          test.dependsOn(mingwTest)
        }
        konanTarget.family.isAppleFamily -> {
          main.dependsOn(appleMain)
          test.dependsOn(appleTest)
        }
        else -> {
          main.dependsOn(linuxMain)
          test.dependsOn(linuxTest)
        }
      }
    }
  }
}

tasks {
  withType<CInteropProcess> {
    onlyIf { !CI || SANDBOX || konanTarget.buildHost == HostManager.host.family }
  }
  withType<AbstractKotlinNativeCompile<*, *>> {
    onlyIf { !CI || SANDBOX || compilation.konanTarget.buildHost == HostManager.host.family }
  }
}
