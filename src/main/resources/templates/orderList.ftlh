<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/order"class="form-inline" >
                <input class="form-control" type="text" name="clientName" value="${clientName!}" placeholder="Seach by Clientname">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <table class="table table-bordered">
        <caption>List of products into cart</caption>
        <thead>
        <tr>
            <th>ID</th>
            <th>Products</th>
            <th>Cost</th>
            <th>User</th>
        </tr>
        </thead>
        <tbody>
        <#list carts as cart>
            <#if cart??>
                <tr>
                    <td>${cart.id}</td>
                    <#--<td><#list items as item>-->
                            <#--<#if cart.id=item.cart.id>-->
                                <#--&lt;#&ndash;${cart.id}=${item.cart.id}&ndash;&gt;-->
                                <#--${item.product.title} ${item.product.price}-->
                            <#--</#if>-->
                        <#--</#list>-->
                    <#--</td>-->
                    <td><#list cart.items as item>
                                ${item.product.title} ${item.product.price}
                        </#list>
                    </td>
                    <td>${cart.cost}</td>
                    <td>${cart.user.username}</td>
                </tr>
            </#if>
            <#else> No orders
        </#list>
        </tbody>
    </table>

</@c.page>