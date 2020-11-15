<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="jsp-components/header.jsp"></jsp:include>

<div style="text-align: center">

<div class="input-section">
    <input class="input-area" id="search" type="text" placeholder="search...">
    <button type="submit" class="searchButton bg-dark" id="sbtn">
        <i class="fa fa-search"></i>
    </button>
</div>
<div class="input-section" style="color: white">
    <label><input class="input-area" type="radio" name="searchby" value="byGroup">group</label>
    <label><input class="input-area" type="radio" name="searchby" value="byYear">year</label>
    <label><input class="input-area" type="radio" name="searchby" value="byMajor">major</label>
    <label><input class="input-area" type="radio" name="searchby" value="byName">name</label>
</div>
</div>

<div>
    <div class="container">
        <div style="display: none" class="row" id="errormsg">
            <table class="table table-sm table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">email</th>
                    <th scope="col">password</th>
                    <th scope="col">group</th>
                    <th scope="col">major</th>
                    <th scope="col">year</th>

                </tr>
                </thead>
                <tbody>
                <tr>
                    <th id="user_id">1</th>
                    <td id="fname"></td>
                    <td id="lname"></td>
                    <td id="email"></td>
                    <td id="password"></td>
                    <td id="group"></td>
                    <td id="major"></td>
                    <td id="year"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<table class="table table-sm table-dark">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">email</th>
        <th scope="col">password</th>
        <th scope="col">group</th>
        <th scope="col">major</th>
        <th scope="col">year</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${sessionScope.users}">
        <tr>
            <th>${user.id}</th>
            <td>${user.fname}</td>
            <td>${user.lname}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.group}</td>
            <td>${user.major}</td>
            <td>${user.year}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
        <p id="errrr" style="display:none;"></p>
<script type="text/javascript">
    $(document).ready(function (){
        $("#sbtn").on("click", function (){
            event.preventDefault();
            var a = $("input[name='searchby']:checked").val();
            var b =$( "#search" ).val();
            var url = "${pageContext.request.contextPath}/search?&action="+a+"&param="+b;

            $.ajax({
                url: url,
                type: 'GET',  // http method
                accepts: 'application/json; charset=utf-8',
                //contentType: "application/json",
                success: function (data) {
                    if(data[0].length != 0)
                    {
                        $('#errrr').hide();
                        $("#user_id").text(data[0].id);
                        $("#fname").text(data[0].fname);
                        $("#lname").attr('src', data[0].lname);
                        $("#email").attr('value', data[0].email);
                        $("#password").attr('value', data[0].password);
                        $("#group").attr('value',data[0].group_name);
                        $("#major").attr('value',data[0].major);
                        $("#year").attr('value',data[0].year);
                        $("#errormsg").show();
                    }
                    else{
                        alert("No result find");
                        $("#errrr").text("No result find");
                        $("#errormsg").hide();
                        $("#errrr").show();

                    }

                },
                error: function (errorData, textStatus, errorMessage) {
                    var msg = (errorData.responseJSON != null) ? errorData.responseJSON.errorMessage : '';
                    $("#errormsg").text('Error: ' + msg + ', ' + errorData.status);

                    $("#errormsg").show();
                }
            });

        });

</script>


<jsp:include page="jsp-components/footer.jsp"></jsp:include>
