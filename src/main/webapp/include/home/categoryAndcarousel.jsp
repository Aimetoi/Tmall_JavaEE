
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    function showProductAsideCategorys(cid) {
        $("div.eachCategory[cid=" + cid + "]").css("background-color", "white");
        $("div.eachCategory[cid=" + cid + "] a").css("color", "#87CEFA");
        $("div.productsAsideCategorys[cid=" + cid + "]").show();
    }

    function hideProductAsideCategory(cid) {
        $("div.eachCategory[cid=" + cid + "]").css("background-color", "#e2e2e3");
        $("div.eachCategory[cid=" + cid + "] a").css("color", "#000");
        $("div.productsAsideCategorys[cid=" + cid + "]").hide();
    }
    $(function () {
        $("div.eachCategory").mouseenter(function(){
            var cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });
        $("div.eachCategory").mouseleave(function(){
            var cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });
        $("div.productsAsideCategorys").mouseenter(function(){
            var cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });
        $("div.productsAsideCategorys").mouseleave(function(){
            var cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });

        $("div.rightMenu span").mouseenter(function(){
            var left = $(this).position().left;
            var top = $(this).position().top;
            var width = $(this).css("width");
            var destLeft = parseInt(left) + parseInt(width)/2;
            $("img#catear").css("left",destLeft);
            $("img#catear").css("top",top - 20);
            $("img#catear").fadeIn(500);

        });
        $("div.rightMenu span").mouseleave(function(){
            $("img#catear").hide();
        });
    });
</script>

<img src="img/site/catear.png" id="catear" class="catear">

<div class="categoryWithCarousel">
    <div class="headbar">
        <div class="head">
            <span style="margin-left: 10px" class="glyphicon glyphicon-th-list"></span>
            <span style="margin-left: 10px">商品分类</span>
        </div>

        <div class="rightMenu">
            <span><a class="nowhere" href="#nowhere"><img src="img/site/tmallmarket.png"/></a></span>
            <span><a class="nowhere" href="#nowhere"><img src="img/site/tmallinternational.png"/></a></span>

            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count<=5}">
                    <span>
                        <a href="forecategory?cid=${c.id}">
                            ${c.name}
                        </a>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </div>

    <div style="position: absolute; left: 45px">
        <%@ include file="categoryMenu.jsp"%>
    </div>

    <div>
        <%@ include file="carousel.jsp"%>
    </div>

</div>

