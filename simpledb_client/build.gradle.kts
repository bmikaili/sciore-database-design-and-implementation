plugins { java }

group = "com.yourdomain.simpledb.client"

version = "1.0-SNAPSHOT"

sourceSets { main { java { srcDirs("src") } } }

repositories { mavenCentral() }

dependencies {
  // This line adds the simpledb_engine project as a dependency
  implementation(project(":simpledb_engine"))
}

tasks.register("runClient", JavaExec::class) {
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("network.FindMajors")
  args(project.findProperty("dept") ?: "Math")
}

tasks.register("createStudentDB", JavaExec::class) {
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("network.CreateStudentDB")
}
