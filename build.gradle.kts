plugins {
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.serialization") version "1.3.70"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.github.ryoii.loader"
version = "0.1.0"

repositories {
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    maven(url = "https://mirrors.huaweicloud.com/repository/maven")
    mavenCentral()
    jcenter()
}

val kotlinVersion = "1.3.71"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jcraft", "jsch", "0.1.55")
    implementation("org.yaml", "snakeyaml", "1.26")
//    implementation(kotlin("serialization", kotlinVersion))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.github.ryoii.uploader.MainKt"
    }
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
