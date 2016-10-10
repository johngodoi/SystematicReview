
package br.unifesp.henrique.john.research.systematic.review.articles;

import br.unifesp.henrique.john.research.systematic.review.assertion.articles.ArticlesAssertion;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Lab-Eletrofisio on 14/06/2016.
 */
public class ArticlesCSVDAOTest {

    private ArticlesCSVDAO articlesCSVDAO;

    @Before
    public void setUp() throws Exception {
        articlesCSVDAO = new ArticlesCSVDAO();
    }

    @Test
    public void loadArticlesPublishedAfterASpecificYear() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().withYear().whereSmallestPublishedYear(2004);
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(4);
    }

    @Test
    public void loadArticlesPublishedBeforeASpecificYear() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().withYear().whereBiggerPublishedYear(2004);
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(3);
    }

    @Test
    public void loadArticlesPublishedInAPeriod() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().withYear().whereSmallestPublishedYear(2004).whereBiggerPublishedYear(2004);
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2);
    }

    @Test
    public void loadArticleWhereTitleContainsASpecificString() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereTitleContainsAllStrings("language");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();
    }

    @Test
    public void loadArticleWhereTitleContainsMultipleStrings() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereTitleContainsAllStrings("language","object");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(1).assertArticleOfIndex(0)
                .hasAuthors("M Fowler").hasTitle("UML distilled: a brief guide to the standard object modeling language").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();
    }

    @Test
    public void loadArticleWhereTitleContainsSomeStrings() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereTitleContainsSomeStrings("language","object");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();;
    }

    @Test
    public void loadArticleWhereTitleContainsSomeStringsIgnoreCase() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereTitleContainsSomeStrings("Language","Object");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();;
    }

    @Test
    public void loadArticleWhereAuthorsContainsASpecificString() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereAuthorsContainsAllStrings("Booch");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(1).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();
    }

    @Test
    public void loadArticleWhereAuthorsContainsMultipleStrings() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereAuthorsContainsAllStrings("M","Fowler");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(1).assertArticleOfIndex(0)
                .hasAuthors("M Fowler").hasTitle("UML distilled: a brief guide to the standard object modeling language").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();
    }

    @Test
    public void loadArticleWhereAuthorsContainsSomeStrings() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereAuthorsContainsSomeStrings("Booch","Fowler");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();;
    }

    @Test
    public void loadArticleWhereAuthorsContainsSomeStringsIgnoreCase() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().whereAuthorsContainsSomeStrings("booch","fowler");
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(2).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();;
    }

    @Test
    public void loadAllArticlesFromCSV() throws Exception {
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv");
        ArticlesAssertion.assertThese(articles).hasQtt(5).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasPublisher("Pearson Education India")
                .hasSource("").hasYear("2005").hasArticleURL("").hasGSRank(742)
                .hasCitesQtt(11303);
    }

    @Test
    public void loadArticlesWithSpecificColumns() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject().withAuthors();
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(5).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasNotTitle().hasNotPublisher()
                .hasNotSource().hasNoYear().hasNotArticleURL().hasNotGSRank()
                .hasNotCitesQtt();
    }

    @Test
    public void loadArticlesWithAllColumns() throws Exception {
        ArticleSearchTemplateObject searchTemplateObject = new ArticleSearchTemplateObject()
                .withAuthors().withTitle().withPublisher().withSource().withYear().withArticleURL().withGSRank().withCiteQuantity().withCiteURL();
        List<Article> articles = articlesCSVDAO.loadArticlesFromCSV("./src/test/resources/articles.csv", searchTemplateObject);
        ArticlesAssertion.assertThese(articles).hasQtt(5).assertArticleOfIndex(0)
                .hasAuthors("G Booch").hasTitle("The unified modeling language user guide").hasPublisher("Pearson Education India")
                .hasSource("").hasYear("2005").hasArticleURL("").hasGSRank(742)
                .hasCitesQtt(11303);
    }
}
