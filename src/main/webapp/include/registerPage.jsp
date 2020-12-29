
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<title>账号注册</title>

<script>
    $(function () {

        <c:if test="${!empty msg}">
            $("span.errorMessage").html("${msg}");
            $("div.registerErrorMessageDiv").css("visibility", "visible");
        </c:if>

        $(".registerForm").submit(function () {
            if(0 == $("#name").val().length) {
                $("span.errorMessage").html("请输入用户名");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            if(0 == $("#password").val().length) {
                $("span.errorMessage").html("请输入密码");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            if(0 == $("#repeatPassword").val().length) {
                $("span.errorMessage").html("请输入重复密码");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            if($("#password").val() != $("#repeatPassword").val()) {
                $("span.errorMessage").html("密码不一致，请重新输入");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            return true;
        });
    });
</script>

<form method="post" action="foreregister" class="registerForm" enctype="multipart/form-data">
    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>

        <table class="registerTable" align="center">
            <tr>
                <td class="registerTip registerTableLeftTD">设置会员名</td>
                <td></td>
            </tr>

            <tr>
                <td class="registerTableLeftTD">登录名</td>
                <td class="registerTableRightTD">
                    <input type="text" id="name" name="name" placeholder="会员名设置成功，无法修改">
                </td>
            </tr>

            <tr>
                <td class="registerTip registerTableLeftTD">设置登陆密码</td>
                <td class="registerTableRightTD">登陆时验证，保护用户信息</td>
            </tr>

            <tr>
                <td class="registerTableLeftTD">登陆密码</td>
                <td class="registerTableRightTD">
                    <input id="password" name="password" type="password" placeholder="设置你的登陆密码">
                </td>
            </tr>

            <tr>
                <td class="registerTableLeftTD">确认密码</td>
                <td class="registerTableRightTD">
                    <input id="repeatPassword" name="repeatPassword" type="password" placeholder="请再次确认密码">
                </td>
            </tr>

            <tr>
                <td colspan="2" class="registerButtonTD">
                    <button type="submit">提 交</button>
                </td>
            </tr>
        </table>
    </div>
</form>
