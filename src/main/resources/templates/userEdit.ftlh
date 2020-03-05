<#import "parts/common.ftlh" as c>
<@c.page>
    User editor
    <#--отсюда на UserContoller ("/user")=>userSave()-->
    <form action="/user" method="post">
        <input type="text"  name="username" value="${user.username}" placeholder="username">
        <#list roles as role>
            <#if role??>
                <div>
                    <#--метод проверяет наличие (bool), + cast into string + checked fo 1st-->
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                        ${role}</label>
                </div>
            </#if>
            <#else> No role
        </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">

        <button type="submit">Save</button>
    </form>
</@c.page>