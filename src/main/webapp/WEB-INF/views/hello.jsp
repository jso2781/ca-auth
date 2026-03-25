
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
        import = "java.util.*"
        import = "com.softforum.xdbe.xCrypto"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello JSP</title>
</head>
<body>
<h2>Hello, ${name}!</h2>
<p>이 페이지는 Spring Boot에서 구동되는 JSP 페이지입니다.</p>
<%
    String sOutput		= null;
    String sDecoded	= null;
    String sOutput7	= null;
    String sDecoded7	= null;
    String sOutputc7	= null;
    String sDecodedc7	= null;
    String sOutputc	= null;
    String sDecodedc	= null;
    String sOutput_H	= null;
    String sString		= "1234567890123";

    try
    {
        long nStartTime    = System.currentTimeMillis();
        xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
        //xCrypto.RegisterEx("pattern7", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "pattern7");
        //xCrypto.RegisterEx("hash", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

        sOutput    = xCrypto.Encrypt("normal", sString);
        sDecoded   = xCrypto.Decrypt("normal", sOutput);
        //sOutput7    = xCrypto.Encrypt("pattern7", sString);
        //sDecoded7   = xCrypto.Decrypt("pattern7", sOutput7);
        //sOutput_H	= xCrypto.Encrypt("hash", sString);
        sOutput_H	= xCrypto.Hash(6, sString);

 %>

<br><B>
    [Normal policy]
    <br>
    <br>
        <%=sString%> -> <font color=red><%=sOutput%></font>
    <br>
        <%=sOutput%> -> <font color=blue><%=sDecoded%></font>
    <br><B>
    <!--
    [Pattern7 policy]
    <br>
    <br>
    <%=sString%> -> <font color=red><%=sOutput7%></font>
    <br>
    <%=sOutput7%> -> <font color=blue><%=sDecoded7%></font>
-->
    <br>
    <br>

    [Hash policy]
    <br>
    <br>
    <%=sString%> -> <font color=green><%=sOutput_H%></font>

    <%
        long nEndTime      = System.currentTimeMillis();
        long nDuration     = nEndTime - nStartTime;
    %>
    <br>
    <br>
    <br>
    <br>
    <font color=red>Elasped: <%=nDuration%> ms</font>
</B>
        <%
	}
    	catch (Exception e)
    	{
        		e.printStackTrace();
		out.println(e);
    	}

%>

</body>
</html>