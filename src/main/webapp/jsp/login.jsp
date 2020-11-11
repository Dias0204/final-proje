<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login</title>
    <!-- CORE CSS-->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css">

    <style type="text/css">
        html,
        body {
            height: 100%;
        }
        html {
            display: table;
            margin: auto;
        }
        body {
            display: table-cell;
            vertical-align: middle;
        }
        .margin {
            margin: 0 !important;
        }
    </style>

</head>

<body class="red">


<div id="login-page" class="row">
    <div class="col s12 z-depth-6 card-panel">
        <form class="login-form" action="${pageContext.request.contextPath }/login" method="post">
            <div class="row">
                <div class="input-field col s12 center">
                    <img src="data:image/png;base64,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" alt="" class="responsive-img valign profile-image-login" style="height: 100px">
                    <p class="center login-form-text">Welcome to Login Form</p>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input class="validate" id="email" type="email">
                    <label for="email" data-error="wrong" data-success="right" class="center-align">Email</label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-action-lock-outline prefix"></i>
                    <input id="password" type="password">
                    <label for="password">Password</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12 m12 l12  login-text">
                    <input type="checkbox" id="remember-me" />
                    <label for="remember-me">Remember me</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <button class="btn waves-effect waves-light col s12">Login</button>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6 m6 l6">
                    <p class="margin medium-small"><a href="register.html">Register Now!</a></p>
                </div>

            </div>

        </form>
    </div>
</div>


<center>
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <!-- Post Page - Responsive -->
    <ins class="adsbygoogle"
         style="display:inline-block;width:300px;height:250px"
         data-ad-client="ca-pub-5104998679826243"
         data-ad-slot="3470684978"></ins>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({});
    </script>
</center>



<!-- ================================================
  Scripts
  ================================================ -->

<!-- jQuery Library -->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!--materialize js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js"></script>

<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-27820211-3', 'auto');
    ga('send', 'pageview');

</script><script src="//load.sumome.com/" data-sumo-site-id="1cf2c3d548b156a57050bff06ee37284c67d0884b086bebd8e957ca1c34e99a1" async="async"></script>


<footer class="page-footer">
    <div class="footer-copyright">
        <div class="container">
            © 2015 W3lessons.info
            <a class="grey-text text-lighten-4 right" href="http://w3lessons.info">Karthikeyan K</a>
        </div>
    </div>
</footer>
</body>

</html>