<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example</title>
</head>
<body>
	<h1>Create Example</h1>
	<form:form method="post" modelAttribute="example">
		<form:errors path="" cssClass="" />
		<form:hidden path="uuid"/>
		
		<fieldset>
			<form:label path="title">Title</form:label>
			<form:input path="title" type="text" required="required" />
			<form:errors path="title" cssClass="" />
		</fieldset>
		
		<fieldSet>
			<form:label path="author">Author</form:label>
			<form:input path="author" type="text" required="reqruied" />
			<form:errors path="author" cssClass="" /> 
		</fieldSet>
		
		<fieldset>
			<form:label path="content">Content</form:label>
			<form:textarea path="content" rows="3" cols="20" required="required"></form:textarea>
		</fieldset>
		
		<button type="submit">Submit</button>
	</form:form>
	
</body>
</html>