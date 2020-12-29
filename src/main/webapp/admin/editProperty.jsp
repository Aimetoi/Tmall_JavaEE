
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>


<html>
<head>
    <title>编辑属性</title>
</head>
<body>
    <div class="workingArea">
        <%--  面包屑导航      --%>
        <ol class="breadcrumb">
            <li>
                <a href="admin_category_list">所有分类</a>
            </li>
            <li>
                <a href="admin_property_list?cid=${p.category.id}">${p.category.name}</a>
            </li>
            <li class="active">
                编辑属性
            </li>
        </ol>

        <%-- 修改属性面板       --%>
        <div class="panel panel-info editDiv">
            <div class="panel-heading">编辑属性</div>
            <div class="panel-body">
                <form method="post" id="editForm" action="admin_property_update" enctype="multipart/form-data">
                    <table class="editTable">
                        <tr>
                            <td>属性名称</td>
                            <td>
                                <input id="name" name="name" class="form-control" type="text" value="${p.name}">
                            </td>
                        </tr>
                        <tr class="submitTR">
                            <td colspan="2" align="center">
                                <button type="submit" class="btn btn-success">提 交</button>
                                <input type="hidden" name="id" value="${p.id}">
                                <input type="hidden" name="cid" value="${p.category.id}">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
<%@ include file="../include/admin/adminFooter.jsp"%>
</html>
