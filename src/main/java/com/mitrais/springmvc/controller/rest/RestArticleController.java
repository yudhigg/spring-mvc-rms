package com.mitrais.springmvc.controller.rest;

import com.mitrais.springmvc.model.Article;
import com.mitrais.springmvc.model.Comment;
import com.mitrais.springmvc.model.response.ArticleDetailResponse;
import com.mitrais.springmvc.model.response.ArticleResponse;
import com.mitrais.springmvc.model.response.FormResponse;
import com.mitrais.springmvc.service.ArticleService;
import com.mitrais.springmvc.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class RestArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/api/article")
    public ArticleResponse list() {
        List<Article> articles = articleService.list();
        List<ArticleResponse.Data> articlesData = new ArrayList<>();
        for (Article article : articles) {
            ArticleResponse.Data data = new ArticleResponse.Data();
            data.setId(article.getId());
            data.setTitle(article.getTitle());
            data.setContent(article.getContent());
            articlesData.add(data);
        }

        ArticleResponse response = new ArticleResponse();
        response.setData(articlesData);
        response.setMessage("Ok");

        return response;
    }

    @GetMapping("/api/article/{id}")
    public ArticleDetailResponse view(@PathVariable("id") int id) {
        Article article = articleService.get(id);
        ArticleDetailResponse.Data data = new ArticleDetailResponse.Data();
        data.setId(article.getId());
        data.setTitle(article.getTitle());
        data.setContent(article.getContent());

        List<ArticleDetailResponse.Comments> comments = new ArrayList<>();
        for (Comment comment : article.getComments()) {
            ArticleDetailResponse.Comments commentData = new ArticleDetailResponse.Comments();
            commentData.setId(comment.getId());
            commentData.setComment(comment.getComment());
            commentData.setCretedAt(comment.getCreatedAt().toString());
            commentData.setUsername(comment.getUser().getUsername());
            comments.add(commentData);
        }
        data.setComments(comments);

        ArticleDetailResponse response = new ArticleDetailResponse();
        response.setData(data);
        response.setMessage("Ok");

        return response;
    }

    @RequestMapping(value = "/api/article/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormResponse create(@ModelAttribute("article") Article article, BindingResult result) {
        FormResponse response = new FormResponse();
        ArticleValidator.Create validator = new ArticleValidator.Create();
        validator.validate(article, result);

        if (!result.hasErrors()) {
            articleService.save(article);
            response.setErrorcode(0);
            response.setMessage("Ok");
            System.out.println("Successfully created the article...");
        } else {
            HashMap<String, String> formError = new HashMap<String, String>();
            System.out.println("Failed to update the article...");
            for (FieldError errors : result.getFieldErrors()) {
                formError.put(errors.getField(), errors.getDefaultMessage());
            }
            response.setMessage("Failed");
            response.setErrorcode(1000);
            response.setFormError(formError);
        }

        return response;
    }

    @RequestMapping(value = "/api/article/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormResponse update(@ModelAttribute("article") Article article, BindingResult result) {
        FormResponse response = new FormResponse();
        ArticleValidator.Update validator = new ArticleValidator.Update();
        validator.validate(article, result);

        if (!result.hasErrors()) {
            articleService.update(article.getId(), article);
            response.setMessage("Ok");
            System.out.println("Successfully updated the article...");
        } else {
            HashMap<String, String> formError = new HashMap<String, String>();
            System.out.println("Failed to update the article...");
            for (FieldError errors : result.getFieldErrors()) {
                formError.put(errors.getField(), errors.getDefaultMessage());
            }
            response.setMessage("Failed");
            response.setErrorcode(1000);
            response.setFormError(formError);
        }

        return response;
    }
}
