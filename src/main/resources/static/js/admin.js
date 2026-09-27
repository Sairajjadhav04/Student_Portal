// ===============================
// ADMIN + COMMON PORTAL JS
// ===============================

// Get JWT token
function portalToken() {
    return (
        localStorage.getItem("token") ||
        localStorage.getItem("jwt") ||
        sessionStorage.getItem("token") ||
        ""
    );
}


// Common API function
async function portalApi(url, options = {}) {

    const headers = {
        ...(options.headers || {})
    };

    // Add JSON header when sending body
    if (
        options.body &&
        !(options.body instanceof URLSearchParams) &&
        !(options.body instanceof FormData)
    ) {
        headers["Content-Type"] = "application/json";
    }

    // Add JWT token
    const token = portalToken();

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

    // Handle unauthorized
    if (response.status === 401 || response.status === 403) {
        throw new Error("You are not authorized to perform this action.");
    }

    // Handle other errors
    if (!response.ok) {
        throw new Error(
            data?.message ||
            data?.error ||
            "Request failed: " + response.status
        );
    }

    return data;
}


// Convert API response into array
function portalArray(data) {

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


// Escape HTML values
function pEsc(value) {

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


// Create initials for profile avatar
function portalInitials(name) {

    return String(name || "Admin")
        .trim()
        .split(/\s+/)
        .map(word => word[0])
        .slice(0, 2)
        .join("")
        .toUpperCase();
}


// Simple message
function portalToast(message) {

    alert(message);
}


// Open modal
function openPortalModal(id) {

    const modal = document.getElementById(id);

    if (modal) {
        modal.classList.add("show");
    }
}


// Close modal
function closePortalModal(id) {

    const modal = document.getElementById(id);

    if (modal) {
        modal.classList.remove("show");
    }
}


// Logout
function portalLogout() {

    localStorage.removeItem("token");
    localStorage.removeItem("jwt");

    sessionStorage.removeItem("token");

    window.location.href = "/login";
}


// Close modal when clicking outside
document.addEventListener("click", function (event) {

    if (event.target.classList.contains("portal-modal")) {
        event.target.classList.remove("show");
    }

});