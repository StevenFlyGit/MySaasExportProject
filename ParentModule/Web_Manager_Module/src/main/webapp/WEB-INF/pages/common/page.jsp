<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<div class="pull-left">
    <div class="form-group form-inline">
        总共${pageInfo.pages} 页，共${pageInfo.total} 条数据。
        每页显示
        <select name="selectPageSize" id="selectPageSize" onchange="goPage(1)">
            <option ${pageInfo.pageSize == 3 ? 'selected' : ''} value="3">3</option>
            <option ${pageInfo.pageSize == 5 ? 'selected' : ''} value="5">5</option>
            <option ${pageInfo.pageSize == 7 ? 'selected' : ''} value="7">7</option>
        </select>
        条数据
    </div>
</div>

<div class="box-tools pull-right">
    <ul class="pagination" style="margin: 0px;">
        <li >
            <a href="javascript:goPage(1)" aria-label="Previous">首页</a>
        </li>
        <li><a href="javascript:goPage(${pageInfo.prePage})">上一页</a></li>
        <%--分页方案1：利用jstl和el表达式--%>
        <%--<c:forEach
                begin="${pageInfo.pageNum-4 < 1 ? 1 : pageInfo.pageNum - 4}"
                end="${pageInfo.pageNum+4 > pageInfo.pages ? pageInf.pages : pageInfo.pageNum}"
                var="i"
        >--%>
        <%--分页方案1：利用pageInfo类的参数，默认显示8个页码--%>
        <c:forEach
                begin="${pageInfo.navigateFirstPage}"
                end="${pageInfo.navigateLastPage}"
                var="i"
        >
            <li class="paginate_button ${pageInfo.pageNum==i ? 'active':''}"><a href="javascript:goPage(${i})">${i}</a></li>
        </c:forEach>
        <li><a href="javascript:goPage(${pageInfo.nextPage})">下一页</a></li>
        <li>
            <a href="javascript:goPage(${pageInfo.pages})" aria-label="Next">尾页</a>
        </li>
    </ul>
</div>
<form id="pageForm" action="${param.pageUrl}" method="post">
    <input type="hidden" name="pageSize" id="pageSize">
    <input type="hidden" name="pageNum" id="pageNum">
</form>
<script>

    function goPage(page) {
        let pageSizeNum = $("#selectPageSize").val();
        //document.getElementById("pageNum").value = page
        //document.getElementById("pageForm").submit()
        $("#pageSize").val(pageSizeNum);
        $("#pageNum").val(page);
        $("#pageForm").submit();
    }
</script>
</body>
</html>
