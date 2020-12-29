
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<html>
<head>
    <title>用户管理</title>
</head>
<body>
    <div class="workingArea">
        <h1 class="label label-info">用户管理</h1>

        <br>
        <br>

        <div class="listDataTableDiv">
            <table class="table table-striped table-hover table-bordered table-condensed">
                <thead>
                    <tr class="success">
                        <th>ID</th>
                        <th>用户名称</th>
                        <th>密码</th>
                        <th>修改密码</th>
                        <th>删除用户</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${us}" var="u">
                        <tr>
                            <td>${u.id}</td>
                            <td>${u.name}</td>
                            <td>${u.password}</td>

                            <td>
                                <a href="admin_user_edit?id=${u.id}">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </a>
                            </td>
                            <td>
                                <a deleteLink="true" href="admin_user_delete?id=${u.id}">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="pageDiv">
            <%@ include file="../include/admin/adminPage.jsp"%>
        </div>
    </div>
</body>
<%@ include file="../include/admin/adminFooter.jsp"%>
</html>
