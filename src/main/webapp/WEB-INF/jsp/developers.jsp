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

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                   <a href="/developers/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>

            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>First name</td>
                        <td>Last name</td>
                        <td>Age</td>
                        <td>Sex</td>
                        <td>Company</td>
                        <td>Salary</td>
                        <td>Skills</td>
                        <td>Projects</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${developers}" var="developer">
                    <tr>
                        <td>
                            <c:out value="${developer.firstName}"/>
                        </td>
                        <td>
                            <c:out value="${developer.lastName}"/>
                        </td>
                        <td>
                            <c:out value="${developer.age}"/>
                        </td>
                        <td>
                            <c:out value="${developer.sex}"/>
                        </td>
                        <td>
                            <c:out value="${developer.company.companyName}"/>
                        </td>
                        <td>
                            <c:out value="${developer.salary}"/>
                        </td>
                        <td>
                            Skills List
                        </td>
                        <td>
                            Projects List
                        </td>
                        <td>
                            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                <div class="btn-group me-2" role="group" aria-label="Second group">
                                     <a href="/developers/${developer.developerId}" type="button" class="btn btn-warning">Edit</a>
                                     <a href="/developers?deleteId=${developer.developerId}" type="button" class="btn btn-danger">Remove</a>
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
