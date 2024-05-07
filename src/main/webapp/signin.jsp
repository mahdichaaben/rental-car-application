<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signin Page</title>
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
<body>
<div class="bg-grey-lighter min-h-screen flex flex-col">
    <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
        <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
            <h1 class="mb-8 text-3xl text-center">Sign in</h1>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <form action="checkuser" method="post">
                <label for="email" class="block mb-2">Email</label>
                <input 
                    type="email"
                    id="email"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="email"
                    placeholder="Enter your email" />

                <label for="password" class="block mb-2">Password</label>
                <input 
                    type="password"
                    id="password"
                    class="block border border-grey-light w-full p-3 rounded mb-4"
                    name="password"
                    placeholder="Enter your password" />
                    
                <button
                    type="submit"
                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >Sign In</button>
            </form>
        </div>

        <div class="text-grey-dark mt-6">
            Don't have an account? 
            <a class="no-underline border-b border-blue text-blue" href="<%=request.getContextPath()%>/signup">
                Sign up
            </a>.
        </div>
    </div>
</div>

</body>
</html>
