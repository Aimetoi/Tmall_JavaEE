
<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8" isELIgnored="false" %>

<div class="searchProducts">
    <%-- 判断产品列表是否为空   --%>
    <c:if test="${empty ps}">
        <div class="alert alert-danger">
            <strong>失败！</strong>没有检索到对应产品！
        </div>
    </c:if>

    <c:forEach items="${ps}" var="p">
        <div class="productUnit" price="${p.promotePrice}">
            <a href="foreproduct?pid=${p.id}">
                <img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
            </a>

            <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span>

            <a class="productLink" href="foreproduct?pid=${p.id}">
                    ${fn:substring(p.name, 0, 50)}
            </a>

            <a  class="tmallLink" href="foreproduct?pid=${p.id}">天猫专卖</a>

            <div class="show1 productInfo">
                <span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
                <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
                <span class="wangwang">
					<a class="wangwanglink" href="#nowhere">
						<img src="img/site/wangwang.png">
					</a>
					</span>
            </div>
        </div>
    </c:forEach>
</div>