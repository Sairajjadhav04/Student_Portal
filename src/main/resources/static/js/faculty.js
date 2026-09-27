// ===============================
// FACULTY PORTAL JS
// ===============================


// Get JWT token
function facultyToken() {

    return (
        localStorage.getItem("token") ||
        localStorage.getItem("jwt") ||
        sessionStorage.getItem("token") ||
        ""
    );
}


// Faculty API function
async function facultyApi(url, options = {}) {

    const headers = {
        ...(options.headers || {})
    };

    if (options.body) {
        headers["Content-Type"] = "application/json";
    }

    const token = facultyToken();

    if (token) {
        headers["Authorization"] = "Bearer " + token;
    }

    const response = await fetch(url, {
        ...options,
        headers: headers
    });

    const contentType = response.headers.get("content-type") || "";

    let data;

    if (contentType.includes("application/json")) {
        data = await response.json().catch(() => []);
    } else {
        data = await response.text();
    }

    if (response.status === 401) {
        localStorage.removeItem("token");
        localStorage.removeItem("jwt");
        sessionStorage.removeItem("token");

        window.location.href = "/login";

        return;
    }

    if (response.status === 403) {
        throw new Error("You are not authorized for this faculty action.");
    }

    if (!response.ok) {
        throw new Error(
            data?.message ||
            data?.error ||
            "Request failed: " + response.status
        );
    }

    return data;
}


// Convert API response to array
function facultyArray(data) {

    if (Array.isArray(data)) {
        return data;
    }

    if (Array.isArray(data?.content)) {
        return data.content;
    }

    if (Array.isArray(data?.data)) {
        return data.data;
    }

    if (Array.isArray(data?.items)) {
        return data.items;
    }

    return [];
}


// Escape HTML
function facultyEsc(value) {

    return String(value ?? "").replace(
        /[&<>"']/g,
        function (character) {

            return {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "'": "&#039;"
            }[character];

        }
    );
}


// Faculty initials
function facultyInitials(name) {

    return String(name || "Faculty")
        .trim()
        .split(/\s+/)
        .map(word => word[0])
        .slice(0, 2)
        .join("")
        .toUpperCase();
}


// Faculty message
function facultyToast(message) {

    alert(message);
}


// Open modal
function facultyOpenModal(id) {

    const modal = document.getElementById(id);

    if (modal) {
        modal.classList.add("show");
    }
}


// Close modal
function facultyCloseModal(id) {

    const modal = document.getElementById(id);

    if (modal) {
        modal.classList.remove("show");
    }
}


// Faculty logout
function facultyLogout() {

    localStorage.removeItem("token");
    localStorage.removeItem("jwt");

    sessionStorage.removeItem("token");

    window.location.href = "/login";
}


// Close modal by clicking outside
document.addEventListener("click", function (event) {

    if (event.target.classList.contains("portal-modal")) {
        event.target.classList.remove("show");
    }

});