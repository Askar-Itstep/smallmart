<#import "parts/common.ftlh" as c>
<@c.page>
    <h1>Confirmation</h1>
    <br>
    <div class="border">
        <h3><b>Customer information</b></h3>
        <br>
        <table>
            <tr>
                <th>Name</th><td>${user.username}</td>
            </tr>
            <tr>
                <th>Email</th><td>${user.password}</td>
            </tr>
            <tr>
                <th>Phone</th><td>${user.phone}</td>
            </tr>
            <tr>
                <th>Address</th><td>${user.address}</td>
            </tr>
        </table>
        <h3><b>Cart summary</b></h3>
        <table>
            <tr>
                <th>Quantity: </th><td> ${items_size}</td>
                <th>Total cost</th><td> ${cart.cost}</td><td> kzt</td>
            </tr>
        </table>
    </div>
    <form   action="/buy" method="post">
        <div  class="d-flex flex-wrap bd-highlight mb-3 jumbotron jumbotron-fluid">
            <#list items! as item>
                <div class="card my-3" style="width: 200px">
                    <img  class="card-img-top" src="${container +"/"+ item.product.filename!}" alt="No img">

                    <table class="table table-hover table-sm">
                        <tr>
                            <th>Title</th><td><#if item??>${item.product.title}</#if></td>
                        </tr>
                        <tr>
                            <th>Price</th><td><#if item??>${item.product.price}</#if></td>
                        </tr>
                    </table>
                </div>
                <div style="width: 10px"></div>
                <#--<input type="hidden" name="itemId" value="${item.id}">-->
                <input type="hidden" name="cartId" value="${cart.id}">
            </#list>
        </div>

        <div class="d-flex justify-content-center" id="divResult">
            <h4><b>Total cost this Cart</b></h4>
            <div style="width: 20px"></div>
            <h4><b>${cart.cost}</b></h4>
            <div style="width: 20px"></div>
            <input type="submit" class="btn btn-success"  id="btnToBuy" value="Buy" style="width: 100px;">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
        </div>
    </form>
    <br>
</@c.page>