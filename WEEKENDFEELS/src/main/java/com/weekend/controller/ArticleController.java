package com.weekend.controller;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.weekend.entity.Article;
import com.weekend.service.ArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	
	@Autowired
	 private  ArticleService articleService;
	 
	 
	 
	  @GetMapping("/list")
	    public String getArticles(Model model) {
	        List<Article> articles = articleService.getAllArticles();
	        model.addAttribute("articles", articles);
	        return "articleslist";        
	    }

	    @GetMapping("/new")
	    public String createArticleForm(Model model) {
	        model.addAttribute("article", new Article());
	        return "create-article";  
	    }

	    @PostMapping("/save")
	    public String saveArticle(@ModelAttribute Article article
	                              ) throws IOException {
	      
	        articleService.saveArticle(article);
	        
	        return "redirect:/articles/list";
	    }
	    
	    @GetMapping("/edit/{id}")
	    public String editArticle(@PathVariable Long id, Model model) 
	    {
	        Article article = articleService.findById(id);
	        
	        if (article != null) {
	            model.addAttribute("article", article);
	            return "update-article"; 
	        }
	        return "redirect:/articles/list";
	    }

	    @PostMapping("/edit/{id}")
	    public String updateArticle(@ModelAttribute Article article) {
	        articleService.saveArticle(article);
	        return "redirect:/articles/list";
	    }

	    
	
	    @GetMapping("/delete/{id}")
	    public String deleteArticle(@PathVariable Long id) {
	        articleService.deleteArticle(id);
	        return "redirect:/articles/list";
	    }
}
