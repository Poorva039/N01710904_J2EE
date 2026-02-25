<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Course Registration - Login</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="header">
  <span>Course Registration System</span>
</div>

<div class="container">
  <h2>Login</h2>
  <p class="small">Login as: Admin / Instructor / Student</p>

  <div class="card">
    <label>Username</label>
    <input id="username" type="text">

    <label>Password</label>
    <input id="password" type="password" >

    <button onclick="login()">Login</button>
    <div id="msg" class="msg" style="display:none;"></div>
  </div>
</div>

<script>
  function showMsg(text) {
    const el = document.getElementById("msg");
    el.style.display = "block";
    el.textContent = text;
  }

  async function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!username || !password) {
      showMsg("Please enter username and password.");
      return;
    }

    try {
      const res = await fetch("<%=request.getContextPath()%>/api/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({username, password})
      });

      if (!res.ok) {
        showMsg("Login failed (401). Check username/password.");
        return;
      }

      const data = await res.json();
      localStorage.setItem("token", data.token);
      window.location.href = "dashboard.jsp";
    } catch (e) {
      showMsg("Error: " + e);
    }
  }
</script>
</body>
</html>