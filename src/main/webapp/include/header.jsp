
<%@ page contentType="text/html;charset=UTF-8" language="java"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="css/fore/style.css" rel="stylesheet" type="text/css">
    <%-- 网站图标   --%>
    <link rel="shortcut icon" href="img/site/icon.ico">

    <script>
        function formatMoney(num) {
            num = num.toString().replace(/\$|\,/g);
            if(isNaN(num))
                num = "0";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if(cents < 10) {
                cents = "0" + cents;
            }
            for(var i = 0; i < Math.floor(num.length - (1 + i)); i++) {
                num = num.substring(0, num.length - (4 * i + 3)) + "," +
                        num.substring(num.length - (4 * i + 3));
            }
            return (((sign)?'':'-') + num + "." + cents);
        }

        function checkEmpty(id, name) {
            var value = $("#" + id).val();
            if(value.length == 0) {
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        $(function () {

            // 搜索按钮点击事件
            $("button.searchButton").click(function () {
                var searchText = $("input.searchInput").val();
                if("" == searchText) {
                    alert("请输入要搜索的内容！");
                    return false;
                }
            });

            $("a.productDetailTopReviewLink").click(function () {
                $("div.productReviewDiv").show();
                $("div.productDetailDiv").hide();
            });

            $("a.productReviewTopPartSelectedLink").click(function () {
                $("div.productReviewDiv").hide();
                $("div.productDetailDiv").show();
            });

            $("span.leaveMessageTextareaSpan").hide();

            $("img.leaveMessageImg").click(function () {
                $(this).hide();
                $("span.leaveMessageTextareaSpan").show();
                $("div.orderItemSumDiv").css("height", "100px");
            });

            $("div#footer a[href$=#nowhere]").click(function () {
                alert("模仿天猫链接，并没有到跳转实际页面~");
            });

            $("a.nowhere").click(function () {
                alert("模仿天猫链接，并没有跳转到实际页面~");
            });

            $("a.wangwanglink").click(function () {
                alert("模仿旺旺的图标，并不会打开旺旺");
            });

            $("a.notlmplementLink").click(function () {
                alert("点不了吧，这个功能还没做呢~");
            });
        });
    </script>
</head>

</html>
