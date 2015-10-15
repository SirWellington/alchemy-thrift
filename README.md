Alchemy Thrift
==============================================

[![Build Status](https://travis-ci.org/SirWellington/commons-thrift.svg)](https://travis-ci.org/SirWellington/commons-thrift)

# Purpose
Part of the Alchemy collection, this Library makes it easier to work with Thrift in Java by managing Thrift Clients, providing for simple serialization and deserialization of Thrift Objects.
This saves some boilerplate code. 

This also library also demonstrates how to integrate the Thrift Compiler with Maven, by compiling the Thrift IDL Objects during the 'generate-sources' phase.

# Requirements

* JDK 8
* Maven installation
* Thrift Compiler installation (for compilation)

# Building
This project requires both maven and thrift to be on the system `PATH`. To build, just run a `mvn clean install` to compile and install to your local maven repository


# Download

> This library is not yet available on Maven Central

To use, simply add the following maven depedencncy.

## Release
```xml
<dependency>
	<groupId>tech.sirwellington.alchemy</groupId>
	<artifactId>alchemy-thrift</artifactId>
	<version>1.0</version>
</dependency>
```

## Snapshot

```xml
<dependency>
	<groupId>tech.sirwellington.alchemy</groupId>
	<artifactId>alchemy-thrift</artifactId>
	<version>1.1-SNAPSHOT</version>
</dependency>
```endency>
```

# Examples
Coming soon....

# Release Notes

## 1.0.0
+ Added Json Conversion utilities
+ Added ThriftOperation interface for Thrift Services