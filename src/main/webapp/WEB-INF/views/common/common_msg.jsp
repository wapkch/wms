<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function () {
        <s:if test="hasActionMessages()">
        var msg="<s:property value="actionMessages[0]"/>";
        showDialogMsg("",msg);
        </s:if>
        <s:if test="hasActionErrors()">
        msg="<s:property value="actionErrors[0]"/>";
        showDialogMsg("",msg);
        </s:if>
    })
</script>