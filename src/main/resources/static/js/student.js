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

    console.log("Student:", data.student);
    console.log("Subjects:", data.subjects);
    console.log("Attendance:", data.attendance);
    console.log("Assignments:", data.assignments);
    console.log("Marks:", data.marks);
    console.log("Results:", data.results);

}