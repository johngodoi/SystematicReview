package br.unifesp.henrique.john.research.systematic.review.articles;

import br.unifesp.henrique.john.research.datascience.charts.AxisChartTypes;
import br.unifesp.henrique.john.research.datascience.charts.BarChartGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArticlesDescriptionProcessor {

    public BarChartGenerator getBarChartGeneratorForPropertyFrequency(String title, String propertyDescription, Map rankedPropertyMap) {
        BarChartGenerator chartGenerator = new BarChartGenerator();
        chartGenerator.setTitle(title).setXAxisLabel(propertyDescription).setXAxisType(AxisChartTypes.Category)
                .setYAxisLabel("Frequency").setYAxisType(AxisChartTypes.Number).generatePoints(rankedPropertyMap);
        return chartGenerator;
    }

    public HashMap getRankedPropertyMap(Stream<Map.Entry<String, Long>> stream, int threshold) {
        final HashMap hashMap = new HashMap();
        stream.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(threshold)
                .forEach(e -> hashMap.put(e.getKey(), e.getValue()));
        return hashMap;
    }

    public List<String> getPropertyStringList(List<Article> articleList, Function<Article, String> getAuthors) {
        return articleList.stream().map(getAuthors).collect(Collectors.toList());
    }
}