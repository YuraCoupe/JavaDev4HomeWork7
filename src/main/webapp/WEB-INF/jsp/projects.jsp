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
            <form action="/findProject">
                <div class="form-group">
                    <label for="projectName">Project name:</label><br>
                    <input type="text" class="form-control" id="projectName" placeholder="Enter Project name" name="projectName"><br>
                </div>
                    <input type="submit" value="Search">
            </form><br>

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                   <a href="/projects/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>

            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>Project name</td>
                        <td>Customer ID</td>
                        <td>Company ID</td>
                        <td>Project cost</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${projects}" var="project">
                    <tr>
                        <td>
                            <c:out value="${project.projectName}"/>
                        </td>
                        <td>
                            <c:out value="${project.customerId}"/>
                        </td>
                        <td>
                            <c:out value="${project.companyDto.companyName}"/>
                        </td>
                        <td>
                            <c:out value="${project.projectCost}"/>
                        </td>
                        <td>
                            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                <div class="btn-group me-2" role="group" aria-label="Second group">
                                     <a href="/projects/${project.projectId}" type="button" class="btn btn-warning">Edit</a>
                                     <a href="/projects?deleteId=${project.projectId}" type="button" class="btn btn-danger">Remove</a>
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
