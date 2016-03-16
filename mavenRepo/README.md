# Maven Repository for ppt-museum

Here is publishing ppt-museum-webapp.jar.

# Usage

build.gradle
```
repositories {
    // add here raw url as maven repository
    maven {
        url 'https://raw.githubusercontent.com/kaakaa/ppt-museum-webapp/master/mavenRepo'
    }
    // add Apache snapshots repository for pdfbox:2.0.0-SNAPSHOT that ppt-museum depends
    maven {
        url 'https://repository.apache.org/content/groups/snapshots'
    }
    mavenCentral()
}

dependencies {
    compile 'org.kaakaa.ppt-museum:ppt-museum-webapp:0.0.4'
}
```