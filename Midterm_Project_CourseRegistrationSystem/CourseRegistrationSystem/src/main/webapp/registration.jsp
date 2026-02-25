<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
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
    <h2>Registration (Enroll / Drop)</h2>

    <div class="row">
        <div class="card">
            <h3>Enroll</h3>

            <label>Student ID</label>
            <input id="enStudentId" type="number" >

            <label>Course ID</label>
            <input id="enCourseId" type="number" >

            <button onclick="enroll()">Enroll</button>
        </div>

        <div class="card">
            <h3>Drop</h3>

            <label>Student ID</label>
            <input id="drStudentId" type="number" >

            <label>Course ID</label>
            <input id="drCourseId" type="number" >

            <button class="danger" onclick="dropCourse()">Drop</button>
        </div>
    </div>

    <div id="msg" class="msg" style="display:none;"></div>
</div>

<script>
    const base = "<%= request.getContextPath() %>/api/registration";

    function token() {
        return localStorage.getItem("token");
    }

    function ensureLogin() {
        if (!token()) {
            window.location.href = "index.jsp";
        }
    }

    function headers() {
        return {
            "Authorization": "Bearer " + token()
        };
    }

    function showMsg(text) {
        const el = document.getElementById("msg");
        el.style.display = "block";
        el.textContent = text;
    }

    async function enroll() {
        ensureLogin();

        const studentId = document.getElementById("enStudentId").value;
        const courseId = document.getElementById("enCourseId").value;

        const url = base + "/enroll?studentId="
            + encodeURIComponent(studentId)
            + "&courseId="
            + encodeURIComponent(courseId);

        const res = await fetch(url, {
            method: "POST",
            headers: headers()
        });

        showMsg(res.status + " " + await res.text());
    }

    async function dropCourse() {
        ensureLogin();

        const studentId = document.getElementById("drStudentId").value;
        const courseId = document.getElementById("drCourseId").value;

        const url = base + "/drop?studentId="
            + encodeURIComponent(studentId)
            + "&courseId="
            + encodeURIComponent(courseId);

        const res = await fetch(url, {
            method: "POST",
            headers: headers()
        });

        showMsg(res.status + " " + await res.text());
    }
</script>

</body>
</html>