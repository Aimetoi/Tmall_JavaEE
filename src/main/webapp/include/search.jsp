
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<a href="${contextPath}">
    <img id="logo" src="img/site/logo2.gif" class="logo" style="width: 240px;height: 130px">
</a>

<form action="foresearch" method="get" >
    <div class="searchDiv">
        <input name="keyword" class="searchInput" type="text" value="${param.keyword}" placeholder="时尚男鞋  太阳镜 ">
        <button  type="submit" class="searchButton">搜 索</button>
    </div>
</form>


