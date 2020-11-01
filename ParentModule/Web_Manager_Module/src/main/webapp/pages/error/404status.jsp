<%--
  User: wpf
  Date: 2020/9/22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>404 - 资源不存在</title>

    <style>
        body, html{
            margin: 0 auto;
            padding: 0;
            font-weight: 800;
        }

        body{
            background: whitesmoke;
            font-family: cursive;
        }

        svg {
            display: block;
            font: 10.5em 'Monoton';
            width: 960px;
            height: 300px;
            margin: 0 auto;
        }

        .content{
            text-align: center;
        }

        h1{
            text-align: center;
            font: 2em 'Roboto', sans-serif;
            font-weight: 900;
            color: #2f8f7f;
            opacity: .6;
        }

        a{
            display: inline-block;
            text-transform: uppercase;
            font: 2em 'Roboto';
            font-weight: 300;
            border: 1px solid #2f8f7f;
            border-radius: 4px;
            color: #2f8f7f;
            margin-top: 10%;
            padding: 8px 14px;
            text-decoration: none;
            opacity: .6;
        }

        .text {
            fill: none;
            stroke: white;
            stroke-dasharray: 8%, 29%;
            stroke-width: 5px;
            stroke-dashoffset: 1%;
            animation: stroke-offset 5.5s infinite linear;
        }

        .text:nth-child(1){
            stroke: #c1e2b3;
            animation-delay: -1s;
        }

        .text:nth-child(2){
            stroke: #62c462;
            animation-delay: -2s;
        }

        .text:nth-child(3){
            stroke: #5bc0de;
            animation-delay: -3s;
        }

        .text:nth-child(4){
            stroke: #006fbb;
            animation-delay: -4s;
        }

        .text:nth-child(5){
            stroke: #1b6d85;
            animation-delay: -5s;
        }

        @keyframes stroke-offset{
            100% {
                stroke-dashoffset: -35%;
            }
        }
    </style>
</head>
<body>

<%--<link href="https://fonts.googleapis.com/css?family=Monoton" rel="stylesheet">--%>
<%--<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">--%>

<svg viewBox="0 0 960 300">
    <symbol id="s-text">
        <text text-anchor="middle" x="50%" y="80%">404</text>
    </symbol>

    <g class = "g-ants">
        <use xlink:href="#s-text" class="text"></use>
        <use xlink:href="#s-text" class="text"></use>
        <use xlink:href="#s-text" class="text"></use>
        <use xlink:href="#s-text" class="text"></use>
        <use xlink:href="#s-text" class="text"></use>
    </g>
</svg>
<div class="content">
    <h1>页面未找到</h1>
    <a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
</div>

</body>
</html>
