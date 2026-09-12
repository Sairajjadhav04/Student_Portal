document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {

    const token = localStorage.getItem("token");

    // Check whether user is logged in
    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch("/api/student/dashboard", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }

        if (!response.ok) {
            throw new Error("Failed to load dashboard");
        }

        const data = await response.json();

        console.log("Dashboard data:", data);

        displayDashboard(data);

    } catch (error) {

        console.error("Dashboard error:", error);

    }
}


function displayDashboard(data) {

    console.log("Dashboard data:", data);

    // ==============================
    // STUDENT NAME
    // ==============================

    const student = data.student;

    if (student) {

        const name = student.fullName || "Student";

        // Top-right name
        const studentName =
            document.getElementById("studentName");

        if (studentName) {
            studentName.textContent = name;
        }


        // Welcome message
        const welcomeMessage =
            document.getElementById("welcomeMessage");

        if (welcomeMessage) {
            welcomeMessage.textContent =
                "Welcome back, " + name;
        }


        // Profile initial
        const profileInitial =
            document.getElementById("profileInitial");

        if (profileInitial && name.length > 0) {
            profileInitial.textContent =
                name.charAt(0).toUpperCase();
        }
    }


    // ==============================
    // SUBJECT COUNT
    // ==============================

    const subjectValue =
        document.getElementById("subjectValue");

    if (subjectValue) {
        subjectValue.textContent =
            data.subjects ? data.subjects.length : 0;
    }


    // ==============================
    // ASSIGNMENT COUNT
    // ==============================

    const assignmentValue =
        document.getElementById("assignmentValue");

    if (assignmentValue) {
        assignmentValue.textContent =
            data.assignments ? data.assignments.length : 0;
    }


    // ==============================
    // ATTENDANCE
    // ==============================

    const attendanceValue =
        document.getElementById("attendanceValue");

    if (attendanceValue) {

        if (data.attendance && data.attendance.length > 0) {

            attendanceValue.textContent =
                data.attendance.length + " records";

        } else {

            attendanceValue.textContent = "0%";
        }
    }


    // ==============================
    // CGPA
    // ==============================

    const cgpaValue =
        document.getElementById("cgpaValue");

    if (cgpaValue) {
        cgpaValue.textContent = "--";
    }

}
// =========================================
// SUBJECTS
// =========================================

async function loadSubjects() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch("/api/student/dashboard", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }

        if (!response.ok) {
            throw new Error("Failed to load subjects");
        }

        const data = await response.json();

        console.log("Subjects:", data.subjects);

        displaySubjects(data.subjects);

    } catch (error) {

        console.error("Subjects error:", error);

    }
}


function displaySubjects(subjects) {

    const count =
        document.getElementById("subjectCount");

    const tableBody =
        document.getElementById("subjectsTableBody");


    if (!subjects) {
        subjects = [];
    }


    // Subject count
    if (count) {
        count.textContent = subjects.length;
    }


    if (!tableBody) {
        return;
    }


    // No subjects
    if (subjects.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="4">
                    No subjects found.
                </td>
            </tr>
        `;

        return;
    }


    // Clear table
    tableBody.innerHTML = "";


    subjects.forEach(function(subject) {

        const row =
            document.createElement("tr");


        const facultyName =
            subject.faculty?.fullName ||
            subject.faculty?.name ||
            "Not Assigned";


        row.innerHTML = `

            <td class="subject-code">
                ${subject.code || "-"}
            </td>

            <td class="subject-name">
                ${subject.name || "-"}
            </td>

            <td class="subject-credits">
                ${subject.credits ?? "-"}
            </td>

            <td class="subject-faculty">
                ${facultyName}
            </td>

        `;


        tableBody.appendChild(row);

    });

}


// Load subjects only on Subjects page
if (window.location.pathname === "/student/subjects") {

    document.addEventListener("DOMContentLoaded", function () {

        loadSubjects();

    });

}
// =========================================
// ATTENDANCE
// =========================================

async function loadAttendance() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch("/api/student/dashboard", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.status === 401 || response.status === 403) {

            localStorage.removeItem("token");
            window.location.href = "/login";
            return;

        }

        if (!response.ok) {
            throw new Error("Failed to load attendance");
        }

        const data = await response.json();

        console.log("Attendance:", data.attendance);

        displayAttendance(data.attendance);

    } catch (error) {

        console.error("Attendance error:", error);

    }
}


function displayAttendance(attendance) {

    if (!attendance) {
        attendance = [];
    }


    let total = attendance.length;

    let present = attendance.filter(
        record => record.present === true
    ).length;

    let absent = total - present;


    let percentage = total > 0
        ? (present / total) * 100
        : 0;


    percentage = percentage.toFixed(1);


    // Overall values

    const overall =
        document.getElementById("overallAttendance");

    if (overall) {
        overall.textContent = percentage + "%";
    }


    const totalElement =
        document.getElementById("totalClasses");

    if (totalElement) {
        totalElement.textContent = total;
    }


    const presentElement =
        document.getElementById("presentClasses");

    if (presentElement) {
        presentElement.textContent = present;
    }


    const absentElement =
        document.getElementById("absentClasses");

    if (absentElement) {
        absentElement.textContent = absent;
    }


    // Attendance status

    const status =
        document.getElementById("attendanceStatus");

    if (status) {

        if (percentage >= 75) {
            status.textContent = "Good standing";
        } else {
            status.textContent = "Attendance is low";
        }

    }


    // Table

    const tableBody =
        document.getElementById("attendanceTableBody");

    if (!tableBody) {
        return;
    }


    if (attendance.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="6">
                    No attendance records found.
                </td>
            </tr>
        `;

        return;
    }


    /*
       Group attendance by subject
    */

    const subjectMap = {};


    attendance.forEach(function(record) {

        const subject = record.subject;

        if (!subject) {
            return;
        }


        const subjectId = subject.id;


        if (!subjectMap[subjectId]) {

            subjectMap[subjectId] = {

                name: subject.name || "-",

                code: subject.code || "-",

                total: 0,

                present: 0

            };

        }


        subjectMap[subjectId].total++;


        if (record.present === true) {
            subjectMap[subjectId].present++;
        }

    });


    tableBody.innerHTML = "";


    Object.values(subjectMap).forEach(function(subject) {

        const total = subject.total;

        const present = subject.present;

        const absent = total - present;


        const percentage = total > 0
            ? ((present / total) * 100).toFixed(1)
            : "0.0";


        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td class="subject-name">
                ${subject.name}
            </td>

            <td class="subject-code">
                ${subject.code}
            </td>

            <td>
                ${total}
            </td>

            <td>
                ${present}
            </td>

            <td>
                ${absent}
            </td>

            <td>
                <strong>
                    ${percentage}%
                </strong>
            </td>

        `;


        tableBody.appendChild(row);

    });

}


// Load attendance only on Attendance page

if (window.location.pathname === "/student/attendance") {

    document.addEventListener("DOMContentLoaded", function () {

        loadAttendance();

    });

}