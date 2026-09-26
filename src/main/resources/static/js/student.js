document.addEventListener("DOMContentLoaded", function () {

    // Dashboard
    if (window.location.pathname === "/student/dashboard") {
        loadDashboard();
        loadAssignmentProgress();
    }

    // Subjects
    if (window.location.pathname === "/student/subjects") {
        loadSubjects();
    }

    // Attendance
    if (window.location.pathname === "/student/attendance") {
        loadAttendance();
    }

});


// =====================================================
// DASHBOARD
// =====================================================

async function loadDashboard() {

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
            throw new Error("Failed to load dashboard");
        }

        const data = await response.json();

        console.log("Dashboard data:", data);

        displayDashboard(data);

    } catch (error) {
        console.error("Dashboard error:", error);
    }
}


// =====================================================
// DISPLAY DASHBOARD
// =====================================================

function displayDashboard(data) {

    console.log("Dashboard data:", data);

    // -----------------------------
    // STUDENT NAME
    // -----------------------------

    const student = data.student;

    if (student) {

        const name = student.fullName || "Student";

        const studentName =
            document.getElementById("studentName");

        if (studentName) {
            studentName.textContent = name;
        }

        const welcomeMessage =
            document.getElementById("welcomeMessage");

        if (welcomeMessage) {
            welcomeMessage.textContent =
                "Welcome back, " + name;
        }

        const profileInitial =
            document.getElementById("profileInitial");

        if (profileInitial && name.length > 0) {
            profileInitial.textContent =
                name.charAt(0).toUpperCase();
        }
    }


    // -----------------------------
    // SUBJECT COUNT
    // -----------------------------

    const subjectValue =
        document.getElementById("subjectValue");

    if (subjectValue) {
        subjectValue.textContent =
            data.subjects ? data.subjects.length : 0;
    }


    // -----------------------------
    // ASSIGNMENT COUNT
    // -----------------------------

    const assignmentValue =
        document.getElementById("assignmentValue");

    if (assignmentValue) {
        assignmentValue.textContent =
            data.assignments ? data.assignments.length : 0;
    }


    // -----------------------------
    // ATTENDANCE
    // -----------------------------

    const attendance = data.attendance || [];

    const attendanceValue =
        document.getElementById("attendanceValue");

    if (attendanceValue) {

        if (attendance.length > 0) {
            attendanceValue.textContent =
                attendance.length + " records";
        } else {
            attendanceValue.textContent = "0%";
        }
    }


    // -----------------------------
    // ATTENDANCE PROGRESS
    // -----------------------------

    const totalClasses = attendance.length;

    const presentClasses = attendance.filter(
        record => record.present === true
    ).length;

    const attendancePercentage =
        totalClasses > 0
            ? (presentClasses / totalClasses) * 100
            : 0;


    const dashboardAttendanceProgress =
        document.getElementById(
            "dashboardAttendanceProgress"
        );

    if (dashboardAttendanceProgress) {
        dashboardAttendanceProgress.textContent =
            attendancePercentage.toFixed(1) + "%";
    }


    const dashboardAttendanceBar =
        document.getElementById(
            "dashboardAttendanceBar"
        );

    if (dashboardAttendanceBar) {
        dashboardAttendanceBar.style.width =
            attendancePercentage + "%";
    }


    // -----------------------------
    // CGPA
    // -----------------------------

    const cgpaValue =
        document.getElementById("cgpaValue");

    if (cgpaValue) {
        cgpaValue.textContent = "--";
    }
}


// =====================================================
// ASSIGNMENT PROGRESS
// =====================================================

async function loadAssignmentProgress() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const [
            assignmentsResponse,
            submissionsResponse
        ] = await Promise.all([

            fetch("/api/student/academics/assignments", {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }),

            fetch("/api/student/academics/submissions", {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            })

        ]);

        // =====================================================
// DASHBOARD RECENT ASSIGNMENTS
// =====================================================

        async function loadDashboardAssignments() {

            const token = localStorage.getItem("token");

            if (!token) {
                window.location.href = "/login";
                return;
            }

            const assignmentList =
                document.getElementById("dashboardAssignmentList");

            if (!assignmentList) {
                return;
            }

            try {

                const response = await fetch(
                    "/api/student/academics/assignments",
                    {
                        method: "GET",
                        headers: {
                            "Authorization": "Bearer " + token
                        }
                    }
                );

                // Authentication check
                if (
                    response.status === 401 ||
                    response.status === 403
                ) {
                    localStorage.removeItem("token");
                    window.location.href = "/login";
                    return;
                }

                if (!response.ok) {
                    throw new Error("Failed to load assignments");
                }

                const assignments =
                    await response.json();

                console.log(
                    "Dashboard assignments:",
                    assignments
                );

                displayDashboardAssignments(assignments);

            } catch (error) {

                console.error(
                    "Dashboard assignments error:",
                    error
                );

                assignmentList.innerHTML = `
        <p>
            Unable to load assignments.
        </p>
    `;
            }

        }

