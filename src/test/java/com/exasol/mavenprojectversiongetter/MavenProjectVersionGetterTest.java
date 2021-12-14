package com.exasol.mavenprojectversiongetter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MavenProjectVersionGetterTest {

    @Test
    void testReadVersion(@TempDir final Path tempDir) throws IOException {
        final Path pomFile = tempDir.resolve("pom.xml");
        Files.writeString(pomFile, "<project><version>1.2.3</version></project>");
        assertThat(MavenProjectVersionGetter.getProjectVersion(pomFile), equalTo("1.2.3"));
    }

    @Test
    void testGetVersionOfCurrentProject() {
        assertThat(MavenProjectVersionGetter.getCurrentProjectVersion(),
                equalTo(MavenProjectVersionGetter.getProjectVersion(Path.of("pom.xml"))));
    }

    @Test
    void testGettingVersionFails(@TempDir final Path tempDir) {
        final Path nonExistingPomFile = tempDir.resolve("nonExistingPom.xml");
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
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