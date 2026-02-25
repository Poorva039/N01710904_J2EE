<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Dashboard</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="header">
  <a href="dashboard.jsp">Dashboard</a>
  <a href="courses.jsp">Courses</a>
  <a href="instructors.jsp">Instructors</a>
  <a href="students.jsp">Students</a>
  <a href="registration.jsp">Registration</a>
  <a href="logout.jsp">Logout</a>
</div>

<div class="container">
  <h2>Dashboard</h2>
  <div class="card">
    <div><b>Welcome!</b> </div>
    <div class="small">You are now logged into the Student Registration System.</div>
  </div>
</div>

<script>
  function decodeJwtPayload(token) {
    const parts = token.split(".");
    if (parts.length !== 3) return null;
    const base64 = parts[1].replaceAll("-", "+").replaceAll("_", "/");
    const json = decodeURIComponent(atob(base64).split("").map(c => "%" + ("00"+c.charCodeAt(0).toString(16)).slice(-2)).join(""));
    return JSON.parse(json);
  }

  const token = localStorage.getItem("token");
  const status = document.getElementById("status");
  const tokenInfo = document.getElementById("tokenInfo");

  if (!token) {
    status.textContent = "No token - please login.";
    window.location.href = "index.jsp";
  } else {
    status.textContent = "Logged in";
    const payload = decodeJwtPayload(token);
    tokenInfo.textContent = payload ? JSON.stringify(payload, null, 2) : "Could not decode token payload.";
  }
</script>

</body>
</html>