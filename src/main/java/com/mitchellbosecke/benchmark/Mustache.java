package com.mitchellbosecke.benchmark;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;
import com.mitchellbosecke.benchmark.model.Stock;

public class Mustache extends BaseBenchmark {

    private com.github.mustachejava.Mustache stocksTemplate;
    private com.github.mustachejava.Mustache presentationsTemplate;

    @Setup
    public void setup() {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory() {

            @Override
            public void encode(String value, Writer writer) {
                // Disable HTML escaping
                try {
                    writer.write(value);
                } catch (IOException e) {
                    throw new MustacheException(e);
                }
            }
        };
        stocksTemplate = mustacheFactory.compile("templates/stocks.mustache.html");
        presentationsTemplate = mustacheFactory.compile("templates/presentations.mustache.html");
    }

    @SuppressWarnings("unchecked")
    @Benchmark
    public String stocks() {

        Map<String, Object> data = getContext();
        data.put("stockItems", new StockCollection((Collection<Stock>) data.get("stockItems")));

        Writer writer = new StringWriter();
        stocksTemplate.execute(writer, data);
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    @Benchmark
    public String presentations() {
        Writer writer = new StringWriter();
        presentationsTemplate.execute(writer, getContext());
        return writer.toString();
    }

    /**
     * This is a modified copy of
     * {@link com.github.mustachejava.util.DecoratedCollection} - we need the
     * first element at index 1.
     */
    private class StockCollection extends AbstractCollection<StockView> {

        private final Collection<Stock> c;

        public StockCollection(Collection<Stock> c) {
            this.c = c;
        }

        @Override
        public Iterator<StockView> iterator() {
            final Iterator<Stock> iterator = c.iterator();
            return new Iterator<StockView>() {

                int index = 1;

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public StockView next() {
                    Stock next = iterator.next();
                    int current = index++;
                    return new StockView(current, current == 1, !iterator.hasNext(), next);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public int size() {
            return c.size();
        }
    }

    class StockView {

        public final int index;

        public final boolean first;

        public final boolean last;

        public final Stock value;

        public final String negativeClass;

        public final String rowClass;

        public StockView(int index, boolean first, boolean last, Stock value) {
            this.index = index;
            this.first = first;
            this.last = last;
            this.value = value;
            this.negativeClass = value.getChange() > 0 ? "" : "class=\"minus\"";
            this.rowClass = index % 2 == 0 ? "even" : "odd";
        }
    }
}
