<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <#if isEmp>
        <div>Product save
            <form action="/save" method="post" enctype="multipart/form-data" class="form-inline">
                <input class="form-control" style="width: 300px" type="text" name="title" placeholder="title"
                       value="title">
                <input class="form-control" style="width: 200px" type="number" name="price" placeholder="100.0"
                       value="100">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <div class="form-group col-sm-6" style="padding-right: 10px;">
                    <label for="file"></label>
                    <input type="file" name="file" id="file" class="form-control" data-filename-placement="inside"
                           title="Выбрать файл"/>
                </div>
                <button class="btn btn-info" style="width: 150px" type="submit">Save</button>
            </form>
        </div>
    </#if>
    <br>
    <#if size = 0>
        <div class="jumbotron">
            <h1>Извините, магазин еще закрыт, приходите поздее!</h1>
            <h2>Если вы работник магазина, то вам следует немедленно выставить товары на прилавок!</h2>
        </div>
    </#if>
    <h1><strong>Product list</strong></h1>
    <form action="/main" method="post">
        <div class="d-flex flex-wrap bd-highlight mb-3 jumbotron jumbotron-fluid">

            <#list products! as product>
                <div class="card my-3" style="width: 200px">
                    <#--<img  class="card-img-top" src="img/${ product.filename!} alt="No img">-->
                    <img class="card-img-top" src="${container+'/'+ product.filename!}" alt="No img">
                    <table class="table table-hover table-sm">
                        <tr>
                            <th>Title</th>
                            <td><#if product??>${product.title}</#if></td>
                        </tr>
                        <tr>
                            <th>Price</th>
                            <td><#if product??>${product.price}</#if></td>
                        </tr>
                        <tr>
                            <th>Quantity</th>
                            <td><input type="number" class="inpQuantity" data-num="${product.id}" name="number" value=""
                                       style="width: 50px"></td>
                        </tr>
                    </table>
                    <#--EDIT - DELETE-->
                    <#if isEmp>
                        <div class="form-inline">
                            <a class="btn btn-warning ml-2" href="/delete/${product.id}">Delete</a>
                            <div style="width: 5px"></div>
                            <a class="btn btn-info ml-2" href="/editProduct/${product.id}">Edit</a>
                        </div>
                    </#if>

                    <#--ADD TO CART-->
                    <#if !isEmp>
                        <div class="card-footer text-muted" id="div${product.id}">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" name="itemId" id="${product.id}"
                                       value="${product.id}">
                                <label class="custom-control-label" for="${product.id}">Add to cart</label>
                            </div>
                        </div>
                    </#if>
                </div>
                <div style="width: 10px"></div>
            </#list>
        </div>
        <#if !isEmp>
            <div class="d-flex justify-content-center" id="divResult">
                <input type="button" class="btn btn-primary" id="btnToCart" value="Start the ordering">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
            </div>
        </#if>

    </form>
    <br>

    <#--===============================================================================================-->

    <script>
        $('#btnToCart').attr("disabled", true);
        var itemsId = [];
        var cart = [];

        $('input:checkbox').on('click', function (e) {
            var prodId = $(this).val()
            $(this).attr('checked', true);
            $('#btnToCart').attr("disabled", false);

            //------и установ. знач. по умолч. (на случ. "забывания")----
            $('.inpQuantity').each(function (ind, item) {
                if (item.dataset.num == prodId) {//выбрать input с атрибутом равным ID-prod
                    $(item).val(1)
                }
            })
        })

        $('#btnToCart')
            .on('mousedown', function (e) {
            $(':checkbox:checked').each(function (ind, item) {  //обход только чекнутых
                // вар1
                itemsId.push($(this).val()) //в массив кладется ID-продукта (потом добав. их кол-во)
                //вар2
                var party = {prodId: $(this).val(), count: 1}//и установ. знач. по умолч.
                cart.push(party)
            })

            $('.inpQuantity').each(function (ind, item) {   //кол-во единиц товара
                //1 вар.
                for (var i = 0; i < $(this).val(); i++) { //положить столько раз ск-ко введено в поле
                    if (!itemsId.includes(this.dataset.num)) {//проверка-если есть и кол-во и checked
                        $(this).attr("disabled", true); //остальн. откл. и  submit
                    }
                }
                //2 вар
                if ($(item).val() > 0) {
                    $(cart).each(function (ind, item2) {
                        console.log(item2.prodId)
                        if (item.dataset.num == item2.prodId) {
                            item2.count = $(item).val()
                        }
                    })
                }
            })
        })
            .mouseup(function (e) {
                $(myForm).triggerHandler("submit")
            })
        let myForm = document.forms[0];

        $(myForm).on("submit", function (e) {
            console.log(cart[0])
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: 'main',
                data: {
                    cartJson: $(cart).serialize()
                },
                error: function (xhr, status) {
                    console.log("ERROR: " + status);
                }
            })
        })

    </script>
</@c.page>