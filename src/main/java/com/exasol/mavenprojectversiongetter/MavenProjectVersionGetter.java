package com.exasol.mavenprojectversiongetter;

import static java.util.function.Predicate.not;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

import com.exasol.errorreporting.ExaError;

/**
 * This class gives the version of the project where the tests are being currently executed.
 */
public class MavenProjectVersionGetter {

    private MavenProjectVersionGetter() {
        // empty on purpose
    }

    /**
     * Get the version of the current project.
     *
     * @return version string
     */
    public static String getCurrentProjectVersion() {
        return getProjectVersion(Path.of("pom.xml"));
    }

    /**
     * Get the version of the current project as specified for the project itself or for its parent.
     *
     * @return version string
     */
    public static String getVersionOfCurrentProjectOrParent() {
        return getVersionOfProjectOrParent(Path.of("pom.xml"));
    }

    /**
     * Get the version as specified for the project itself or for its parent
     *
     * @param pomFile path to {@code pom.xml} to get the version from
     * @return version string
     */
    public static String getVersionOfProjectOrParent(final Path pomFile) {
        return Stream.of(getProjectVersion(pomFile), //
                getParentVersion(pomFile)) //
                .filter(not(String::isEmpty)) //
                .findFirst() //
                .orElse("");
    }

    /**
     * Get the version of a given project.
     *
     * @param pomFile path to {@code pom.xml} to get the version from
     * @return version string
     */
    public static String getProjectVersion(final Path pomFile) {
        return getPropertyOfXmlFile(pomFile, "/project/version");
    }

    /**
     * Get the revision of a parent pom.
     *
     * @param pomFile path to {@code pom.xml} to get the revision from
     * @return revision string
     */
    public static String getProjectRevision(final Path pomFile) {
        return getPropertyOfXmlFile(pomFile, "/project/properties/revision");
    }

    /**
     * Get the version of the parent project.
     *
     * @param pomFile path to {@code pom.xml} to get the parent version from
     * @return version string
     */
    public static String getParentVersion(final Path pomFile) {
        return getPropertyOfXmlFile(pomFile, "/project/parent/version");
    }

    private static String getPropertyOfXmlFile(final Path pomFile, final String propertyXPath) {
        try {
            final var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            final var pom = documentBuilderFactory.newDocumentBuilder().parse(pomFile.toFile());
            final var xPath = XPathFactory.newInstance().newXPath();
            return xPath.compile(propertyXPath).evaluate(pom);
        } catch (final XPathExpressionException | SAXException | ParserConfigurationException | IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-MPVG-1")
                    .message("Failed to get current project version from pom file {{file}}.", pomFile).toString(),
                    exception);
        }
    }
}
