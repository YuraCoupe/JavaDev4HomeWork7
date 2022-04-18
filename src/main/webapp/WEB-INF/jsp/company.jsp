<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
         <c:import url="${contextPath}/WEB-INF/jsp/header.jsp"/>
    </head>

    <body>
        <% ua.goit.projectmanagementsystem.model.dto.CompanyDto company = (ua.goit.projectmanagementsystem.model.dto.CompanyDto) request.getAttribute("company"); %>

        <c:import url="${contextPath}/WEB-INF/jsp/navibar.jsp"/>
        <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/companies" type="button" class="btn btn-success">Back to companies</a>
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="companyName">Company ID:</label><br>
                <input type="text" disabled class="form-control" id="companyId" placeholder="Company ID" name="companyId" value="${company.companyId}"> <br>
                <label for="companyName">Company name:</label><br>
                <input type="text" class="form-control" id="companyName" placeholder="Enter Company name" name="companyName" value="${company.companyName}"><br>
                <label for="companyLocation">Company location:</label><br>
                <input type="text" class="form-control" id="companyLocation" placeholder="Enter Company location" name="companyLocation" value="${company.companyLocation}"><br>
            </div>

            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <button onclick="save()" type="button" class="btn btn-primary">Save</button>
                    </div>
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
                            Developers List

                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            let companyId = document.getElementById('companyId');
            let companyName = document.getElementById('companyName');
            let companyLocation = document.getElementById('companyLocation');
            function save() {
             let body = {
             <% if(company.getCompanyId() != null) {%>
                 companyId: companyId.value,
              <% } %>
                companyName: companyName.value,
                companyLocation: companyLocation.value,
              }
              <% if(company.getCompanyId() == null) {%>
                 let url = '/companies';
                 let method = 'POST';
              <% } else { %>
                 let url = '/companies/<%= company.getCompanyId() %>';
                 let method = 'PUT';
              <% } %>
                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/companies';
                }
                );
            }
        </script>
    </body>
</html>
