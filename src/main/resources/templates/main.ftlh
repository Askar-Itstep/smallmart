<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <#if isEmp>
        <div>Product save
            <form action="/save" method="post" enctype="multipart/form-data" class="form-inline">
                <input class="form-control" style="width: 300px" type="text"  name="title" placeholder="title" value="title">
                <input class="form-control" style="width: 200px" type="number"  name="price" placeholder="100.0" value="100">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <div class="form-group col-sm-6" style="padding-right: 10px;">
                    <label for="file"></label>
                    <input type="file" name="file" id="file" class="form-control" data-filename-placement="inside" title="Выбрать файл" />
                </div>
                <button  class="btn btn-info" style="width: 150px" type="submit">Save</button>
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
    <form   action="/main" method="post">
      <div  class="d-flex flex-wrap bd-highlight mb-3 jumbotron jumbotron-fluid">

          <#list products! as product>
              <div class="card my-3" style="width: 200px">
                  <#--<img  class="card-img-top" src="img/${ product.filename!} alt="No img">-->
                  <img  class="card-img-top" src="${container+'/'+ product.filename!}" alt="No img">
                  <table class="table table-hover table-sm">
                      <tr>
                          <th>Title</th><td><#if product??>${product.title}</#if></td>
                      </tr>
                      <tr>
                          <th>Price</th><td><#if product??>${product.price}</#if></td>
                      </tr>
                      <tr>
                          <th>Quantity</th><td>
                              <input type="number" class="inpQuantity"  data-num="${product.id}" name="number"  value="" style="width: 50px"></td>
                          <#--placeholder="1"-->
                      </tr>
                  </table>
                  <#--EDIT - DELETE-->
                  <#if isEmp>
                      <div class="form-inline">
                          <form action="/delete" method="post" >
                              <input type="hidden" name="_csrf" value="${_csrf.token}">
                              <input type="hidden" name="delId" value=${product.id}>
                              <input type="submit" class="btn btn-warning ml-2" value="Delete">
                          </form>
                          <div style="width: 5px"></div>

                          <form action="/editProduct" method="get" >
                              <input type="hidden" name="_csrf" value="${_csrf.token}">
                              <input type="hidden" name="editId" value=${product.id}>
                              <input type="submit" class="btn btn-info ml-2" value="Edit">
                          </form>
                      </div>
                  </#if>

                  <#--ADD TO CART-->
                  <#if !isEmp>
                      <div class="card-footer text-muted" id="div${product.id}">
                          <div class="custom-control custom-checkbox">
                              <input type="checkbox" class="custom-control-input"  name="itemId" id="${product.id}" value="${product.id}">
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
                <input type="submit" class="btn btn-primary"  id="btnToCart" value="Start the ordering">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
            </div>
        </#if>

    </form>
<br>


<script>
    var itemsId = [];
    var numbers = [];
    $('input:checkbox').on('click', function(e) {
        console.log(e.target);
        $(this).attr('checked', true);
    });

    $('#btnToCart')
        .on('mousedown', function (e) {
            $(':checkbox:checked').each(function (e) {
                itemsId.push($(this).val());
            });
            $('.inpQuantity').each(function (e) {   //кол-во единиц товара
                if($(this).val() == "")
                    $(this).val(1);                 //установ. по умолч.
                for(var i = 0; i < $(this).val(); i++){ //положить столько раз ск-ко введено в поле
                    if(!itemsId.includes(this.dataset.num)) {//проверка-если есть и кол-во и checked
                        $(this).attr("disabled", true); //остальн. откл. и  submit
                    }
                }
            });
        })
                                        //без этого работ.
        // .on('submit', function (e) {
        //     e.preventDefault();
            // $.ajax({
            //     type: 'POST',
            //     url: 'main',
            //     contentType: "application/json; charset=utf-8",
            //     async: false,
            //     cache: false,
            //     processData:false,
            //     data: {"itemsId":JSON.stringify(itemsId)}+"!!!!!" , //кроме списка item'ов уходит numbers из соотв. input'ов
            //     beforeSend: function (xhr) {
            //         console.log("SEND");
            //         xhr.setRequestHeader("Accept", "application/json");
            //         xhr.setRequestHeader("Content-Type", "application/json");
            //     },
            //     error: function () {
            //         console.log("ERROR");
            //     },
            //     complete: function () {
            //         console.log("FINAL");
            //     }
            // });
        // });

</script>
</@c.page>