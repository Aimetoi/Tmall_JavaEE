
<%@ page contentType="text/html;charset=UTF-8" language="java" 
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<html>
<script>
    $(function () {
        $("#editForm").submit(function () {
            if(!checkEmpty("password", "新密码"))
                return false;
            return true;
        });
    });
</script>
<head>
    <title>修改密码</title>
</head>
<body>
    <div class="workingArea">
        <%--  面包屑导航      --%>
        <ol class="breadcrumb">
            <li>
                <a href="admin_user_list">所有用户</a>
            </li>
            <li class="active">${u.name}</li>
            <li class="active">修改密码</li>
        </ol>

        <%--  修改密码面板      --%>
        <div class="panel panel-info editDiv">
            <div class="panel-heading">修改密码</div>
            <div class="panel-body">
                <form method="post" id="editForm" action="admin_user_update" enctype="multipart/form-data">
                    <table class="editTable">
                        <tr>
                            <td>新密码</td>
                            <td>
                                <input id="password" name="password" class="form-control">
                                <input type="hidden" name="id" value="${u.id}">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <button type="submit" class="btn btn-success">提 交</button>
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
