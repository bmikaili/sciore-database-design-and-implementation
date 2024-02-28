plugins {
    java
    application
}

group = "com.yourdomain.simpledb"
version = "1.0-SNAPSHOT"

sourceSets {
    main {
        java {
            srcDirs("src")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Add your dependencies here
}

application {
    mainClass.set("simpledb.server.StartServer")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register("runServer", JavaExec::class) {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("simpledb.server.StartServer")
    args("studentdb")
}

