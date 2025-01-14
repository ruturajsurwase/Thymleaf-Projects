package com.weekend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weekend.entity.Article;
import com.weekend.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private  ArticleRepository articleRepository;
	
	
	
	
	public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

	public Article findById(Long id) {
		 return articleRepository.findById(id).orElse(null);
	}



	public void deleteArticle(Long id) {
	      articleRepository.deleteById(id);
		
	}
}
