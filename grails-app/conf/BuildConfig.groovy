grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    inherits("global") {}
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
    }

    dependencies {
        compile("com.lowagie:itext:2.1.5")
        compile("com.lowagie:itext-rtf:2.1.5")
        runtime 'xerces:xercesImpl:2.9.0'
    }

    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.0.3") {
            export = false
        }
    }
}
