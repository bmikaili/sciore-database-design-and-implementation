import org.gradle.api.tasks.JavaExec

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

tasks.register("resetStudentDB", JavaExec::class) {
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("network.ResetStudentDB")
}

tasks.register("importData", JavaExec::class) {
  doFirst {
    // Retrieve properties or throw an exception if they are not provided
    val filename: String? = project.findProperty("filename") as String?
    val tablename: String? = project.findProperty("tablename") as String?
    requireNotNull(filename) { "Please provide '-Pfilename=<path_to_file>' parameter" }
    requireNotNull(tablename) { "Please provide '-Ptablename=<table_name>' parameter" }
    
    // Set the args here where the filename and tablename are derived from project properties
    args(filename, tablename)
  }
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("network.ImportData")
}

tasks.register("exportData", JavaExec::class) {
  doFirst {
    // Retrieve properties or throw an exception if they are not provided
    val filename: String? = project.findProperty("filename") as String?
    val tablename: String? = project.findProperty("tablename") as String?
    requireNotNull(filename) { "Please provide '-Pfilename=<path_to_file>' parameter" }
    requireNotNull(tablename) { "Please provide '-Ptablename=<table_name>' parameter" }

    // Set the args here where the tablename and filename are derived from project properties
    args(tablename, filename)
  }
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("network.ExportData")
}

