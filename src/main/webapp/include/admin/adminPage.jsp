<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
        $("ul.pagination li.disabled a").click(function(){
            return false;
        });
    });

</script>

<nav>
    <ul class="pagination">
        <li>
            <a  href="?page.start=0${page.param}" aria-label="Previous" >
                <span aria-hidden="true">«</span>
            </a>
        </li>

        <li >
            <a  href="?page.start=${page.start-page.count}${page.param}" aria-label="Previous" >
                <span aria-hidden="true">‹</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
            <li>
                <a href="?page.start=${status.index*page.count}${page.param}" class="current">${status.count}</a>
            </li>

        </c:forEach>

        <li >
            <a href="?page.start=${page.start+page.count}${page.param}" aria-label="Next">
                <span aria-hidden="true">›</span>
            </a>
        </li>
        <li >
            <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
    </ul>
</nav>