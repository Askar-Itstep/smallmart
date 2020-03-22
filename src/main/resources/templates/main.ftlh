<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<#include "parts/security.ftlh">
<@c.page>
    <#if isEmp>
        <div>Product save
            <#----------------------------------------ФОРМА № 1------------------------------------------->
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
                <button type="submit" class="btn btn-info" style="width: 150px">Save</button>
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
<#----------------------------------------------ФОРМА № 2-------------------------------------------------------->
    <@p.pager url page/>
    <form action="/main" method="post" name="products">
        <div class="d-flex flex-wrap bd-highlight mb-3 jumbotron jumbotron-fluid">
            <#--<#list products! as product>-->
            <#list page.content as product>
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
    <@p.pager url page/>
    <br>

<#--===============================================================================================-->

    <script type="text/javascript">
        $('#btnToCart').attr("disabled", true);


        $('input:checkbox').on('click', function (e) {
            var prodId = $(this).val()
            $(this).attr('checked', true);
            $('#btnToCart').attr("disabled", false);

            //------и установ. знач. по умолч. (на случ. "забывания")----
            $('.inpQuantity').each(function (ind, item) {
                if (item.dataset.num == prodId) {//выбрать input с атрибутом равным ID-prod
                    $(item).val(1)  //устан. знач. по умолч. (пока)
                }
            })
        })

        $('#btnToCart')
            .click(function (e) {
                //a)-----------------------------
                $(':checkbox:checked').each(function (ind, item) {  //обход только чекнутых
                    // вар1
                    itemsId.push($(this).val()) //в массив кладется ID-продукта (потом добав. их кол-во)
                    //вар2
                    var party = {prodId: $(this).val(), count: 1}//и установ. знач. по умолч.
                    cart.push(party)
                })

                $('.inpQuantity').each(function (ind, item) {   //кол-во единиц товара
                    //---------1 вар.(тело: последовательность типа <num=1&num=3&itemId=2&itemId=4>---------------
                    var id = this.dataset.num;
                    var obj = this;
                    $(itemsId).each((ind, item2) => {
                        // console.log(item2)      //ID-item
                        if (id == item2)
                            $(obj).attr("disabled", false);
                        else $(obj).remove()//.attr("disabled", true);
                    })
                    //---------2 вар. (тело: массив объектов ----------------------------
                    if ($(item).val() > 0) {
                        $(cart).each(function (ind, item2) {
                            // console.log(item2.prodId)
                            if (item.dataset.num == item2.prodId) {
                                item2.count = $(item).val()
                            }
                        })
                    }
                })
                //b)------------
                $(this).attr("disabled", true);
                // tourDeCart()
                console.log(cart)
                console.log(JSON.stringify(cart))
                ajaxCart(e)
            });

        // $(myForm).submit(function (e) {
        //     e.preventDefault()
        //     ajaxForm()
        // })
        //------------------------------------FUNCTIONS -----------------------------------------
        var itemsId = [];
        var cart = [];
        var myForm = document.forms['products'];

        //-------------------2-------------------

        function ajaxForm() {
            var formData = new FormData(myForm)
            formData.append('cart', cart)
            formData.append('itemsId', itemsId)
            $.ajax({
                type: 'POST',
                url: 'main',
                processData: false,
                contentType: false, //'application/json; charset=utf-8',
                cache: false,
                data: formData
                ,success: function (data) {
                    console.log("!!!!!!!!!")
                },
                error: function (xhr, status) {
                    alert("ERROR: " + xhr.status);
                }
            })
        }

        //--------------------3------------------------------------------
        function tourDeForm() {
            $(myForm).find('input[type=number]').each((ind, item) => {
                console.log(item)
            })
            $('input').each(function (ind, item) {
                // console.log(item)
            })
        }

        //---------------------4--------------------------------------
        function tourDeCart() {
            $(cart).each(function (ind, item) {
                console.log(item)
            })
        }

        //--------------------------5--------------------------------------
        function ajaxCart(e) {
            if (e != undefined) {
                e.preventDefault();
            }
            $.ajax({
                type: 'POST',
                url: 'main',
                dataType: false,
                processData: false,
                contentType: 'application/json; charset=utf-8',
                cache: false,
                data: JSON.stringify(cart),
                success: function (data) {
                    location.replace('cart')
                },
                error: function (xhr, status) {
                    alert("ERROR: " + xhr.status);
                }
            })
        };
        //--------6) для ajax-запроса (без этого не работ.)--------------------------
        var token = "${_csrf.token}"
        $.ajaxSetup({
            headers:{
                'X-Csrf-Token':token
            }
        });
    </script>
</@c.page>