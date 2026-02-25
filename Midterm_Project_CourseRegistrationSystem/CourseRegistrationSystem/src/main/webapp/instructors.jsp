<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Instructors</title>
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
    <h2>Instructors</h2>

    <div class="row">
        <div class="card">
            <h3>Add / Update Instructor (Admin/Instructor)</h3>

            <label>ID (for update/delete)</label>
            <input id="iid" type="number" >

            <label>Name</label>
            <input id="iname" type="text" >

            <label>Email</label>
            <input id="iemail" type="text" >

            <label>Department</label>
            <input id="idept" type="text" >

            <div class="actions">
                <button onclick="addInstructor()">Add</button>
                <button class="secondary" onclick="updateInstructor()">Update</button>
                <button class="danger" onclick="deleteInstructor()">Delete</button>
            </div>

            <div id="msg" class="msg" style="display:none;"></div>
        </div>

        <div class="card">
            <h3>All Instructors</h3>
            <div id="tableWrap"></div>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>/api/instructors";

    function token() { return localStorage.getItem("token"); }
    function authHeaders() { return { "Authorization": "Bearer " + token(), "Content-Type": "application/json" }; }
    function ensureLogin() { if (!token()) window.location.href = "index.jsp"; }
    function showMsg(text) { const el = document.getElementById("msg"); el.style.display="block"; el.textContent=text; }

    function readInstructor() {
        return {
            name: document.getElementById("iname").value.trim(),
            email: document.getElementById("iemail").value.trim(),
            department: document.getElementById("idept").value.trim()
        };
    }

    async function loadInstructors() {
        ensureLogin();
        const res = await fetch(base, { headers: authHeaders() });
        const data = await res.json();

        let html = "<table><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Department</th></tr></thead><tbody>";
        data.forEach(i => {
            html += "<tr>"
                + "<td>" + i.id + "</td>"
                + "<td>" + i.name + "</td>"
                + "<td>" + i.email + "</td>"
                + "<td>" + i.department + "</td>"
                + "</tr>";
        });
        html += "</tbody></table>";
        document.getElementById("tableWrap").innerHTML = html;
    }

    async function addInstructor() {
        ensureLogin();
        const res = await fetch(base, { method:"POST", headers: authHeaders(), body: JSON.stringify(readInstructor()) });
        showMsg(res.status + " " + await res.text());
        loadInstructors();
    }

    async function updateInstructor() {
        ensureLogin();
        const id = document.getElementById("iid").value;
        if (!id) { showMsg("Enter ID for update."); return; }
        const res = await fetch(base + "/" + id, { method:"PUT", headers: authHeaders(), body: JSON.stringify(readInstructor()) });
        showMsg(res.status + " " + await res.text());
        loadInstructors();
    }

    async function deleteInstructor() {
        ensureLogin();
        const id = document.getElementById("iid").value;
        if (!id) { showMsg("Enter ID for delete."); return; }
        const res = await fetch(base + "/" + id, { method:"DELETE", headers: authHeaders() });
        showMsg(res.status + " " + (await res.text() || "Deleted (or no content)."));
        loadInstructors();
    }

    loadInstructors();
</script>

</body>
</html>