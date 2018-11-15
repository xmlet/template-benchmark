template-benchmark
================

JMH benchmark for popular Java template engines:

* [Freemarker](http://freemarker.org/)
* [Handlebars](https://github.com/jknack/handlebars.java)
* [Mustache](https://github.com/spullara/mustache.java)
* [Pebble](http://www.mitchellbosecke.com/pebble)
* [Rocker](https://github.com/fizzed/rocker)
* [Thymeleaf](http://www.thymeleaf.org/)
* [Trimou](http://trimou.org/)
* [Velocity](http://velocity.apache.org/)
* [j2Html](https://j2html.com/)
* [KotlinX Html](https://github.com/Kotlin/kotlinx.html)
* [HtmlFlow](https://github.com/xmlet/HtmlFlow)


Running the benchmark
======================

1. Download the source code and build it (`mvn clean install`)
2. Run the entire benchmark suite with `java -jar target/benchmarks.jar`
3. (Optional) To run a single benchmark, such as Mustache, use `java -jar target/benchmarks.jar Mustache`

Generating plot
===============
1. Run `Stocks` benchmark while exporting results to csv with `java -jar target/benchmarks.jar -rff results.csv -rf csv stocks`
2. Use gnuplot to generate plot with `gnuplot benchmark.plot`. This will output `results.png`.
3. Run `Presentations` benchmark while exporting results to csv with `java -jar target/benchmarks.jar -rff results.csv -rf csv presentations`
4. Use gnuplot to generate plot with `gnuplot benchmark.plot`. This will output `results.png`.

`Presentations` benchmark was imported from [spring-comparing-template-engines](https://github.com/jreijn/spring-comparing-template-engines).

Rules of Template Engine Configuration
======================================
It is imperative that each template engine is configured in way to reflect real-world usage as opposed to it's *optimal* configuration. Typically this means an out-of-the-box configuration.

To strive for a common set of features across template engines, the following configurations are expected:
* Disabling of HTML escaping
* Template loaded from classpath prior to actual benchmark

Interpreting the Results
========================
The benchmarks measure throughput, given in "ops/time". The time unit used is seconds.
Generally, the score represents the number of templates rendered per second; the higher the score, the better.

Example Results (2018)
===============================

These tests were performed on a local machine with the following specs:

```
Mac OS X Version 10.14.1
3 GHz Intel Core i7 Quad core
Java(TM) SE Runtime Environment 18.3 (build 10.0.1+10)
Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.1+10, mixed mode)
```

<table>
<tr>
<td><img src="results-presentations.svg"></td>
<td><img src="results-stocks.svg"></td>
</tr>
</table>
