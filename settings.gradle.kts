rootProject.name = "simpledb-root"

include(":simpledb_engine")

include(":simpledb_client")

project(":simpledb_engine").projectDir = file("simpledb_engine")

project(":simpledb_client").projectDir = file("simpledb_client")
