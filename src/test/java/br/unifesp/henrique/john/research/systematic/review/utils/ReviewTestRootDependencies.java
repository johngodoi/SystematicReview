package br.unifesp.henrique.john.research.systematic.review.utils;

import br.unifesp.henrique.john.research.datascience.words.WordProcessor;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesCSVDAO;
import br.unifesp.henrique.john.research.systematic.review.articles.ArticlesDescriptionProcessor;

/**
 * Created by Lab-Eletrofisio on 01/08/2016.
 */
public interface ReviewTestRootDependencies {
    ArticlesDescriptionProcessor getArticlesDescriptionProcessor();

    ArticlesCSVDAO getArticlesCSVDAO();

    WordProcessor getWordProcessor();
}
