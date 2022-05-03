<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
         <c:import url="${contextPath}/WEB-INF/jsp/header.jsp"/>
    </head>

    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/navibar.jsp"/>
        <div class="container">
            <form action="/companies">
                <div class="form-group">
                    <label for="companyId">Company name:</label><br>
                    <select class="form-control" id="companyId" name="companyId">
                        <option disabled selected value> -- select company -- </option>
                        <c:forEach items="${companies}" var="company">
                            <option value="${company.companyId}"><c:out value="${company.companyName}"/></option>
                        </c:forEach>
                    </select>
                </div>
                    <input type="submit" value="Search">
            </form><br>

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                   <a href="/companies/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>

            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>Company name</td>
                        <td>Company location</td>
                        <td>Company employees</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${companies}" var="company">
                    <tr>
                        <td>
                            <c:out value="${company.companyName}"/>
                        </td>
                        <td>
                            <c:out value="${company.companyLocation}"/>
                        </td>
                        <td>
                            <c:forEach items="${company.developers}" var="developer">
                                <c:out value="${developer.firstName}"/> <c:out value="${developer.lastName}"/><br>
                            </c:forEach>
                        </td>
                        <td>
                            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                <div class="btn-group me-2" role="group" aria-label="Second group">
                                     <a href="/companies/${company.companyId}" type="button" class="btn btn-warning">Edit</a>
                                     <a href="/companies?deleteId=${company.companyId}" type="button" class="btn btn-danger">Remove</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
