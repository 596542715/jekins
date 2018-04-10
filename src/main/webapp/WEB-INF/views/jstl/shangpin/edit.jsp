<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<c:url value="/styles/main.css" />" type="text/css"
    rel="stylesheet" />
<title>编辑商品</title>
<base href="<c:url value="/" />" />
</head>
<body>
    <div class="main">
        <h2 class="title">
            <span>编辑商品</span>
        </h2>
        <form:form action="shangpin/editSave" modelAttribute="entity">
            <fieldset>
                <legend>商品</legend>
               <p>
                <label for="name">商品名称：</label>
                <form:input path="name" size="50"/>
                <form:errors path="name" cssClass="error"></form:errors>
            </p>
            <p>
                <label for="price_low">最低价：</label>
                <form:input path="price_low"/>
                <form:errors path="price_low" cssClass="error"></form:errors>
            </p>
            <p>
                <label for="price_ave">平均价：</label>
                <form:input path="price_ave"/>
                <form:errors path="price_ave" cssClass="error"></form:errors>
            </p>
            <p>
                <label for="price_hight">最高价：</label>
                <form:input path="price_hight"/>
                <form:errors path="price_hight" cssClass="error"></form:errors>
            </p>
            <p>
                <label for="spec">规格：</label>
                <form:input path="spec"/>
                <form:errors path="spec" cssClass="error"></form:errors>
            </p>
            <p>
                <label for="unit">单位：</label>
                <form:input path="unit"/>
                <form:errors path="unit" cssClass="error"></form:errors>
            </p>
             <p>
                <label for="catalog_name">商品类型：</label>
                <form:input path="catalog_name"/>
                <form:errors path="catalog_name" cssClass="error"></form:errors>
            </p>
                <p>
                    <form:hidden path="public_date" />
                    <form:hidden path="id" />
                    <input type="submit" value="保存" class="btn out">
                </p>
            </fieldset>
        </form:form>
        <p style="color: red">${message}</p>
        <form:errors path="*"></form:errors>
        <p>
            <a href="<c:url value="/shangpin/list" />" class="abtn out">返回列表</a>
        </p>
    </div>
</body>
</html>