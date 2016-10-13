package br.unifesp.henrique.john.research.systematic.review.utils;

import br.unifesp.henrique.john.research.datascience.words.WordProcessor;
import br.unifesp.henrique.john.research.systematic.review.articles.Article;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticleSearchTemplateObject;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesCSVDAO;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesDescriptionProcessor;
import br.unifesp.henrique.john.research.systematic.review.assertion.articles.ArticlesAssertion;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * Created by Lab-Eletrofisio on 25/07/2016.
 */
public abstract class ReviewTestRoot implements ReviewTestRootDependencies {

    protected ArticlesDescriptionProcessor articlesDescriptionProcessor;
    protected WordProcessor processor;
    protected ArticlesCSVDAO csvdao;
    
    public abstract String getGeneralPathFile();
    public abstract String getResearchFrom2012PathFile();

    @Before
    public void setUp() throws Exception {
        processor = getWordProcessor();
        csvdao = getArticlesCSVDAO();
        articlesDescriptionProcessor = getArticlesDescriptionProcessor();
    }

    @Override
    public ArticlesDescriptionProcessor getArticlesDescriptionProcessor() {
        return new ArticlesDescriptionProcessor();
    }

    @Override
    public ArticlesCSVDAO getArticlesCSVDAO() {
        return new ArticlesCSVDAO();
    }

    @Override
    public WordProcessor getWordProcessor() {
        return new WordProcessor();
    }

