<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    
</head>
<body>
    <h1>Dashboard</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Email</th>
                <th>Nom</th>
                <th>Prenom</th>
                <th>delete</th>
                <th>modifier</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${farmers}" var="farmer">
                <tr>
                    <td>${farmer.email}</td>
                    <td>${farmer.nom}</td>
                    <td>${farmer.prenom}</td>
                    
                    <td>
                    
 				<button type="button" onclick="deleteFarmer('${farmer.id}')">delete</button>                   


                    </td>

                    <td><button class="modify-button bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" data-id="${farmer.id}">
                    modify
                    </button> 
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
<script>
function test(){
	console.log("test");
}
function deleteFarmer(id) {
	console.log(id);
    fetch('deletefarmer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: id }),
    }).then(response => {
        // handle response here if needed
        console.log(response);
    }).catch(error => {
        // handle error here if needed
        console.error('Error:', error);
    });
}
</script>
</body>
</html>
