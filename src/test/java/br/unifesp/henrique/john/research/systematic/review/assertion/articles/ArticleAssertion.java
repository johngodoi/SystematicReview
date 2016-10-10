package br.unifesp.henrique.john.research.systematic.review.assertion.articles;

import br.unifesp.henrique.john.research.systematic.review.articles.Article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Lab-Eletrofisio on 14/06/2016.
 */
public class ArticleAssertion {
    private final Article article;

    public ArticleAssertion(Article article) {
        this.article = article;
    }

    public ArticleAssertion hasAuthors(String authors) {
        assertEquals("Autores",authors,this.article.getAuthors());
        return this;
    }

    public ArticleAssertion hasTitle(String title) {
        assertEquals("Título",title, this.article.getTitle());
        return this;
    }

    public ArticleAssertion hasPublisher(String publisher) {
        assertEquals("Publicador", publisher, this.article.getPublisher());
        return this;
    }

    public ArticleAssertion hasSource(String source) {
        assertEquals("Fonte",source,this.article.getSource());
        return this;
    }

    public ArticleAssertion hasYear(String year) {
        assertEquals("Ano",year,this.article.getYear());
        return this;
    }

    public ArticleAssertion hasArticleURL(String url) {
        assertEquals("URL do artigo", url, this.article.getArticleURL());
        return this;
    }

    public ArticleAssertion hasGSRank(Integer position) {
        assertEquals("GS Rank", position, this.article.getGSRank());
        return this;
    }

    public ArticleAssertion hasCitesQtt(Integer qtt) {
        assertEquals("Quantidade de citações",qtt, this.article.getCiteQuantity());
        return this;
    }

    public ArticleAssertion hasNotTitle() {
        assertNull("Não deveria ter título",this.article.getTitle());
        return this;
    }

    public ArticleAssertion hasNotPublisher() {
        assertNull("Não deveria ter publicador", this.article.getPublisher());
        return this;
    }

    public ArticleAssertion hasNotSource() {
        assertNull("Não deveria ter fonte", this.article.getSource());
        return this;
    }

    public ArticleAssertion hasNoYear() {
        assertNull("Não deveria ter ano", this.article.getYear());
        return this;
    }


    public ArticleAssertion hasNotArticleURL() {
        assertNull("Não deveria ter url do artigo", this.article.getArticleURL());
        return this;
    }

    public ArticleAssertion hasNotGSRank() {
        assertNull("Não deveria ter GS Rank", this.article.getGSRank());
        return this;
    }

    public ArticleAssertion hasNotCitesQtt() {
        assertNull("Não deveria ter quantidade de citações", this.article.getCiteQuantity());
        return this;
    }
}
