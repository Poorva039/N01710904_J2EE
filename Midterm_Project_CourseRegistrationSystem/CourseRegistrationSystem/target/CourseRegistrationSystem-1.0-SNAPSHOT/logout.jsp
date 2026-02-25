<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Logout</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="header">
  <a href="index.jsp">Login</a>
</div>

<div class="container">
  <h2>Logout</h2>
  <div class="card">
    <p>You have been logged out.</p>
    <button onclick="go()">Go to Login</button>
  </div>
</div>

<script>
  localStorage.removeItem("token");
  function go() {
    window.location.href = "index.jsp";
  }
</script>

</body>
</html>