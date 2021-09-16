package com.exasol.mavenprojectversiongetter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
}