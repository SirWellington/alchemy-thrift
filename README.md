Alchemy Thrift
==============================================

[<img src="https://raw.githubusercontent.com/SirWellington/alchemy/develop/Graphics/Logo/Alchemy-Logo-v3-name.png" width="200">](https://github.com/SirWellington/alchemy)

[![Build Status](https://travis-ci.org/SirWellington/alchemy-thrift.svg)](https://travis-ci.org/SirWellington/alchemy-thrift)

# Purpose
Part of the Alchemy collection.

This Library makes it easier to work with Thrift in Java by managing Thrift Clients, providing simple serialization and deserialization of Thrift Objects, saving some boilerplate code.

This also library also demonstrates how to integrate the Thrift Compiler with Maven, by compiling the Thrift IDL Objects during the 'generate-sources' phase.

# Requirements

* Java 8
* Maven installation
* Thrift Compiler installation (for compilation)

# Building
This project requires both maven and thrift to be on the system `PATH`. To build, just run a `mvn clean install` to compile and install to your local maven repository


# Download

> This library is not yet available on Maven Central

To use, simply add the following maven dependency.

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

## 1.0
+ Initial Public Release

## 1.0.0
+ Added Json Conversion utilities
+ Added ThriftOperation interface for Thrift Services
