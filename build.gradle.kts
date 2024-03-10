import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {

    java
    idea

    // Gradle Versions Plugin
    // https://github.com/ben-manes/gradle-versions-plugin
    id("com.github.ben-manes.versions") version "0.51.0"
}

if (!JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_21)) {
    val error = "Build requires Java 21 and does not run on Java ${JavaVersion.current().majorVersion}."
    throw GradleException(error)
}

idea {
    project {
        jdkName = "21"
        languageLevel = IdeaLanguageLevel(JavaVersion.VERSION_21)
        vcs = "Git"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-codec:commons-codec:1.16.1")
    implementation("commons-io:commons-io:2.15.1")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-compress:1.26.1")
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation("org.apache.commons:commons-dbcp2:2.12.0")
    implementation("org.apache.commons:commons-email:1.6.0")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("org.apache.commons:commons-pool2:2.12.0")
    implementation("org.apache.commons:commons-text:1.11.0")
    implementation("org.json:json:20240303")
}

tasks {

    clean {
        // delete IntelliJ build directory
        delete("${projectDir}/out/")
    }

    dependencyUpdates {
        gradleReleaseChannel = "release"
        rejectVersionIf {
            isUnstableVersion(candidate)
        }
    }

}

fun isUnstableVersion(candidate: ModuleComponentIdentifier): Boolean {
    return candidate.version.contains("-M") // ignore milestone version
            || candidate.version.contains("-rc") // ignore release candidate versions
            || candidate.version.contains("-alpha") // ignore alpha versions
}
