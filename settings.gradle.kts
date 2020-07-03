pluginManagement {

    repositories {
        mavenLocal()
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

buildscript {

}

rootProject.name = "sftp-uploader"
