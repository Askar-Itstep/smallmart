<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SmallMart</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-product">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-product">
                <a class="nav-link" href="/login">Login</a>
            </li>
            <#--определение в secur.ftlh-->
            <#if isEmp!>
                <li class="nav-product">
                    <a class="nav-link" href="/order">Order list</a>
                </li>
            </#if>
            <#if isAdmin!>
                <li class="nav-product">
                    <a  class="nav-link" href="/user">User list</a>
                </li>
            </#if>

            <li class="nav-product">
                <a  class="nav-link" href="/main">Main page</a>
            </li>
            <li class="nav-product">
                <a  class="nav-link" href="/cart">My Cart</a>
            </li>
        </ul>

        <div class="navber-text mr-3">${name}</div>
        <@l.logout/>
    </div>
</nav>