// =====================================================
// DISPLAY DASHBOARD ASSIGNMENTS
// =====================================================

        function displayDashboardAssignments(assignments) {

            const assignmentList =
                document.getElementById(
                    "dashboardAssignmentList"
                );

            if (!assignmentList) {
                return;
            }

            assignments = assignments || [];

// No assignments
            if (assignments.length === 0) {

                assignmentList.innerHTML = `
        <p>
            No assignments available.
        </p>
    `;

                return;
            }

// Show only latest 3 assignments
            const recentAssignments =
                assignments.slice(0, 3);

            assignmentList.innerHTML = "";

            recentAssignments.forEach(function (assignment) {

                const item =
                    document.createElement("div");

                item.className =
                    "assignment-item";

                const subjectName =
                    assignment.subject?.name ||
                    "Subject not assigned";

                const dueDate =
                    assignment.dueDate
                        ? formatDashboardDate(
                            assignment.dueDate
                        )
                        : "No due date";

                item.innerHTML = `

        <div class="assignment-details">

            <strong>
                ${assignment.title || "Untitled Assignment"}
            </strong>

            <p>
                ${subjectName}
            </p>

        </div>

        <span class="assignment-date">
            ${dueDate}
        </span>

        <span class="assignment-status status-pending">
            Pending
        </span>

    `;

                assignmentList.appendChild(item);

            });

        }

// =====================================================
// FORMAT ASSIGNMENT DATE
// =====================================================

        function formatDashboardDate(dateValue) {

            const date =
                new Date(dateValue);

            if (isNaN(date.getTime())) {
                return dateValue;
            }

            return date.toLocaleDateString(
                "en-IN",
                {
                    day: "2-digit",
                    month: "short"
                }
            );

        }


        // -----------------------------
        // AUTH CHECK
        // -----------------------------

        if (
            assignmentsResponse.status === 401 ||
            assignmentsResponse.status === 403 ||
            submissionsResponse.status === 401 ||
            submissionsResponse.status === 403
        ) {

            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }


        if (
            !assignmentsResponse.ok ||
            !submissionsResponse.ok
        ) {
            throw new Error(
                "Failed to load assignment data"
            );
        }


        const assignments =
            await assignmentsResponse.json();

        const submissions =
            await submissionsResponse.json();


        console.log(
            "REAL ASSIGNMENTS:",
            assignments
        );

        console.log(
            "REAL SUBMISSIONS:",
            submissions
        );


        // -----------------------------
        // CALCULATE PROGRESS
        // -----------------------------

        const totalAssignments =
            assignments.length;

        const completedAssignments =
            submissions.length;

        let percentage = 0;

        if (totalAssignments > 0) {

            percentage =
                (completedAssignments /
                    totalAssignments) * 100;
        }

        percentage =
            Math.min(percentage, 100);


        console.log(
            "Assignment Progress:",
            percentage + "%"
        );


        // -----------------------------
        // DISPLAY PERCENTAGE
        // -----------------------------

        const progressText =
            document.getElementById(
                "dashboardAssignmentProgress"
            );

        if (progressText) {
            progressText.textContent =
                percentage.toFixed(1) + "%";
        }


        // -----------------------------
        // PROGRESS BAR
        // -----------------------------

        const progressBar =
            document.getElementById(
                "dashboardAssignmentBar"
            );

        if (progressBar) {
            progressBar.style.width =
                percentage + "%";
        }

    } catch (error) {

        console.error(
            "Assignment progress error:",
            error
        );
    }
}


// =====================================================
// SUBJECTS
// =====================================================

async function loadSubjects() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch(
            "/api/student/dashboard",
            {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        if (
            response.status === 401 ||
            response.status === 403
        ) {

            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }


        if (!response.ok) {
            throw new Error(
                "Failed to load subjects"
            );
        }


        const data =
            await response.json();

        console.log(
            "Subjects:",
            data.subjects
        );

        displaySubjects(data.subjects);

    } catch (error) {

        console.error(
            "Subjects error:",
            error
        );
    }
}


// =====================================================
// DISPLAY SUBJECTS
// =====================================================

