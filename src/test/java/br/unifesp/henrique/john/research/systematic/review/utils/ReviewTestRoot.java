package br.unifesp.henrique.john.research.systematic.review.utils;

import br.unifesp.henrique.john.research.datascience.words.WordProcessor;
import br.unifesp.henrique.john.research.systematic.review.articles.Article;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticleSearchTemplateObject;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesCSVDAO;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesDescriptionProcessor;
import br.unifesp.henrique.john.research.systematic.review.assertion.articles.ArticlesAssertion;
import com.google.common.collect.Maps;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lab-Eletrofisio on 25/07/2016.
 */
public abstract class ReviewTestRoot implements ReviewTestRootDependencies {

    protected ArticlesDescriptionProcessor articlesDescriptionProcessor;
    protected WordProcessor processor;
    protected ArticlesCSVDAO csvdao;
    protected List<Article> articlesToWrite;
    protected String articlesToWritePath;

    public abstract String getGeneralResearchPathFile();
    public abstract String getJustRecentResearchPathFile();

    @Before
    public void setUp() throws Exception {
        processor = getWordProcessor();
        csvdao = getArticlesCSVDAO();
        articlesDescriptionProcessor = getArticlesDescriptionProcessor();
        articlesToWrite = new ArrayList<>();
        articlesToWritePath = "./src/test/article_results.csv";
    }

    @After
    public void finalize() throws Exception {
        if(articlesToWrite.isEmpty()) return;
        csvdao.writeToCSV(articlesToWritePath,articlesToWrite);;
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
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getJustRecentResearchPathFile(), articleSearchTemplateObject);
        return articles_2012.stream().filter(a -> !articles.contains(a)).collect(Collectors.toCollection(ArrayList::new));
    }


    private ArrayList<Article> separateArticlesExclusiveForSearchWithoutParameters() {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getJustRecentResearchPathFile(), articleSearchTemplateObject);
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
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getJustRecentResearchPathFile(), articleSearchTemplateObject);

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
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getJustRecentResearchPathFile(), articleSearchTemplateObject);
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
    public HashMap commonWordsAtTitleForSearchWithoutParameters(int limitResult) throws Exception {
        ArrayList<Article> differentArticlesFromGeneralSearch = separateArticlesExclusiveForSearchWithoutParameters();

        List<String> titles = articlesDescriptionProcessor.getPropertyStringList(differentArticlesFromGeneralSearch, Article::getTitle);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(titles," ",true, true);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);
        System.out.println(rankedPropertyMap);
        return rankedPropertyMap;
    }

    /**
     * Quais autores são mais frequentes, nos resultados exclusidos, da busca geral?
     */
    public abstract void statisticsFromAuthorsForSearchWithoutParameters() ;
    public HashMap statisticsFromAuthorsForSearchWithoutParameters(int limitResult) {
        ArrayList<Article> differentArticlesFromGeneralSearch = separateArticlesExclusiveForSearchWithoutParameters();

        List<String> authors = articlesDescriptionProcessor.getPropertyStringList(differentArticlesFromGeneralSearch, Article::getAuthors);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(authors,",",false,false);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);

        System.out.println(rankedPropertyMap);
        return rankedPropertyMap;
    }

    /**
     * Quais as artigos vieram apenas na busca parametrizada por ano?
     * @throws Exception
     */
    public abstract void differenceForSearchFilteredByYear() throws Exception ;
    public void differenceForSearchFilteredByYear(int expectedQuantityOfArticles) throws Exception {
        ArticleSearchTemplateObject articleSearchTemplateObject = new ArticleSearchTemplateObject().withAuthors()
                .withTitle().withYear().withSource().withPublisher().withCiteQuantity().withGSRank().withArticleURL();
        List<Article> articles = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), articleSearchTemplateObject);
        List<Article> articles_2012 = csvdao.loadArticlesFromCSV(getJustRecentResearchPathFile(), articleSearchTemplateObject);

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
    public HashMap commonWordsAtTitleForSearchFilteredByYear(int limitResult) throws Exception {
        ArrayList<Article> differentArticlesForSearchFilteredByYear = separateArticlesExclusiveForSearchFilteredByYear();
        List<String> titles = articlesDescriptionProcessor.getPropertyStringList(differentArticlesForSearchFilteredByYear, Article::getTitle);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(titles," ",true, true);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);
        System.out.println(rankedPropertyMap);
        return rankedPropertyMap;
    }

    /**
     * Quais autores são mais frequentes, nos resultados exclusidos, da busca parametrizada por ano?
     */
    public abstract void statisticsFromAuthorsForSearchFilteredByYear() ;
    public HashMap statisticsFromAuthorsForSearchFilteredByYear(int limitResult) {
        ArrayList<Article> differentArticlesForSearchFilteredByYear = separateArticlesExclusiveForSearchFilteredByYear();

        List<String> authors = articlesDescriptionProcessor.getPropertyStringList(differentArticlesForSearchFilteredByYear, Article::getAuthors);
        Map<String, Long> namesOccurrences = processor.splitAndCountWordsOccurrences(authors,",",false,false);
        final HashMap rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);

        System.out.println(rankedPropertyMap);
        return rankedPropertyMap;
    }

    public abstract void statisticsFromYear();
    public SortedMap<String, Long> statisticsFromYear(int expectedQuantity, int limitResult) {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject().withAuthors().withTitle().withYear();
        List<Article> articleList = csvdao.loadArticlesFromCSV(getGeneralResearchPathFile(), searchTemplateObject);
        ArticlesAssertion.assertThese(articleList).hasQtt(expectedQuantity);

        List<String> years = articlesDescriptionProcessor.getPropertyStringList(articleList, Article::getYear);
        Map<String, Long> namesOccurrences = processor.countWordsOccurrences(years,false,false);
        Map<String, Long> rankedPropertyMap = articlesDescriptionProcessor.getRankedPropertyMap(namesOccurrences.entrySet().stream(), limitResult);

        SortedMap<String, Long> sortedMap = Maps.newTreeMap();
        for (String key :
                rankedPropertyMap.keySet()) {
            sortedMap.put(key,rankedPropertyMap.get(key));
        }
        System.out.println(sortedMap);
        return sortedMap;
        //o crescimento a partir de 2010 pode ser devido a diminuição do custo da tecnologia
    }
}
