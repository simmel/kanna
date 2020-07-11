## kanna

A small shim on top of
[camel-blueprint-main](https://github.com/apache/camel-karaf/tree/master/components/camel-blueprint-main)
which runs your [OSGi Blueprints](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.blueprint.html)
in sort of the same way that [Apache Karaf](https://karaf.apache.org) does and
with logging enabled via [logback](https://logback.qos.ch/).

### Usage

#### Gradle

1. [Create a new Gradle project](https://guides.gradle.org/creating-new-gradle-builds/#initialize_a_project) in a folder e.g. `yourintegration`
1. Change your `build.gradle` to:
   ```groovy
   plugins {
     id 'application'
   }

   mainClassName = 'se.soy.kanna.Main'
   group = 'tld.example.your.integrations'
   version = '1.0.0'

   ext {
     versions = [
       camel       : '3.3.0',
       kanna       : '1.0.0',
     ]
   }

   repositories {
     mavenCentral()
   }

   dependencies {
     runtimeOnly "se.soy.kanna:kanna:$versions.kanna"

     // What ever Camel Components you need https://camel.apache.org/components/latest/
     // camel-blueprint-main is obligatory though, otherwise nothing will work.
     implementation "org.apache.camel.karaf:camel-blueprint-main:$versions.camel"
     implementation "org.apache.camel:camel-log:$versions.camel"
     implementation "org.apache.camel:camel-timer:$versions.camel"
   }
   ```
1. Add an OSGi Blueprint to `src/main/resources/OSGI-INF/blueprint/yourintegration.xml`:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.1.0"
              xsi:schemaLocation="
            http://www.osgi.org/xmlns/blueprint/v1.0.0 https://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd
            http://camel.apache.org/schema/blueprint http://camel.apache.org/schema/blueprint/camel-blueprint.xsd
            http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.1.0 http://aries.apache.org/schemas/blueprint-cm/blueprint-cm-1.1.0.xsd">

     <camelContext id="yourContext" xmlns="http://camel.apache.org/schema/blueprint">
       <route id="yourRoute">
         <from uri="timer:yourTimer?period=60000"/>
         <log message="Hello world!"/>
       </route>
     </camelContext>
   </blueprint>
   ```
1. Build it
   ```terminal
   $ ./gradlew installDist
   [...]
   BUILD SUCCESSFUL in 2s
   5 actionable tasks: 5 executed
   ```
1. And run it
   ```terminal
   $ env JAVA_OPTS="-Xms50m -Xmx50m" build/install/yourintegration/bin/yourintegration \
   -bundleName yourintegration \
   -applicationContext '/OSGI-INF/blueprint/yourintegration.xml'
   [main] INFO se.soy.kanna.Main - Starting Kanna
   [...]
   [Camel (yourContext) thread #1 - timer://yourTimer] INFO yourRoute - Hello world!
   ```

#### Maven

1. FIXME Use the [maven-assembly-plugin](https://maven.apache.org/plugins/maven-assembly-plugin/)
somehow, see [this blog-post](https://www.petrikainulainen.net/programming/tips-and-tricks/creating-a-runnable-binary-distribution-with-maven-assembly-plugin/).

### Caveats

* Unfortunately I haven't figured out use this in an uberjar. When packages as
  an uberjar camel-blueprint-main can't find the needed bundles, probably because
	shadow/shade trashes something in `/META-INF/` in the jars in some way. It's
	not related to services because shadow/shade handles that. dist{Tar,Zip}
  works perfectly though!
* Your blueprints aren't automatically loaded from `/OSGI-INF/blueprint/*.xml`
  as it should, so you need `-ac /OSGI-INF/blueprint/your-blueprint.xml` as an
  argument sadly.

### TODO

* [ ] Investigate how to be used as uberjar
* [ ] Be able to set bundle version?
* [ ] Why doesn't it load `/OSGI-INF/blueprint/*.xml` automatically?
