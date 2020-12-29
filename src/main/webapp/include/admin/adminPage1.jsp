<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function(){
        $("ul.pagination li.disable a").click(function(){
            return false;
        });
    });
</script>

<nav>
    <ul class="pagination">
        <li <c:if test="${!page.hasPrevious}"> class="disabled"</c:if>>
            <a href="?page.start=0${page.param}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
            <a href="?page.start=${page.start-page.count}${page.param}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage - 1}" varStatus="status">
            <c:if test="${status.count * page.count - page.start <= 20 && status.count * page.count - page.start >= -10}">
                <li <c:if test="${status.index * page.count == page.start}">class="disabled" </c:if>>
                    <a href="?page.start=${status.index * page.count}${page.param}"
                       <c:if test="${status.index * page.count == page.start}">class="current"</c:if>>
                        ${status.count}
                    </a>
                </li>
            </c:if>
        </c:forEach>

        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
            <a href="?page.start=${page.start + page.count}${page.param}" aria-label="Next">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
            <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    </ul>
</nav>

