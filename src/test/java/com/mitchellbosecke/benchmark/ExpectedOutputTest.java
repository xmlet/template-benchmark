package com.mitchellbosecke.benchmark;

import freemarker.template.TemplateException;
import io.pebbletemplates.pebble.error.PebbleException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Martin Kouba
 */
public class ExpectedOutputTest {

    @BeforeClass
    public static void beforeClass() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testFreemarkerOutput() throws IOException, TemplateException {
        Freemarker freemarker = new Freemarker();
        freemarker.setup();
        assertOutput(freemarker.stocks());
    }

    @Test
    public void testRockerOutput() throws IOException, TemplateException {
        Rocker rocker = new Rocker();
        rocker.setup();
        assertOutput(rocker.stocks());
    }

    @Test
    public void testPebbleOutput() throws IOException, PebbleException {
        Pebble pebble = new Pebble();
        pebble.setup();
        assertOutput(pebble.stocks());
    }

    @Test
    public void testVelocityOutput() throws IOException {
        Velocity velocity = new Velocity();
        velocity.setup();
        assertOutput(velocity.stocks());
    }

    @Test
    public void testMustacheOutput() throws IOException {
        Mustache mustache = new Mustache();
        mustache.setup();
        assertOutput(mustache.stocks());
    }

    @Test
    public void testJStachioOutput() throws IOException {
        JStachio jstachio = new JStachio();
        jstachio.setup();
        assertOutput(jstachio.stocks());
    }

    @Test
    public void testThymeleafOutput() throws IOException, TemplateException {
        Thymeleaf thymeleaf = new Thymeleaf();
        thymeleaf.setup();
        assertOutput(thymeleaf.stocks());
    }

    @Test
    public void testTrimouOutput() throws IOException {
        Trimou trimou = new Trimou();
        trimou.setup();
        assertOutput(trimou.stocks());
    }

    @Test
    public void testHbsOutput() throws IOException {
        Handlebars hbs = new Handlebars();
        hbs.setup();
        assertOutput(hbs.stocks());
    }

    @Test
    public void testHtmlFlowOutput() throws IOException {
        HtmlFlow hf = new HtmlFlow();
        hf.setup();
        assertOutput(hf.stocks());
    }

    @Test
    public void testHtmlFlowVsOutput() throws IOException {
        HtmlFlowVS hf = new HtmlFlowVS();
        hf.setup();
        assertOutput(hf.stocks());
    }

    @Test
    public void testJteOutput() throws IOException, TemplateException {
        var jte = new Jte();
        jte.setup();
        assertOutput(jte.stocks());
    }


    private void assertOutput(final String output) throws IOException {
        Iterator<String> expected = Arrays.stream(readExpectedOutputResource().split(">")).iterator();
        Arrays
                .stream(output.toLowerCase().replaceAll("\\s", "").split(">"))
                .forEach(actual -> assertEquals(expected.next(), actual));

    }

    private String readExpectedOutputResource() throws IOException {
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        ExpectedOutputTest.class.getResourceAsStream("/expected-output.html")))) {
            return reader.lines()
                    .map(line -> line.toLowerCase().replaceAll("\\s", ""))
                    .collect(Collectors.joining());
        }
    }
}