function displaySubjects(subjects) {

    const count =
        document.getElementById("subjectCount");

    const tableBody =
        document.getElementById(
            "subjectsTableBody"
        );


    subjects = subjects || [];


    // Subject count
    if (count) {
        count.textContent =
            subjects.length;
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


    tableBody.innerHTML = "";


    subjects.forEach(function (subject) {

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


// =====================================================
// ATTENDANCE
// =====================================================

async function loadAttendance() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch(
            "/api/student/dashboard",
            {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        if (
            response.status === 401 ||
            response.status === 403
        ) {

            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }


        if (!response.ok) {
            throw new Error(
                "Failed to load attendance"
            );
        }


        const data =
            await response.json();


        console.log(
            "Attendance:",
            data.attendance
        );


        displayAttendance(
            data.attendance
        );

    } catch (error) {

        console.error(
            "Attendance error:",
            error
        );
    }
}


// =====================================================
// DISPLAY ATTENDANCE
// =====================================================

function displayAttendance(attendance) {

    attendance = attendance || [];


    const total =
        attendance.length;


    const present =
        attendance.filter(
            record => record.present === true
        ).length;


    const absent =
        total - present;


    const percentage =
        total > 0
            ? (present / total) * 100
            : 0;


    // -----------------------------
    // OVERALL ATTENDANCE
    // -----------------------------

    const overall =
        document.getElementById(
            "overallAttendance"
        );

    if (overall) {
        overall.textContent =
            percentage.toFixed(1) + "%";
    }


    // -----------------------------
    // TOTAL CLASSES
    // -----------------------------

    const totalElement =
        document.getElementById(
            "totalClasses"
        );

    if (totalElement) {
        totalElement.textContent =
            total;
    }


    // -----------------------------
    // PRESENT CLASSES
    // -----------------------------

    const presentElement =
        document.getElementById(
            "presentClasses"
        );

    if (presentElement) {
        presentElement.textContent =
            present;
    }


    // -----------------------------
    // ABSENT CLASSES
    // -----------------------------

    const absentElement =
        document.getElementById(
            "absentClasses"
        );

    if (absentElement) {
        absentElement.textContent =
            absent;
    }


    // -----------------------------
    // ATTENDANCE STATUS
    // -----------------------------

    const status =
        document.getElementById(
            "attendanceStatus"
        );

    if (status) {

        if (percentage >= 75) {
            status.textContent =
                "Good standing";
        } else {
            status.textContent =
                "Attendance is low";
        }
    }


    // -----------------------------
    // ATTENDANCE TABLE
    // -----------------------------

    const tableBody =
        document.getElementById(
            "attendanceTableBody"
        );


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


    // -----------------------------
    // GROUP BY SUBJECT
    // -----------------------------

    const subjectMap = {};


    attendance.forEach(function (record) {

        const subject =
            record.subject;


        if (!subject) {
            return;
        }


        const subjectId =
            subject.id;


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


    // -----------------------------
    // CREATE TABLE ROWS
    // -----------------------------

    Object.values(subjectMap).forEach(
        function (subject) {

            const total =
                subject.total;

            const present =
                subject.present;

            const absent =
                total - present;


            const percentage =
                total > 0
                    ? ((present / total) * 100)
                        .toFixed(1)
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

        }
    );
}

document.addEventListener("DOMContentLoaded", loadAssignments);

async function loadAssignments() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch("/api/student/assignments", {
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
            throw new Error("Failed to load assignments");
        }

        const assignments = await response.json();

        console.log("Assignments:", assignments);

        displayAssignments(assignments);

    } catch (error) {

        console.error("Assignment error:", error);

        document.getElementById("assignmentTableBody").innerHTML = `
            <tr>
                <td colspan="5">Unable to load assignments.</td>
            </tr>
        `;
    }
}


function displayAssignments(assignments) {

    const tableBody =
        document.getElementById("assignmentTableBody");

    const totalAssignments =
        document.getElementById("totalAssignments");

    const pendingAssignments =
        document.getElementById("pendingAssignments");

    const dueSoonAssignments =
        document.getElementById("dueSoonAssignments");


    totalAssignments.textContent = assignments.length;


    if (assignments.length === 0) {

        pendingAssignments.textContent = "0";
        dueSoonAssignments.textContent = "0";

        tableBody.innerHTML = `
            <tr>
                <td colspan="5">No assignments available.</td>
            </tr>
        `;

        return;
    }


    const today = new Date();

    today.setHours(0, 0, 0, 0);

    let pending = 0;
    let dueSoon = 0;

    tableBody.innerHTML = "";


    assignments.forEach(assignment => {

        const dueDate = assignment.dueDate
            ? new Date(assignment.dueDate + "T00:00:00")
            : null;

        let status = "No Due Date";

        if (dueDate) {

            const difference =
                Math.ceil(
                    (dueDate - today) /
                    (1000 * 60 * 60 * 24)
                );

            if (difference < 0) {

                status = "Overdue";

            } else {

                pending++;

                if (difference <= 7) {
                    dueSoon++;
                    status = "Due Soon";
                } else {
                    status = "Pending";
                }
            }
        }


        const subjectName =
            assignment.subject
                ? assignment.subject.name
                : "Not Assigned";


        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${assignment.title || "-"}</td>

            <td>${subjectName}</td>

            <td>${assignment.description || "-"}</td>

            <td>${assignment.dueDate || "-"}</td>

            <td>${status}</td>
        `;

        tableBody.appendChild(row);
    });


    pendingAssignments.textContent = pending;

    dueSoonAssignments.textContent = dueSoon;
}
document.addEventListener("DOMContentLoaded", loadMarks);


async function loadMarks() {

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
            throw new Error("Failed to load marks");
        }


        const data = await response.json();

        console.log("Marks:", data.marks);

        displayMarks(data.marks || []);


    } catch (error) {

        console.error("Marks error:", error);

        document.getElementById("marksTableBody").innerHTML = `
            <tr>
                <td colspan="5">
                    Unable to load marks.
                </td>
            </tr>
        `;
    }
}


function displayMarks(marks) {

    const tableBody =
        document.getElementById("marksTableBody");


    const totalExams =
        document.getElementById("totalExams");

    const totalObtained =
        document.getElementById("totalObtained");

    const totalMarks =
        document.getElementById("totalMarks");

    const percentage =
        document.getElementById("percentage");


    totalExams.textContent = marks.length;


    if (marks.length === 0) {

        totalObtained.textContent = "0";
        totalMarks.textContent = "0";
        percentage.textContent = "0%";

        tableBody.innerHTML = `
            <tr>
                <td colspan="5">
                    No marks available.
                </td>
            </tr>
        `;

        return;
    }


    let obtained = 0;
    let maximum = 0;


    tableBody.innerHTML = "";


    marks.forEach(mark => {

        const obtainedMarks =
            Number(mark.obtainedMarks) || 0;

        const totalMarksValue =
            Number(mark.totalMarks) || 0;


        obtained += obtainedMarks;
        maximum += totalMarksValue;


        let examPercentage = 0;

        if (totalMarksValue > 0) {
            examPercentage =
                (obtainedMarks / totalMarksValue) * 100;
        }


        const subjectName =
            mark.subject
                ? mark.subject.name
                : "Not Assigned";


        const row = document.createElement("tr");


        row.innerHTML = `
            <td>${subjectName}</td>

            <td>${mark.examType || "-"}</td>

            <td>${obtainedMarks}</td>

            <td>${totalMarksValue}</td>

            <td>${examPercentage.toFixed(2)}%</td>
        `;


        tableBody.appendChild(row);

    });


    totalObtained.textContent =
        obtained.toFixed(2);

    totalMarks.textContent =
        maximum.toFixed(2);


    const overallPercentage =
        maximum > 0
            ? (obtained / maximum) * 100
            : 0;


    percentage.textContent =
        overallPercentage.toFixed(2) + "%";
}
document.addEventListener("DOMContentLoaded", loadResults);


async function loadResults() {

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
            throw new Error("Failed to load results");
        }


        const data = await response.json();

        console.log("Results:", data.results);

        displayResults(data.results || []);


    } catch (error) {

        console.error("Results error:", error);

        document.getElementById("resultsTableBody").innerHTML = `
            <tr>
                <td colspan="3">
                    Unable to load results.
                </td>
            </tr>
        `;
    }
}


function displayResults(results) {

    const tableBody =
        document.getElementById("resultsTableBody");

    const totalSemesters =
        document.getElementById("totalSemesters");

    const latestSgpa =
        document.getElementById("latestSgpa");

    const bestSgpa =
        document.getElementById("bestSgpa");

    const overallStatus =
        document.getElementById("overallStatus");


    totalSemesters.textContent = results.length;


    if (results.length === 0) {

        latestSgpa.textContent = "--";
        bestSgpa.textContent = "--";
        overallStatus.textContent = "--";

        tableBody.innerHTML = `
            <tr>
                <td colspan="3">
                    No results available.
                </td>
            </tr>
        `;

        return;
    }


    // Sort by semester
    results.sort((a, b) =>
        (a.semester || 0) - (b.semester || 0)
    );


    let best = 0;

    results.forEach(result => {

        const sgpa = Number(result.sgpa) || 0;

        if (sgpa > best) {
            best = sgpa;
        }

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>Semester ${result.semester || "-"}</td>
            <td>${result.sgpa != null
            ? Number(result.sgpa).toFixed(2)
            : "-"}</td>
            <td>${result.status || "-"}</td>
        `;

        tableBody.appendChild(row);
    });


    // Last semester = latest result
    const latest = results[results.length - 1];


    latestSgpa.textContent =
        latest.sgpa != null
            ? Number(latest.sgpa).toFixed(2)
            : "--";


    bestSgpa.textContent =
        best.toFixed(2);


    overallStatus.textContent =
        latest.status || "--";
}