<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="../header.jsp"></c:import>
<section class="article-header">
    <div class="container">
        <div class="article-heading">${article.title}</div>
    </div>
</section>
<div>
    <div class="container">
        <div class="article-content">
            ${article.content}
        </div>
        <div class="article-post-comment">
            <form:form class="form form-comment" method="POST" action="article/comment" modelAttribute="comment" id="form_comment">
                <form:input path="articleId" type="hidden"/>
                <form:textarea class="form-control" id="comment" path="comment" placeholder="What's on your mind..."></form:textarea>
                <button class="btn btn-md btn-primary btn-block" type="submit">Post Comment</button>
            </form:form>
        </div>
        <div class="article-comments">
            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <div class="user-image"></div>
                    <div class="user-comment">
                        <div class="comment-text">${comment.comment}</div>
                        <div class="comment-time">${comment.createdAt}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script>
 $(document).ready(function(){
    $("#form_comment").bind("submit", function(e){
        e.preventDefault();
        console.log("LMAO");
    })
 });
</script>
<c:import url="../footer.jsp"></c:import>