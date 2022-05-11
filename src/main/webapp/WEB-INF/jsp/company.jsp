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
        <% ua.goit.projectmanagementsystem.model.domain.Company company = (ua.goit.projectmanagementsystem.model.domain.Company) request.getAttribute("company"); %>

        <c:import url="${contextPath}/WEB-INF/jsp/navibar.jsp"/>
        <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/companies" type="button" class="btn btn-success">Back to companies</a>
                    </div>
                </div>
            </div><br>
        <form action="/companies" method="post">
            <div class="form-group">
                <div class="row">
                    <label for="companyId">Company ID:</label><br>
                    <input type="number" readonly="readonly" class="form-control" id="companyId" placeholder="Company ID" name="companyId" value="${company.companyId}"> <br>
                    <label for="companyName">Company name:</label><br>
                    <input type="text" class="form-control" id="companyName" placeholder="Enter Company name" name="companyName" value="${company.companyName}"><br>
                    <label for="companyLocation">Company location:</label><br>
                    <input type="text" class="form-control" id="companyLocation" placeholder="Enter Company location" name="companyLocation" value="${company.companyLocation}"><br>
                    <label for="developer">Add developer:</label><br>
                    <select class="form-control" id="developerId" name="developerId">
                        <option disabled selected value> -- select an option -- </option>
                        <c:forEach items="${unemployedDevelopers}" var="unemployedDeveloper">
                             <option value="${unemployedDeveloper.developerId}">
                                 <c:out value="${unemployedDeveloper.firstName}"/>
                                 <c:out value="${unemployedDeveloper.lastName}"/>
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

        <c:if test = "${fn:length(company.developers) > 0}">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>Developer first name</td>
                        <td>Developer last name</td>
                        <td>Developer salary</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${company.developers}" var="developer">
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
                                    <a href="/companies/${company.companyId}?removeId=${developer.developerId}" type="button" class="btn btn-danger">Remove</a>
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
