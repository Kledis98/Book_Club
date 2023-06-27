<%--
  Created by IntelliJ IDEA.
  User: dejvi
  Date: 6/26/2023
  Time: 8:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Book Share</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<h1>Change your entry</h1>
<a href="/home">Back to the shelves.</a>
<br>

<%--@elvariable id="bookEdit" type="javax"--%>
<form:form method="put" action="/books/${bookEdit.id}/update" modelAttribute="bookEdit">

  <p class="form-group">
    <form:label path="title">Title:</form:label>
    <form:input path="title" class="form-control"></form:input>
    <form:errors path="title" class="text-danger"></form:errors>
  </p>
  <p class="form-group">
    <form:label path="author">Author:</form:label>
    <form:input path="author" class="form-control"></form:input>
    <form:errors path="author" class="text-danger"></form:errors>
  </p>
  <p class="form-group">
    <form:label path="thoughts">My thoughts:</form:label>
    <form:input path="thoughts" class="form-control"></form:input>
    <form:errors path="thoughts" class="text-danger"></form:errors>
  </p>
  <button class="btn btn-info">Submit</button>
</form:form>

</body>
</html>
