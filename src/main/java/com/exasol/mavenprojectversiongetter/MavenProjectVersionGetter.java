package com.exasol.mavenprojectversiongetter;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

import com.exasol.errorreporting.ExaError;

/**
 * This class gives the version of the project that's tests are currently executed.
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
        try {
            final var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            final var pom = documentBuilderFactory.newDocumentBuilder().parse(new File("pom.xml"));
            final var xPath = XPathFactory.newInstance().newXPath();
            return xPath.compile("/project/version").evaluate(pom);
        } catch (final XPathExpressionException | SAXException | ParserConfigurationException | IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-MPVG-1")
                    .message("Failed to get current project version from pom file.").toString(), exception);
        }
    }
}
