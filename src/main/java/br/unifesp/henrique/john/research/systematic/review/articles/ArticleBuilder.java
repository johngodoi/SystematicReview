package br.unifesp.henrique.john.research.systematic.review.articles;

import org.apache.commons.csv.CSVRecord;

public class ArticleBuilder {

     Article mountCompletedArticle(CSVRecord csvRecord) {
        Article article = new Article();
        article.setCiteQuantity(csvRecord.get(ArticleCSVFileHeader.CITES));
        article.setAuthors(csvRecord.get(ArticleCSVFileHeader.AUTHORS));
        article.setTitle(csvRecord.get(ArticleCSVFileHeader.TITLE));
        article.setYear(csvRecord.get(ArticleCSVFileHeader.YEAR));
        article.setSource(csvRecord.get(ArticleCSVFileHeader.SOURCE));
        article.setPublisher(csvRecord.get(ArticleCSVFileHeader.PUBLISHER));
        article.setArticleURL(csvRecord.get(ArticleCSVFileHeader.ARTICLE_URL));
        article.setCitesURL(csvRecord.get(ArticleCSVFileHeader.CITES_URL));
        article.setGSRank(csvRecord.get(ArticleCSVFileHeader.GSRANK));
        return article;
    }

    Article mountArticleBySelectedProperties(ArticleSearchTemplateObject searchTemplateObject, CSVRecord csvRecord) {
        Article article = new Article();
        if (searchTemplateObject.isWithCiteQuantity()) {
            article.setCiteQuantity(csvRecord.get(ArticleCSVFileHeader.CITES));
        }
        if (searchTemplateObject.isWithAuthors()) {
            article.setAuthors(csvRecord.get(ArticleCSVFileHeader.AUTHORS));
        }
        if (searchTemplateObject.isWithTitle()) {
            article.setTitle(csvRecord.get(ArticleCSVFileHeader.TITLE));
        }
        if (searchTemplateObject.isWithYear()) {
            article.setYear(csvRecord.get(ArticleCSVFileHeader.YEAR));
        }
        if (searchTemplateObject.isWithSource()) {
            article.setSource(csvRecord.get(ArticleCSVFileHeader.SOURCE));
        }
        if (searchTemplateObject.isWithPublisher()) {
            article.setPublisher(csvRecord.get(ArticleCSVFileHeader.PUBLISHER));
        }
        if (searchTemplateObject.isWithArticleURL()) {
            article.setArticleURL(csvRecord.get(ArticleCSVFileHeader.ARTICLE_URL));
        }
        if (searchTemplateObject.isWithCiteURL()) {
            article.setCitesURL(csvRecord.get(ArticleCSVFileHeader.CITES_URL));
        }
        if (searchTemplateObject.isWithGSRank()) {
            article.setGSRank(csvRecord.get(ArticleCSVFileHeader.GSRANK));
        }
        return article;
    }
}