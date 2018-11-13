package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.pebble.error.PebbleException;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 */
public class ExpectedPresentationsTest {

    @Test
    public void testFreemarkerOutput() throws IOException, TemplateException {
        Freemarker freemarker = new Freemarker();
        freemarker.setup();
        assertOutput(freemarker.presentations());
    }

    @Test
    public void testPebbleOutput() throws IOException, PebbleException {
        Pebble pebble = new Pebble();
        pebble.setup();
        assertOutput(pebble.presentations());
    }

    @Test
    public void testVelocityOutput() throws IOException {
        Velocity velocity = new Velocity();
        velocity.setup();
        assertOutput(velocity.presentations());
    }

    @Test
    public void testMustacheOutput() throws IOException {
        Mustache mustache = new Mustache();
        mustache.setup();
        assertOutput(mustache.presentations());
    }

    @Test
    public void testThymeleafOutput() throws IOException, TemplateException {
        Thymeleaf thymeleaf = new Thymeleaf();
        thymeleaf.setup();
        assertOutput(thymeleaf.presentations());
    }

    @Test
    public void testTrimouOutput() throws IOException {
        Trimou trimou = new Trimou();
        trimou.setup();
        assertOutput(trimou.presentations());
    }

    @Test
    public void testHbsOutput() throws IOException {
        Handlebars hbs = new Handlebars();
        hbs.setup();
        assertOutput(hbs.presentations());
    }

    @Test
    public void testRockerOutput() throws IOException {
        Rocker rocker = new Rocker();
        rocker.setup();
        assertOutput(rocker.presentations());
    }

    @Test
    public void testJ2HtmlOutput() throws IOException {
        //assertOutput(new J2Html().presentations());
    }

    @Test
    public void testKotlinOutput() throws IOException {
        //assertOutput(new Kotlin().presentations());
    }

    @Test
    public void testHtmlFlowOutput() throws IOException {
        HtmlFlow hf = new HtmlFlow();

        hf.setup();

        assertOutput(hf.presentations());
        assertOutput(hf.presentations());
    }

    private void assertOutput(final String output) throws IOException {
        Pattern less = Pattern.compile("<");
        Iterator<String> actual = less
            .splitAsStream(output.replaceAll("\\s", "").toLowerCase())
            .iterator();
        less.splitAsStream(readExpectedOutputResource())
            .forEach(expected -> {
                String curr = actual.next();
                assertEquals(expected, curr);
                // System.out.print("<" + curr);
            });
    }

    private String readExpectedOutputResource() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(ExpectedPresentationsTest.class.getResourceAsStream("/expected-presentations.html")))) {
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                  break;
                }
                builder.append(line);
            }
        }
        // Remove all whitespaces
        return builder.toString().replaceAll("\\s", "").toLowerCase();
    }
}
