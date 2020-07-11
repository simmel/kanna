## kanna

A small shim on top of
[camel-blueprint-main](https://github.com/apache/camel-karaf/tree/master/components/camel-blueprint-main)
which runs your [OSGi Blueprints](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.blueprint.html)
in sort of the same way that [Apache Karaf](https://karaf.apache.org) does and
with logging enabled via [logback](https://logback.qos.ch/).

### Usage

1. FIXME

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
