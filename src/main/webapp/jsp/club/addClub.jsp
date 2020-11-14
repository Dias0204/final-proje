<jsp:include page="../jsp-components/footer.jsp"></jsp:include>
<form action="${pageContext.request.contextPath }/clubServlet" method="get" style="margin-bottom: 200px">
    <div class="login-box">
        <div class="input-section">
            <p id="checkemail"></p>
            <input class="input-area" type="email" id="emailInput" placeholder="email" name="email">
        </div>
        <div class="input-section">
            <input class="input-area" type="password" placeholder="Password" name="pass">
        </div>
        <div class="input-section">
            <input class="input-area" type="text" placeholder="first name" name="fname">
        </div>
        <div class="input-section">
            <input class="input-area" type="text" placeholder="last name" name="lname">
        </div>
        <div class="input-section">
            <p id="checkyear"></p>
            <input class="input-area" id="yearInput" type="text" placeholder="year" name="year">
        </div>
        <div class="input-section">
            <input class="input-area" type="text" placeholder="major" name="major">
        </div>
        <div class="input-section">
            <input class="input-area" type="text" placeholder="group" name="group">
        </div>
        <button class="btn" id="regbtn" type="submit" name="action" value="add">add</button>
    </div>
</form>

<jsp:include page="../jsp-components/footer.jsp"></jsp:include>