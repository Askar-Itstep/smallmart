<#assign know = Session.SPRING_SECURITY_CONTEXT??>
    <#--исп. переменных(isAdmin..)-->
<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isEmp = user.isEmployee()>

<#else >
    <#assign
    name = "unknown"
    isEmp = false
    >
</#if>

<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        isAdmin = user.isAdmin()>
    <#else>
    <#assign
        isAdmin = false>
</#if>

<#if know>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    isManager = user.isManager()>
<#else>
    <#assign
    isManager = false>
</#if>