package br.unifesp.henrique.john.research.systematic.review.articles;

/**
 * Created by Lab-Eletrofisio on 20/06/2016.
 */
public class ArticleSearchTemplateObject {
    private boolean withAuthors;
    private boolean withTitle;
    private boolean withPublisher;
    private boolean withSource;
    private boolean withYear;
    private boolean withArticleURL;
    private boolean withGSRank;
    private boolean withCiteQuantity;
    private boolean withCiteURL;
    private String[] titleContainsAll;
    private String[] titleContainsSome;
    private String[] authorsContainsAll;
    private String[] authorsContainsSome;
    private Integer smallestPublishedYear;
    private Integer biggerPublishedYear;

    public ArticleSearchTemplateObject withAuthors() {
        this.withAuthors = true;
        return this;
    }

    public ArticleSearchTemplateObject withTitle() {
        this.withTitle = true;
        return this;
    }

    public ArticleSearchTemplateObject withPublisher() {
        this.withPublisher = true;
        return this;
    }

    public ArticleSearchTemplateObject withSource() {
        this.withSource = true;
        return this;
    }

    public ArticleSearchTemplateObject withYear() {
        this.withYear = true;
        return this;
    }


    public ArticleSearchTemplateObject withArticleURL() {
        this.withArticleURL = true;
        return this;
    }

    public ArticleSearchTemplateObject withGSRank() {
        this.withGSRank = true;
        return this;
    }

    public ArticleSearchTemplateObject withCiteQuantity() {
        this.withCiteQuantity = true;
        return this;
    }

    public ArticleSearchTemplateObject withCiteURL() {
        this.withCiteURL = true;
        return this;
    }

    public boolean isWithCiteQuantity() {
        return withCiteQuantity;
    }

    public boolean isWithAuthors() {
        return withAuthors;
    }

    public boolean isWithTitle() {
        return withTitle;
    }

    public boolean isWithYear() {
        return withYear;
    }

    public boolean isWithSource() {
        return withSource;
    }

    public boolean isWithPublisher() {
        return withPublisher;
    }

    public boolean isWithArticleURL() {
        return withArticleURL;
    }

    public boolean isWithCiteURL() {
        return withCiteURL;
    }

    public boolean isWithGSRank() {
        return withGSRank;
    }

    public ArticleSearchTemplateObject whereTitleContainsAllStrings(String ...strings) {
        this.titleContainsAll = strings;
        return this;
    }

    public ArticleSearchTemplateObject whereTitleContainsSomeStrings(String ...strings) {
        this.titleContainsSome = strings;
        return this;
    }

    public ArticleSearchTemplateObject whereAuthorsContainsAllStrings(String ...strings) {
        this.authorsContainsAll = strings;
        return this;
    }

    public ArticleSearchTemplateObject whereAuthorsContainsSomeStrings(String ...strings) {
        this.authorsContainsSome = strings;
        return this;
    }

    public ArticleSearchTemplateObject whereSmallestPublishedYear(Integer year) {
        this.smallestPublishedYear = year;
        return this;
    }

    public ArticleSearchTemplateObject whereBiggerPublishedYear(Integer year) {
        this.biggerPublishedYear = year;
        return this;
    }

    public String[] whereTitleContainsAllStrings() {
        return this.titleContainsAll;
    }

    public String[] whereTitleContainsSomeStrings() {
        return this.titleContainsSome;
    }

    public String[] whereAuthorsContainsAllStrings() {
        return this.authorsContainsAll;
    }

    public String[] whereAuthorsContainsSomeStrings() {
        return this.authorsContainsSome;
    }

    public Integer whereSmallestPublishedYear() {
        return this.smallestPublishedYear;
    }

    public Integer whereBiggerPublishedYear() {
        return this.biggerPublishedYear;
    }
}