    private ArrayList<Article> separateArticlesExclusiveForSearchFilteredByYear() {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getResearchFrom2012PathFile(), articleSearchTemplateObject);
        return articles_2012.stream().filter(a -> !articles.contains(a)).collect(Collectors.toCollection(ArrayList::new));
    }


    private ArrayList<Article> separateArticlesExclusiveForSearchWithoutParameters() {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getResearchFrom2012PathFile(), articleSearchTemplateObject);
        return articles.stream().filter(a -> !articles_2012.contains(a)).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Quais artigos comuns nos resultados das duas buscas?
     * (Intersecção)
     * @throws Exception
     */
    public abstract void commonsFromSearchWithOrWithoutYear() throws Exception;
    public void commonsFromSearchWithOrWithoutYear(int expectedQuantityOfArticles) throws Exception {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle().withYear().withSource().withPublisher().withCiteQuantity().withGSRank().withArticleURL();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getResearchFrom2012PathFile(), articleSearchTemplateObject);

        ArrayList<Article> commons = articles.stream().filter(a ->  articles_2012.contains(a)).collect(Collectors.toCollection(ArrayList::new));
        Assert.assertEquals("",expectedQuantityOfArticles,commons.size());
        commons.forEach(c -> System.out.println(c));
    }

    /**
     * Quais artigos vieram apenas na busca geral?
     * @throws Exception
     */
    public abstract void differenceForSearchWithoutParameters() throws Exception ;
    public void differenceForSearchWithoutParameters(int expectedQuantityOfArticles) throws Exception {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle().withYear().withSource().withPublisher().withCiteQuantity().withGSRank().withArticleURL();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getResearchFrom2012PathFile(), articleSearchTemplateObject);
        ArrayList<Article> differentArticlesFromGeneralSearch = articles.stream().filter(a -> !articles_2012.contains(a)).collect(Collectors.toCollection(ArrayList::new));

        Assert.assertFalse("",articles.containsAll(articles_2012));
        System.out.println("Different Articles From General Search");
        Assert.assertEquals("",expectedQuantityOfArticles,differentArticlesFromGeneralSearch.size());
        differentArticlesFromGeneralSearch.forEach(c -> System.out.println(c));
    }

    /**
     * Quais as principais palavras dos títulos, nos resultados exclusidos, da busca geral?
     * @throws Exception
     */
    public abstract void commonWordsAtTitleForSearchWithoutParameters() throws Exception ;
    public void commonWordsAtTitleForSearchWithoutParameters(int limitResult) throws Exception {
        ArrayList<Article> differentArticlesFromGeneralSearch = separateArticlesExclusiveForSearchWithoutParameters();

        List<String> titles = articlesDescriptionProcessor.getPropertyStringList(differentArticlesFromGeneralSearch, Article::getTitle);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(titles," ",true, true);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);
        System.out.println(rankedPropertyMap);
    }

    /**
     * Quais autores são mais frequentes, nos resultados exclusidos, da busca geral?
     */
    public abstract void statisticsFromAuthorsForSearchWithoutParameters() ;
    public void statisticsFromAuthorsForSearchWithoutParameters(int limitResult) {
        ArrayList<Article> differentArticlesFromGeneralSearch = separateArticlesExclusiveForSearchWithoutParameters();

        List<String> authors = articlesDescriptionProcessor.getPropertyStringList(differentArticlesFromGeneralSearch, Article::getAuthors);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(authors,",",false,false);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);

        System.out.println(rankedPropertyMap);
    }

    /**
     * Quais as artigos vieram apenas na busca parametrizada por ano?
     * @throws Exception
     */
    public abstract void differenceForSearchFilteredByYear() throws Exception ;
    public void differenceForSearchFilteredByYear(int expectedQuantityOfArticles) throws Exception {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle().withYear().withSource().withPublisher().withCiteQuantity().withGSRank().withArticleURL();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getResearchFrom2012PathFile(), articleSearchTemplateObject);

        Assert.assertFalse("",articles_2012.containsAll(articles));
        System.out.println("Different Articles From Search Filtered by Year");
        ArrayList<Article> differentArticlesFilteredByYearSearch = articles_2012.stream().filter(a -> !articles.contains(a)).collect(Collectors.toCollection(ArrayList::new));
        Assert.assertEquals("",expectedQuantityOfArticles,differentArticlesFilteredByYearSearch.size());
        differentArticlesFilteredByYearSearch.forEach(c -> System.out.println(c));
    }

    /**
     * Quais as principais palavras dos títulos, nos resultados exclusidos, da busca parametrizada por ano?
     * @throws Exception
     */
    public abstract void commonWordsAtTitleForSearchFilteredByYear() throws Exception ;
    public void commonWordsAtTitleForSearchFilteredByYear(int limitResult) throws Exception {
        ArrayList<Article> differentArticlesForSearchFilteredByYear = separateArticlesExclusiveForSearchFilteredByYear();
        List<String> titles = articlesDescriptionProcessor.getPropertyStringList(differentArticlesForSearchFilteredByYear, Article::getTitle);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(titles," ",true, true);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);
        System.out.println(rankedPropertyMap);
    }

    /**
     * Quais autores são mais frequentes, nos resultados exclusidos, da busca parametrizada por ano?
     */
    public abstract void statisticsFromAuthorsForSearchFilteredByYear() ;
    public void statisticsFromAuthorsForSearchFilteredByYear(int limitResult) {
        ArrayList<Article> differentArticlesForSearchFilteredByYear = separateArticlesExclusiveForSearchFilteredByYear();

        List<String> authors = articlesDescriptionProcessor.getPropertyStringList(differentArticlesForSearchFilteredByYear, Article::getAuthors);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(authors,",",false,false);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);

        System.out.println(rankedPropertyMap);
    }

//FIXME
    //@Test
//    public void statisticsFromYear() {
//        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject().withAuthors().withTitle().withYear();
//        List<Article> articleList = csvdao.loadArticlesFromCSV(getGeneralPathFile(), searchTemplateObject);
//        ArticlesAssertion.assertThese(articleList).hasQtt(631);
//        List<String> years = articlesDescriptionProcessor.getPropertyStringList(articleList, Article::getYear);
//        Map<String, Long> namesOccurrences = processor.countWordsOccurrences(years,false,false);
//        Map<String, Long> rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), 20);
//
//        SortedMap<String, Long> sortedMap = Maps.newTreeMap();
//        for (String key :
//                rankedPropertyMap.keySet()) {
//            sortedMap.put(key,rankedPropertyMap.get(key));
//        }
//        System.out.println(sortedMap);
////        BarChartGenerator chartGenerator = articlesDescriptionProcessor.getBarChartGeneratorForPropertyFrequency("Years with more publications", "Years", sortedMap);
////        chartGenerator.show();
//        //o crescimento a partir de 2010 pode ser devido a diminuição do custo da tecnologia
//    }
}
