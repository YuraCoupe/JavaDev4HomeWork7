<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
         <c:import url="${contextPath}/WEB-INF/jsp/header.jsp"/>
    </head>

    <body>
        <% ua.goit.projectmanagementsystem.model.domain.Project project = (ua.goit.projectmanagementsystem.model.domain.Project) request.getAttribute("project"); %>

        <c:import url="${contextPath}/WEB-INF/jsp/navibar.jsp"/>
        <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/projects" type="button" class="btn btn-success">Back to projects</a>
                    </div>
                </div>
            </div><br>
        <form action="/projects" method="post">
            <div class="form-group">
                <div class="row">
                    <label for="projectId">Project ID:</label><br>
                    <input type="number" readonly="readonly" class="form-control" id="companyId" placeholder="Project ID" name="projectId" value="${project.projectId}"> <br>
                    <label for="projectName">Project name:</label><br>
                    <input type="text" class="form-control" id="projectName" placeholder="Enter project name" name="projectName" value="${project.projectName}"><br>
                    <label for="customerId">Customer:</label><br>
                    <input type="number" class="form-control" id="customerId" placeholder="Enter customer ID" name="customerId" value="${project.customerId}"><br>
                    <label for="companyId">Company:</label><br>
                    <select class="form-control" id="companyId" name="companyId">
                        <option disabled selected value> -- select company -- </option>
                        <c:forEach items="${companies}" var="company">
                            <option value="${company.companyId}" ${company.companyId == project.companyId ? 'selected="selected"' : ''}>
                                <c:out value="${company.companyName}"/>
                            </option>
                        </c:forEach>
                    </select><br>
                    <label for="projectCost">Project cost:</label><br>
                    <input type="number" class="form-control" id="projectCost" placeholder="Enter project cost" name="projectCost" value="${project.projectCost}"><br>

                    <label for="developer">Add developer:</label><br>
                    <select class="form-control" id="developerId" name="developerId">
                        <option disabled selected value> -- select developer -- </option>
                        <c:forEach items="${developersWithoutThisProject}" var="developerWithoutThisProject">
                             <option value="${developerWithoutThisProject.developerId}">
                                 <c:out value="${developerWithoutThisProject.firstName}"/>
                                 <c:out value="${developerWithoutThisProject.lastName}"/>
                             </option>
                        </c:forEach>
                    </select><br>
                </div>
                <div class="row">
                    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                        <div class="btn-group me-2" role="group" aria-label="Second group">
                            <button type="submit" value="Submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <c:forEach items="${errorMessage.errors}" var="error">
                    <p style="color:red">${error}</p>
                </c:forEach>
            </c:if>
        </form>

        <c:if test = "${fn:length(developers) > 0}">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>Developer first name</td>
                        <td>Developer last name</td>
                        <td>Developer salary</td>
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
                            <c:out value="${developer.salary}"/>
                        </td>
                        <td>
                            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                <div class="btn-group me-2" role="group" aria-label="Second group">
                                    <a href="/developers/${developer.developerId}" type="button" class="btn btn-warning">Edit</a>
                                    <a href="/projects/${project.projectId}?removeId=${developer.developerId}" type="button" class="btn btn-danger">Remove</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        </div>
    </body>
</html>
