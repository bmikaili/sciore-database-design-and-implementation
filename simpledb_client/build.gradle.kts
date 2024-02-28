plugins {
    java
}

group = "com.yourdomain.simpledb.client"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // This line adds the simpledb_engine project as a dependency
    implementation(project(":simpledb_engine"))
}

