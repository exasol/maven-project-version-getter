# Maven Project Version Getter

[![Build Status](https://github.com/exasol/maven-project-version-getter/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/maven-project-version-getter/actions/workflows/ci-build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.exasol/maven-project-version-getter)](https://search.maven.org/artifact/com.exasol/maven-project-version-getter)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Amaven-project-version-getter&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Amaven-project-version-getter)

Java helper library for getting the current project's version from test code.

## Usage

```java
MavenProjectVersionGetter.getCurrentProjectVersion(); //returns for example "0.1.0"
```

You can also use this tool to get the version of a different project:

```java
MavenProjectVersionGetter.getProjectVersion(Path.of("path/to/project/pom.xml"));
```

## Additional Information:

* [Changelog](doc/changes/changelog.md)
* [Dependencies](dependencies.md)
