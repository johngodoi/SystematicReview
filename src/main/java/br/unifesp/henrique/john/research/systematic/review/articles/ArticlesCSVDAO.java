package br.unifesp.henrique.john.research.systematic.review.articles;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lab-Eletrofisio on 14/06/2016.
 */
public class ArticlesCSVDAO {

    private final ArticleBuilder articleBuilder = new ArticleBuilder();

    public List<Article> loadArticlesFromCSV(String filename) {
        return loadArticlesFromCSV(filename,null);
    }

    public List<Article> loadArticlesFromCSV(String filename, ArticleSearchTemplateObject searchTemplateObject) {

        ArrayList<Article> articles = Lists.newArrayList();
        try {
            List<CSVRecord> csvRecords = retrieveCSVRecords(filename);
            //first record is the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord csvRecord = csvRecords.get(i);
                Article article;
                if(searchTemplateObject==null) {
                    article = articleBuilder.mountCompletedArticle(csvRecord);
                } else {
                    article = articleBuilder.mountArticleBySelectedProperties(searchTemplateObject, csvRecord);
                }
                if(!articles.contains(article) && isAccepted(article, searchTemplateObject)) {
                    articles.add(article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    private List<CSVRecord> retrieveCSVRecords(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(ArticleCSVFileHeader.FILE_HEADER_MAPPING);
        CSVParser csvFileParser = new CSVParser(reader, csvFileFormat);
        return csvFileParser.getRecords();
    }

    private boolean isAccepted(Article article, ArticleSearchTemplateObject searchTemplateObject){
        boolean accept = true;
        if(searchTemplateObject == null) return accept;

        if(shouldProceed(accept,searchTemplateObject.whereTitleContainsAllStrings())){
            accept = accept && propertyContainsAllStrings(searchTemplateObject.whereTitleContainsAllStrings(), article.getTitle());
        }
        if(shouldProceed(accept,searchTemplateObject.whereTitleContainsSomeStrings())){
            accept = accept && propertyContainsSomeStrings(searchTemplateObject.whereTitleContainsSomeStrings(), article.getTitle());
        }
        if(shouldProceed(accept,searchTemplateObject.whereAuthorsContainsAllStrings())){
            accept = accept && propertyContainsAllStrings(searchTemplateObject.whereAuthorsContainsAllStrings(), article.getAuthors());
        }
        if(shouldProceed(accept,searchTemplateObject.whereAuthorsContainsSomeStrings())){
            accept = accept && propertyContainsSomeStrings(searchTemplateObject.whereAuthorsContainsSomeStrings(), article.getAuthors());
        }
        if(shouldProceed(accept,searchTemplateObject.whereSmallestPublishedYear())){
            accept = accept && (Integer.valueOf(article.getYear()) >= searchTemplateObject.whereSmallestPublishedYear());
        }
        if(shouldProceed(accept,searchTemplateObject.whereBiggerPublishedYear())){
            accept = accept && (Integer.valueOf(article.getYear()) <= searchTemplateObject.whereBiggerPublishedYear());
        }
        return accept;
    }

    private boolean shouldProceed(boolean accept, Object restriction){
        return accept && (restriction!=null);
    }

    private boolean propertyContainsAllStrings(String[] strings, String title) {
        boolean containsString = true;
        if(strings !=null){
            for (String string : strings) {
                containsString = containsString && title.toLowerCase().contains(string.toLowerCase());
            }
        }
        return containsString;
    }

    private boolean propertyContainsSomeStrings(String[] strings, String title) {
        boolean containsString = false;
        if(strings !=null){
            for (String string : strings) {
                containsString = containsString || title.toLowerCase().contains(string.toLowerCase());
            }
        }
        return containsString;
    }

    public boolean writeToCSV(String articlesToWritePath, List<Article> articlesToWrite) {
        //TODO
        throw new NotImplementedException();
        //return true;
    }
}
