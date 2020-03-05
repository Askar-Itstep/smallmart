<#import "parts/common.ftlh" as c>
<@c.page>
<h2>Enter Customer Information</h2>
    <form method="post" action="/myOrder">
        <div class="form-group row">
            <label for="staticName" class="col-sm-2 col-form-label">Name *</label>
            <div class="col-sm-10">
                <input type="text" class="form-control-plaintext" id="staticName" name="name" placeholder="Alex" value="Alex">
            </div>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-2 col-form-label">Email *</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="email" id="staticEmail" placeholder="email@example.com" value="email@example.com">
            </div>
        </div>
        <div class="form-group row">
            <label for="staticPhone" class="col-sm-2 col-form-label">Phone *</label>
            <div class="col-sm-10">
                <input type="tel" class="form-control" name="phone" id="staticPhone" placeholder="87023695245" value="87023695245">
            </div>
        </div>
        <div class="form-group row">
            <label for="staticAddress" class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="address" id="staticAddress" placeholder="Karaganda, Erubaeva st., 23-56" value="Karaganda, Erubaeva st., 23-56">
            </div>
        </div>
        <input type="submit"class="btn btn-warning">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
<hr>
    <div class="alert alert-warning" role="alert">
        Внимание, за полноту и правильность заполнения полей несет ответственность Покупатель!
    </div>


</@c.page>