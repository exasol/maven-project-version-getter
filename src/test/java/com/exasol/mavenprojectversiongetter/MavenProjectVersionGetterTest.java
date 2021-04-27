package com.exasol.mavenprojectversiongetter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class MavenProjectVersionGetterTest {
    @Test
    void test() {
        assertThat(MavenProjectVersionGetter.getCurrentProjectVersion(), equalTo("0.1.0"));
    }
}