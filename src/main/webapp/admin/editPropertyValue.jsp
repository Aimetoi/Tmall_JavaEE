
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<html>
<head>
    <title>编辑产品属性值</title>
</head>
<script>
    $(function () {
        $("input.pvValue").keyup(function(){
            var value = $(this).val();
            var page = "admin_product_updatePropertyValue";
            var pvid = $(this).attr("pvid");
            var parentSpan = $(this).parent("span");
            parentSpan.css("border", "1px solid yellow");
            $.post(
                page,
                {"value":value, "pvid":pvid},
                function(result){
                    if("success" == result)
                        parentSpan.css("border", "1px solid green");
                    else
                        parentSpan.css("border", "1px solid red");
                }
            );
        });
    });
</script>
<body>
    <div class="workingArea">
        <%--  面包屑导航      --%>
        <ol class="breadcrumb">
            <li>
                <a href="admin_category_list">所有分类</a>
            </li>
            <li>
                <a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a>
            </li>
            <li class="active">${p.name}</li>
            <li class="active">编辑产品属性</li>
        </ol>

        <%-- 数据显示区域       --%>
        <div class="editPVDiv">
            <c:forEach items="${pvs}" var="pv">
                <div class="eachPV">
                    <span class="pvName">${pv.property.name}</span>
                    <span class="pvValue">
                        <input class="pvValue" pvid="${pv.id}" type="text" value="${pv.value}">
                    </span>
                </div>
            </c:forEach>

<%--            <div class="clear:both"></div>--%>
        </div>
    </div>
</body>
<%-- 调整页脚css样式 --%>
<%@include file="../include/admin/adminFooter.jsp"%>
<style>
    .back-footer{
        margin-top: 500px;
    }
</style>
</html>
