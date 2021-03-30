<%--
  Created by IntelliJ IDEA.
  User: wny
  Date: 2021/3/4
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript">
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            window.location.href = "http://wnyeagle.top/LockWebServer/pages/phone/phoneLogin.html";
        } else {
            window.location.href = "http://wnyeagle.top/LockWebServer/pages/pc/login.jsp";
        }
    </script>
</head>

<body>

</body>
</html>
