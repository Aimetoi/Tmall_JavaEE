
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<div class="categoryMenu">
    <c:forEach items="${cs}" var="c">
        <div cid="${c.id}" class="eachCategory">
            <span class="glyphicon glyphicon-link" style="color: white"></span>
            <a href="forecategory?cid=${c.id}">
                ${c.name}
            </a>
        </div>
    </c:forEach>
</div>
