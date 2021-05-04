import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies {
    api(project(":core:common"))

    api("mysql:mysql-connector-java")
    api("com.h2database:h2")
    api("org.springframework.data:spring-data-commons")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.hibernate:hibernate-envers")
}
