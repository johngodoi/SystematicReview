package br.unifesp.henrique.john.research.systematic.review.articles;

/**
 * Created by Lab-Eletrofisio on 14/06/2016.
 */
public class Article {
    private String authors;
    private String title;
    private String publisher;
    private String source;
    private String year;
    private String articleURL;
    private Integer GSRank;
    private Integer citeQuantity;
    private String citesURL;

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSource() {
        return source;
    }

    public String getYear() {
        return year;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public Integer getGSRank() {
        return GSRank;
    }

    public Integer getCiteQuantity() {
        return citeQuantity;
    }

    public void setCiteQuantity(String citeQuantity) {
        this.citeQuantity = Integer.parseInt(citeQuantity);
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public void setGSRank(String GSRank) {
        this.GSRank = Integer.parseInt(GSRank);
    }

    public String getCitesURL() {
        return citesURL;
    }

    public void setCitesURL(String citesURL) {
        this.citesURL = citesURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (getAuthors() != null ? !getAuthors().equals(article.getAuthors()) : article.getAuthors() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(article.getTitle()) : article.getTitle() != null) return false;
        if (getPublisher() != null ? !getPublisher().equals(article.getPublisher()) : article.getPublisher() != null)
            return false;
        if (getSource() != null ? !getSource().equals(article.getSource()) : article.getSource() != null) return false;
        return getYear() != null ? getYear().equals(article.getYear()) : article.getYear() == null;

    }

    @Override
    public int hashCode() {
        int result = getAuthors() != null ? getAuthors().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getPublisher() != null ? getPublisher().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", source='" + source + '\'' +
                ", year='" + year + '\'' +
                ", articleURL='" + articleURL + '\'' +
                ", GSRank=" + GSRank +
                ", citeQuantity=" + citeQuantity +
                ", citesURL='" + citesURL + '\'' +
                '}';
    }
}
