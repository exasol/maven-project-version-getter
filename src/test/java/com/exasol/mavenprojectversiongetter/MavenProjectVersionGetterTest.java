package com.exasol.mavenprojectversiongetter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.exasol.mavenprojectversiongetter.MavenProjectVersionGetter.VersionGetterException;

class MavenProjectVersionGetterTest {

    @Test
    void testReadVersion(@TempDir final Path tempDir) throws IOException {
        final Path pomFile = tempDir.resolve("pom.xml");
        Files.writeString(pomFile, "<project><version>1.2.3</version></project>");
        assertThat(MavenProjectVersionGetter.getProjectVersion(pomFile), equalTo("1.2.3"));
    }

    @Test
    void getVersionOfCurrentProjectOrParent(@TempDir final Path tempDir) throws IOException {
        final Path pomFile = tempDir.resolve("pom.xml");
        Files.writeString(pomFile, "<project><parent><version>2.3.4</version></parent></project>");
        assertThat(MavenProjectVersionGetter.getVersionOfProjectOrParent(pomFile), equalTo("2.3.4"));
    }

    @Test
    void inconsistentVersionsOfProjectAndParent(@TempDir final Path tempDir) throws IOException {
        final Path pomFile = tempDir.resolve("pom.xml");
        Files.writeString(pomFile,
                "<project><version>1.2.3</version><parent><version>2.3.4</version></parent></project>");
        final Exception exception = assertThrows(VersionGetterException.class,
                () -> MavenProjectVersionGetter.getVersionOfProjectOrParent(pomFile));
        assertThat(exception.getMessage(), matchesRegex("E-MPVG-2: Inconsistent version information in file .*pom.xml:"
                + " project version is '1.2.3', while parent version is '2.3.4'."));
    }

    @Test
    void testGetVersionOfCurrentProject() {
        assertThat(MavenProjectVersionGetter.getCurrentProjectVersion(),
                equalTo(MavenProjectVersionGetter.getProjectVersion(Path.of("pom.xml"))));
    }

    @Test
    void testGettingVersionFails(@TempDir final Path tempDir) {
        final Path nonExistingPomFile = tempDir.resolve("nonExistingPom.xml");
        final Exception exception = assertThrows(VersionGetterException.class,
                () -> MavenProjectVersionGetter.getProjectVersion(nonExistingPomFile));
        assertThat(exception.getMessage(), startsWith("E-MPVG-1: Failed to get current project version from pom file"));
    }

    @Test
    void testReadRevision(@TempDir final Path tempDir) throws IOException {
        final Path pomFile = tempDir.resolve("pom.xml");
        Files.writeString(pomFile,
                "<project><version>${revision}</version><properties><revision>1.2.3</revision></properties></project>");
        assertThat(MavenProjectVersionGetter.getProjectRevision(pomFile), equalTo("1.2.3"));
    }
}