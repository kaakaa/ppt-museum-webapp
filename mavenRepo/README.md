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
    mavenCentral()
}

dependencies {
    compile 'org.kaakaa.ppt-museum:ppt-museum-webapp:0.0.4'
}
```
