<#import "parts/common.ftlh" as c>
    <@c.page>
        <h1>My Cart</h1>
        <br>

        <form   action="/cart" method="post" id="formOne">
            <div  class="d-flex flex-wrap bd-highlight mb-3 jumbotron jumbotron-fluid">
                <#list cart.items! as item>
                    <div class="card my-3" style="width: 200px">
                        <img  class="card-img-top" src="img/${item.product.filename!}" alt="No img">

                        <table class="table table-hover table-sm">
                            <tr>
                                <th>Title</th><td><#if item??>${item.product.title}</#if></td>
                            </tr>
                            <tr>
                                <th>Price</th><td><#if item??>${item.product.price}</#if></td>
                            </tr>
                        </table>

                        <#--REMOVE FROM CART-->
                        <div class="card-footer text-muted" id="div${item.product.id}">
                            <a href="/cart/${item.id}" class="btn btn-primary btn-lg active" role="button">Remove</a>
                        </div>
                    </div>
                    <div style="width: 10px"></div>
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

<script>
    // $('form[name="second"]').each.on("mousedown", function (e) {
    //
    //     e.stopPropagation();
    //     $('#formOne').attr("disabled", true)
    //                 .remove();
    // });

</script>