import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath( "org.jetbrains.kotlin:kotlin-noarg:1.3.71")
    }
}

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
    kotlin("plugin.jpa") version "1.3.71"
}

allprojects {

    group = "com.doohong.oauth"
    version = "staging"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("idea")
        plugin("eclipse")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin( "kotlin-allopen")
    }
    val ktlint by configurations.creating

    dependencies {
        ktlint("com.pinterest:ktlint:0.35.0")
    }

    val verifyKtlint = task("ktlint", JavaExec::class) {
        description = "Check *.gradle.kts code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("src/**/*.kt")
    }

    tasks.check.get().dependsOn(verifyKtlint)

    task("ktlintFormat", JavaExec::class) {
        description = "Fix *.gradle.kts code style violations."
        classpath = verifyKtlint.classpath
        main = verifyKtlint.main
        args("-F")
        args(verifyKtlint.args)
    }
}

allOpen {
    annotation("javax.persistence.Entity")
}