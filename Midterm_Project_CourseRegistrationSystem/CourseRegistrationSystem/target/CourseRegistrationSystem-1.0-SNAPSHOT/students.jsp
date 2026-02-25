<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students</title>
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
    <h2>Students</h2>

    <div class="row">
        <div class="card">
            <h3>Add / Update Student (Admin only)</h3>

            <label>ID (for update/delete)</label>
            <input id="sid" type="number" >

            <label>First Name</label>
            <input id="sf" type="text" >

            <label>Last Name</label>
            <input id="sl" type="text" >

            <label>Email</label>
            <input id="se" type="text" >

            <div class="actions">
                <button onclick="addStudent()">Add</button>
                <button class="secondary" onclick="updateStudent()">Update</button>
                <button class="danger" onclick="deleteStudent()">Delete</button>
            </div>

            <div id="msg" class="msg" style="display:none;"></div>
        </div>

        <div class="card">
            <h3>All Students</h3>
            <div id="tableWrap"></div>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>/api/students";

    function token() { return localStorage.getItem("token"); }
    function authHeaders() { return { "Authorization": "Bearer " + token(), "Content-Type": "application/json" }; }
    function ensureLogin() { if (!token()) window.location.href = "index.jsp"; }
    function showMsg(text) { const el=document.getElementById("msg"); el.style.display="block"; el.textContent=text; }

    function readStudent() {
        return {
            firstName: document.getElementById("sf").value.trim(),
            lastName: document.getElementById("sl").value.trim(),
            email: document.getElementById("se").value.trim()
        };
    }

    async function loadStudents() {
        ensureLogin();
        const res = await fetch(base, { headers: authHeaders() });
        const data = await res.json();

        let html = "<table><thead><tr><th>ID</th><th>First</th><th>Last</th><th>Email</th><th>Enrolled Course IDs</th></tr></thead><tbody>";
        data.forEach(s => {
            const enrolled = (s.enrolledCourseIds || []).join(", ");
            html += "<tr>"
                + "<td>" + s.id + "</td>"
                + "<td>" + s.firstName + "</td>"
                + "<td>" + s.lastName + "</td>"
                + "<td>" + s.email + "</td>"
                + "<td>" + enrolled + "</td>"
                + "</tr>";
        });
        html += "</tbody></table>";
        document.getElementById("tableWrap").innerHTML = html;
    }

    async function addStudent() {
        ensureLogin();
        const res = await fetch(base, { method:"POST", headers: authHeaders(), body: JSON.stringify(readStudent()) });
        showMsg(res.status + " " + await res.text());
        loadStudents();
    }

    async function updateStudent() {
        ensureLogin();
        const id = document.getElementById("sid").value;
        if (!id) { showMsg("Enter ID for update."); return; }
        const res = await fetch(base + "/" + id, { method:"PUT", headers: authHeaders(), body: JSON.stringify(readStudent()) });
        showMsg(res.status + " " + await res.text());
        loadStudents();
    }

    async function deleteStudent() {
        ensureLogin();
        const id = document.getElementById("sid").value;
        if (!id) { showMsg("Enter ID for delete."); return; }
        const res = await fetch(base + "/" + id, { method:"DELETE", headers: authHeaders() });
        showMsg(res.status + " " + (await res.text() || "Deleted (or no content)."));
        loadStudents();
    }

    loadStudents();
</script>

</body>
</html>