<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Rental Management Application</title>
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
<header class="shadow-md relative flex flex-wrap sm:justify-start sm:flex-nowrap w-full bg-white text-sm  dark:bg-neutral-800">
  <nav class="max-w-[85rem] w-full mx-auto px-4 py-7 sm:flex sm:items-center sm:justify-between" aria-label="Global">
    <div class="flex items-center justify-between">
      <a class="flex-none text-xl font-semibold dark:text-white" href="<%= request.getContextPath() %>/home">Car rental app</a>
      <div class="sm:hidden">
        <button type="button" class="hs-collapse-toggle p-2 inline-flex justify-center items-center gap-x-2 rounded-lg border border-gray-200 bg-white text-gray-800 shadow-sm hover:bg-gray-50 disabled:opacity-50 disabled:pointer-events-none dark:bg-transparent dark:border-neutral-700 dark:text-white dark:hover:bg-white/10" data-hs-collapse="#navbar-with-mega-menu" aria-controls="navbar-with-mega-menu" aria-label="Toggle navigation">
          <svg class="hs-collapse-open:hidden flex-shrink-0 size-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="3" x2="21" y1="6" y2="6"/><line x1="3" x2="21" y1="12" y2="12"/><line x1="3" x2="21" y1="18" y2="18"/></svg>
          <svg class="hs-collapse-open:block hidden flex-shrink-0 size-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
        </button>
      </div>
    </div>
    <div id="navbar-with-mega-menu" class="hs-collapse hidden  transition-all duration-300 basis-full grow sm:block">
      <div class="flex flex-col gap-5 mt-5 sm:flex-row sm:items-center sm:justify-end sm:mt-0 sm:ps-5">
        <a class="font-medium text-blue-500" href="<%= request.getContextPath() %>/home" aria-current="page">Home</a>
        <a class="font-medium text-gray-600 hover:text-gray-400 dark:text-neutral-400 dark:hover:text-neutral-500" href="<%= request.getContextPath() %>/services">Services</a>
        <a class="font-medium text-gray-600 hover:text-gray-400 dark:text-neutral-400 dark:hover:text-neutral-500" href="<%= request.getContextPath() %>/contact">Contact</a>
        <div class="hs-dropdown [--strategy:static] sm:[--strategy:fixed] [--adaptive:none] ">
          <% if (session.getAttribute("user") != null) { %>
          <button id="profile-toggle" type="button" class="flex items-center w-full text-gray-600 hover:text-gray-400 font-medium dark:text-neutral-400 dark:hover:text-neutral-500 ">
            Hello, ${user.name}
            <svg class="ms-1 flex-shrink-0 size-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m6 9 6 6 6-6"/></svg>
          </button>
          <% } else { %>
           <a class="py-2 px-6 bg-gray-50 hover:bg-gray-100 text-sm text-gray-900 font-bold rounded-xl transition duration-200" href="<%= request.getContextPath() %>/signup">Sign up</a>
            <a class="py-2 px-6 bg-blue-500 hover:bg-blue-600 text-sm text-white font-bold rounded-xl transition duration-200" href="<%= request.getContextPath() %>/signin">Sign in</a>
          <% } %>
        </div>
      </div>
    </div>
  </nav>
</header>

<div id="profile-dropdown" class="absolute hidden right-4 top-25 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="profile-toggle" tabindex="-1">
    <div class="py-1" role="none">
        <a href="<%= request.getContextPath() %>/myrentals" class="text-gray-700 block px-4 py-2 text-sm"  id="menu-item-0">My Rentals</a>
        <a href="<%= request.getContextPath() %>/cars-list" class="text-gray-700 block px-4 py-2 text-sm"   id="menu-item-1">Manage Cars</a>
        <a href="<%= request.getContextPath() %>/rentals-list" class="text-gray-700 block px-4 py-2 text-sm"  id="menu-item-2">Manage Rentals</a>
        <a href="<%= request.getContextPath() %>/account-settings" class="text-gray-700 block px-4 py-2 text-sm"   id="menu-item-3">Account Settings</a>
        <form method="POST" action="<%= request.getContextPath() %>/logout" role="none">
            <button type="submit" class="text-gray-700 block w-full px-4 py-2 text-left text-sm" role="menuitem" tabindex="-1" id="menu-item-5">Logout</button>
        </form>
    </div>
</div>


<div class="container mx-auto px-4">
    <h1 class="font-serif text-3xl font-bold underline decoration-gray-400">Edit Rental</h1>
    <div class="bg-white max-w-md mx-auto p-8 rounded shadow-md">
        <c:if test="${not empty errorMessage}">
            <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                ${errorMessage}
            </div>
        </c:if>
        <form action="<%= request.getContextPath() %>/rental-update" method="post">

            <label for="user_id" class="block mb-2">User ID</label>
             <input 
                type="hidden"
                id="userId"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="rentalid"
                value="${rental.id}"  />
            <input 
                type="text"
                id="userId"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="user_id"
                value="${rental.user_id}"  />

            <label for="registrationNumber" class="block mb-2">Registration Number</label>
            <input 
                type="text"
                id="registrationNumber"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="registrationNumber"
                value="${rental.registrationNumber}" />

            <label for="startDate" class="block mb-2">Start Date</label>
            <input 
                type="date"
                id="startDate"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="startDate"
                value="${rental.startDate}" />

            <label for="endDate" class="block mb-2">End Date</label>
            <input 
                type="date"
                id="endDate"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="endDate"
                value="${rental.endDate}" />

            <label for="note" class="block mb-2">Note</label>
            <textarea 
                id="note"
                class="block border border-gray-300 w-full p-3 rounded mb-4"
                name="note"
                rows="4">${rental.note}</textarea>

            <button
                type="submit"
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >Update Rental</button>
        </form>
    </div>
</div>

<script type="text/javascript" > 
document.addEventListener("DOMContentLoaded", function() {
    // Get the profile toggle button
    var profileToggle = document.getElementById("profile-toggle");
    
    // Get the profile dropdown menu
    var profileDropdown = document.getElementById("profile-dropdown");

    // Add click event listener to the profile toggle button
    profileToggle.addEventListener("click", function() {
        // Toggle the visibility of the profile dropdown menu
        if (profileDropdown.classList.contains("hidden")) {
            profileDropdown.classList.remove("hidden");
        } else {
            profileDropdown.classList.add("hidden");
        }
    });
});
</script>
</body>
</html>
