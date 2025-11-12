plugins {
    kotlin("jvm") version "2.1.0"
    id("jacoco")
}

group = "org.bot"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.insert-koin:koin-core:4.1.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/core/di/**",        // исключить DI
                    "**/org/bot/Main*.*",   // исключить только Main.kt
                    "**/org/bot/App*.*"     // если есть App.kt
                )
            }
        })
    )
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
tasks.register("printCoverage") {
    dependsOn(tasks.jacocoTestReport)
    doLast {
        val reportFile = file("build/reports/jacoco/test/jacocoTestReport.xml")
        if (reportFile.exists()) {
            println("✅ Coverage report generated at: ${reportFile.absolutePath}")
            println("ℹ️  Open build/reports/jacoco/test/html/index.html in browser to view results.")
        } else {
            println("⚠️  Coverage report not found. Make sure tests have run and generated data.")
        }
    }
}

kotlin {
    jvmToolchain(21)
}