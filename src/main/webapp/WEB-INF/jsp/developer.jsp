<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
         <c:import url="${contextPath}/WEB-INF/jsp/header.jsp"/>
    </head>

    <body>
        <% ua.goit.projectmanagementsystem.model.dto.DeveloperDto developer = (ua.goit.projectmanagementsystem.model.dto.DeveloperDto) request.getAttribute("developer"); %>

        <c:import url="${contextPath}/WEB-INF/jsp/navibar.jsp"/>
        <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/developers" type="button" class="btn btn-success">Back to developers</a>
                    </div>
                </div>
            </div><br>
            <form action="/developers" method="post">
                <div class="form-group">
                    <div class="row">
                        <label for="developerId">Developer ID:</label><br>
                        <input type="number" readonly="readonly" class="form-control" id="developerId" placeholder="Developer ID" name="developerId" value="${developer.developerId}"> <br>
                         <label for="firstName">First name:</label><br>
                        <input type="text" class="form-control" id="firstName" placeholder="Enter first name" name="firstName" value="${developer.firstName}"><br>
                        <label for="lastName">Last name:</label><br>
                        <input type="text" class="form-control" id="lastName" placeholder="Enter last name" name="lastName" value="${developer.lastName}"><br>
                        <label for="age">Age:</label><br>
                        <input type="number" class="form-control" id="age" placeholder="Enter age" name="age" value="${developer.age}"><br>
                        <label for="sex">Sex:</label><br>
                        <select class="form-control" id="sex" name="sex">
                            <option value="male" <c:if test="${developer.sex == 'male'}"> selected </c:if>>male</option>
                            <option value="female" <c:if test="${developer.sex == 'female'}"> selected </c:if>>female</option>
                        </select><br>
                        <label for="company">Company:</label><br>
                        <select class="form-control" id="companyId" name="companyId">
                            <c:forEach items="${companies}" var="company">
                                <option value="${company.companyId}" ${company.companyId == developer.company.companyId ? 'selected="selected"' : ''}>
                                    <c:out value="${company.companyName}"/>
                                </option>
                            </c:forEach>
                        </select><br>
                        <label for="salary">Salary, USD:</label><br>
                        <input type="number" class="form-control" id="salary" placeholder="Enter salary" name="salary" value="${developer.salary}"><br>
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
        </div>
    </body>
</html>
