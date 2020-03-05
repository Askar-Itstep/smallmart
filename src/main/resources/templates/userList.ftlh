<#import "parts/common.ftlh" as c>

<@c.page>
    <br>
    <b>List of users</b>
    <table class="table table-bordered">

        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <#if user??>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a> </td>
                </tr>
            </#if>
            <#else> No user
        </#list>
        </tbody>
    </table>

</@c.page>