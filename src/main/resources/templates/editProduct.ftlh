<#import "parts/common.ftlh" as c>
<@c.page>
<h1>Edit product</h1>
    <br>
    <#--my-3-->
    <form   action="/editProduct" method="post" id="formEdit"enctype="multipart/form-data" class="form-inline">
        <div class="card " style="width: 400px; height: 600px">
            <div class="form-group col-sm-6" style="padding-right: 10px;">
                <label for="file"></label>
                <input type="file" name="file" id="file" class="form-control" data-filename-placement="inside" title="Выбрать файл" />
            </div>
            <img   style="height: 400px" class="card-img-top" src="${container+'/'+ product.filename!}" alt="No img">
            <#--<table class="table table-hover table-sm">-->
                <#--<tr>-->
                    <#--<th>ID</th><td><#if product??>${product.id}</#if></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<th>Title</th><td><#if product??>${product.title}</#if></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<th>Price</th><td><#if product??>${product.price}</#if></td>-->
                <#--</tr>-->
            <#--</table>-->

            <#if product??>
                <input type="hidden" value="${product.id}" name="id">
                <input type="text" value="${product.title}"name="title">
                <input type="number" value="${product.price}" name="price">
                <#if product.filename??>
                    <input type="hidden" value="${product.filename}" name="filename">
                </#if>

                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <input  class="btn btn-primary" type="submit" value="Edit!">
            </#if>
        </div>
    </form>
    <br>
</@c.page>
