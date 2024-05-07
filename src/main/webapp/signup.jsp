<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signup Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com?plugins=forms,typography,aspect-ratio,line-clamp"></script>
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        clifford: '#da373d',
                    }
                }
            }
        }
    </script>
</head>
<body class="h-[120vh]">
<div class="bg-grey-lighter  flex flex-col">
    <div class="container max-w-sm mx-auto flex-1 flex flex-col mt-8 items-center justify-center p-2">
        <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
            <h1 class="mb-8 text-3xl text-center">Sign Up</h1>
            
            <c:if test="${not empty errorMessage}">
                <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                    ${errorMessage}
                </div>
            </c:if>
            
            <form action="registerUser" method="post" onsubmit="return validatePassword();">

				  <label for="id" class="block mb-2">Enter your Id</label>
                <input 
                    type="text"
                    id="id"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="id"
                    placeholder="Enter your Id"
                     />
                <label for="name" class="block mb-2">Name</label>
                <input 
                    type="text"
                    id="name"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="name"
                    placeholder="Enter your name"
                     />

                <label for="email" class="block mb-2">Email</label>
                <input 
                    type="email"
                    id="email"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="email"
                    placeholder="Enter your email"
                   />

                <label for="password" class="block mb-2">Password</label>
                <input 
                    type="password"
                    id="password"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="password"
                    placeholder="Enter your password"
                  />

                <label for="confirm_password" class="block mb-2">Confirm Password</label>
                <input 
                    type="password"
                    id="confirm_password"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="confirm_password"
                    placeholder="Confirm your password" />
                
                <!-- Error message container -->
                <div id="error_message" class="text-sm text-red-800 mb-4"></div>

                <label for="phoneNumber" class="block mb-2">Phone Number</label>
                <input 
                    type="number"
                    id="phoneNumber"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="phoneNumber"
                    placeholder="Enter your phone number"
                    />

                <label for="address" class="block mb-2">Address</label>
                <input 
                    type="text"
                    id="address"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="address"
                    placeholder="Enter your address"
                 />

                <button
                    type="submit"
                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >Register</button>
            </form>
        </div>

        <div class="text-grey-dark mt-6">
            Already have an account? 
            <a class="no-underline border-b border-blue text-blue" href="<%=request.getContextPath()%>/signin">
                Sign in
            </a>.
        </div>
    </div>
</div>


  <script>

        // JavaScript function for password confirmation
        function validatePassword() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirm_password").value;
            var errorMessage = document.getElementById("error_message");

            if (password !== confirmPassword) {
                errorMessage.innerHTML = "Passwords do not match";
                return false;
            } else {
                errorMessage.innerHTML = "";
                return true;
            }
        }
    </script>


</body>
</html>
