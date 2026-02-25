<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
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
    <h2>Courses</h2>

    <div class="row">
        <div class="card">
            <h3>Add / Update Course (Admin/Instructor)</h3>
            <label>ID (for update/delete) </label>
            <input id="cid" type="number" >

            <label>Code</label>
            <input id="ccode" type="text" >

            <label>Title</label>
            <input id="ctitle" type="text" >

            <label>Credit Hours</label>
            <input id="cch" type="number" >

            <label>Instructor ID</label>
            <input id="ciid" type="number" >

            <div class="actions">
                <button onclick="addCourse()">Add</button>
                <button class="secondary" onclick="updateCourse()">Update</button>
                <button class="danger" onclick="deleteCourse()">Delete by ID</button>
            </div>

            <div id="msg" class="msg" style="display:none;"></div>
        </div>

        <div class="card">
            <h3>All Courses</h3>
            <div id="tableWrap"></div>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>/api/courses";

    function token() {
        return localStorage.getItem("token");
    }
    function authHeaders() {
        return { "Authorization": "Bearer " + token(), "Content-Type": "application/json" };
    }
    function showMsg(text) {
        const el = document.getElementById("msg");
        el.style.display = "block";
        el.textContent = text;
    }
    function ensureLogin() {
        if (!token()) window.location.href = "index.jsp";
    }

    function readCourseFromInputs() {
        return {
            code: document.getElementById("ccode").value.trim(),
            title: document.getElementById("ctitle").value.trim(),
            creditHours: parseInt(document.getElementById("cch").value || "0", 10),
            instructorId: parseInt(document.getElementById("ciid").value || "0", 10)
        };
    }

    async function loadCourses() {
        ensureLogin();
        const res = await fetch(base, { headers: authHeaders() });
        const data = await res.json();

        let html = "<table><thead><tr><th>ID</th><th>Code</th><th>Title</th><th>Credit</th><th>Instructor</th></tr></thead><tbody>";
        data.forEach(c => {
            html += "<tr>"
                + "<td>" + c.id + "</td>"
                + "<td>" + c.code + "</td>"
                + "<td>" + c.title + "</td>"
                + "<td>" + c.creditHours + "</td>"
                + "<td>" + c.instructorId + "</td>"
                + "</tr>";
        });
        html += "</tbody></table>";
        document.getElementById("tableWrap").innerHTML = html;
    }

    async function addCourse() {
        ensureLogin();
        const course = readCourseFromInputs();

        const res = await fetch(base, {
            method: "POST",
            headers: authHeaders(),
            body: JSON.stringify(course)
        });

        const text = await res.text();
        showMsg(res.status + " " + text);
        loadCourses();
    }

    async function updateCourse() {
        ensureLogin();
        const id = document.getElementById("cid").value;
        if (!id) { showMsg("Enter ID for update."); return; }

        const course = readCourseFromInputs();

        const res = await fetch(base + "/" + id, {
            method: "PUT",
            headers: authHeaders(),
            body: JSON.stringify(course)
        });

        const text = await res.text();
        showMsg(res.status + " " + text);
        loadCourses();
    }

    async function deleteCourse() {
        ensureLogin();
        const id = document.getElementById("cid").value;
        if (!id) { showMsg("Enter ID to delete."); return; }

        const res = await fetch(base + "/" + id, {
            method: "DELETE",
            headers: authHeaders()
        });

        const text = await res.text();
        showMsg(res.status + " " + (text || "Deleted (or no content)."));
        loadCourses();
    }

    loadCourses();
</script>

</body>
</html>