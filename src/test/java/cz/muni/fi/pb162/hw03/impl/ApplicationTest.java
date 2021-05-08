package cz.muni.fi.pb162.hw03.impl;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jakub Cechacek
 */

public class ApplicationTest {

    private static PrintStream outBcp;
    private static PrintStream errBcp;

    private static ByteArrayOutputStream out = new ByteArrayOutputStream();
    private static PrintStream outStream = new PrintStream(out);
    private static ByteArrayOutputStream err = new ByteArrayOutputStream();
    private static PrintStream errStream = new PrintStream(err);

    private Map<String, String> params = new HashMap<>();

    @BeforeClass
    public static void setupClass() {
        outBcp = System.out;
        errBcp = System.err;
        System.setOut(outStream);
        System.setErr(errStream);
    }

    @AfterClass
    public static void teardownClass() {
        System.setOut(outBcp);
        System.setErr(errBcp);
    }

    @Before
    public void setup() throws URISyntaxException {


        params.put("--name", "Stephen Mercer");
        params.put("--age", "42");
        params.put("--occupation", "Investigative Journalist" );
        params.put("--health", "5");
        params.put("--sanity", "5");
        params.put("--inventory" , "42");
        params.put(
                "--attrs", Paths.get(getClass().getResource("/attributes.txt").toURI()).toAbsolutePath().toString()
        );
        params.put(
                "--skills", Paths.get(getClass().getResource("/skills.txt").toURI()).toAbsolutePath().toString()
        );
    }

    public String[] params(String... changes) {
        for (int i = 0; i < changes.length - 1; i += 2) {
            params.put(changes[i], changes[i+1]);
        }

        List<String> listParams = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, String> e : params.entrySet()) {
            listParams.add(e.getKey());
            String v = e.getValue();
            if (v != null && !v.isEmpty()) {
                listParams.add(e.getValue());
            }
        }
        return  listParams.toArray(new String[listParams.size()]);
    }

    public void assertOutput(ByteArrayOutputStream stream, SoftAssertions assertions, String content) {
        assertions.assertThat(stream.toString()).contains(content);
        stream.reset();
    }


    @Test
    public void shouldDisplayErrorWhenUnknownParameterIsPresent() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--foo", "0"));
        assertOutput(err, assertions, "--foo");
        assertions.assertAll();
    }

    @Test
    public void shouldDisplayErrorWhenAgeIsNotValid() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--age", "0"));
        assertOutput(err, assertions, "Parameter --age should be a positive integer");
        Application.main(params("--age", "-1"));
        assertOutput(err, assertions, "Parameter --age should be a positive integer");
        assertions.assertAll();
    }

    @Test
    public void shouldDisplayErrorWhenInventoryIsNotValid() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--inventory", "0"));
        assertOutput(err, assertions, "Parameter --inventory should be a positive multiplier of 3");
        Application.main(params("--inventory", "-1"));
        assertOutput(err, assertions, "Parameter --inventory should be a positive multiplier of 3");
        Application.main(params("--inventory", "2"));
        assertOutput(err, assertions, "Parameter --inventory should be a positive multiplier of 3");
        assertions.assertAll();
    }

    @Test
    public void shouldDisplayErrorWhenHealthIsNotValid() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--health", "0"));
        assertOutput(err, assertions, "Parameter --health should be an integer from 1 to 10");
        Application.main(params("--health", "-1"));
        assertOutput(err, assertions, "Parameter --health should be an integer from 1 to 10");
        Application.main(params("--health", "11"));
        assertOutput(err, assertions, "Parameter --health should be an integer from 1 to 10");
        assertions.assertAll();
    }

    @Test
    public void shouldDisplayErrorWhenSanityIsNotValid() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--sanity", "0"));
        assertOutput(err, assertions, "Parameter --sanity should be an integer from 1 to 10");
        Application.main(params("--sanity", "-1"));
        assertOutput(err, assertions, "Parameter --sanity should be an integer from 1 to 10");
        Application.main(params("--sanity", "11"));
        assertOutput(err, assertions, "Parameter --sanity should be an integer from 1 to 10");
        assertions.assertAll();
    }

    @Test
    public void shouldCreateOutputFileOnCorrectCall() throws IOException {
        String name = "character_" + System.currentTimeMillis() + ".html";
        Path path = Paths.get(name);
        Application.main(params("--out", name));
        Assertions.assertThat(Files.exists(path))
                .withFailMessage("File " + name + " should  be created")
                .isTrue();
        Files.delete(path);
    }

    @Test
    public void shouldDisplayHelpWhenRequested() {
        SoftAssertions assertions = new SoftAssertions();
        Application.main(params("--help", ""));
        assertOutput(out, assertions, "Usage: char-gen [options]");
        assertions.assertAll();
    }
}
