<#import "parts/common.ftlh" as c>
<@c.page>
    <h1>Edit product</h1>
    <br>
    <div class="jumbotron">
        <form action="/editProduct" method="post" id="formEdit" enctype="multipart/form-data" class="form-inline">
            <div class="form-group container">
                <dl class="row">

                    <dt class="col-sm-1"><label for="file">Upload</label></dt>
                    <dd class="col-sm-11"><input type="file" name="file" id="file" class="form-control" data-filename-placement="inside"
                           title="Выбрать файл"/>
                    </dd>
                    <dt class="col-sm-1">Image</dt>
                    <dd class="col-sm-11"><img style="height: 400px" class="img-fluid" src="${container+'/'+ product.filename!}" alt="No img"></dd>


                    <#if product??>
                        <input type="hidden" value="${product.id}" name="id">
                        <dt class="col-sm-1">Title</dt>
                        <dd class="col-sm-11"><input class="form-control" id="title" type="text" value="${product.title}" name="title"></dd>
                        <dt class="col-sm-1">Price</dt>
                        <dd class="col-sm-11"><input class="form-control" id="price" type="number" value="#{product.price}" name="price"></dd>
                        <#if product.filename??>
                            <input type="hidden" value="${product.filename}" name="filename">
                        </#if>

                        <input type="hidden" value="${_csrf.token}" name="_csrf">
                        <input class="btn btn-primary ml-3" type="submit" value="Edit!">
                    </#if>
                </dl>
            </div>
        </form>
    </div>

    <br>
</@c.page>
<style>
    input#price, input#title {
        width: 30%;
    }
    input[type=submit]{
        width: 40%;
    }
</style>