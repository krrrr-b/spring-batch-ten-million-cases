plugins {
    application
}

application {
    mainClassName = "com.skeleton.batch.BatchApplication"
}

tasks {
    startScripts {
        mainClassName = "com.skeleton.batch.BatchApplication"
        version = System.getenv("VERSION") ?: project.version
    }

    jar {
        manifest {
            attributes(
                "Main-Class" to "com.skeleton.batch.BatchApplication"
            )
        }

        version = (System.getenv("VERSION") ?: project.version) as String?
    }

    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        version = (System.getenv("VERSION") ?: project.version) as String?
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to "com.skeleton.batch.BatchApplication")
        }

        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }
}

dependencies {
    api(project(":data:data-mysql"))
    api(project(":core:common"))
    
    api("org.springframework.boot:spring-boot-starter-batch")
}
