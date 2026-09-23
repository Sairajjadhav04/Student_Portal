document.addEventListener("DOMContentLoaded", function () {

    loadQuizzes();

});


async function loadQuizzes() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    try {

        const response = await fetch("/api/quiz/student/available", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Failed to load quizzes");
        }

        const quizzes = await response.json();

        displayQuizzes(quizzes);

    } catch (error) {

        console.error(error);

        document.getElementById("quiz-list").innerHTML =
            "<p>Unable to load quizzes.</p>";
    }
}


function displayQuizzes(quizzes) {

    const quizList = document.getElementById("quiz-list");

    quizList.innerHTML = "";

    if (!quizzes || quizzes.length === 0) {

        quizList.innerHTML =
            "<p>No quizzes available.</p>";

        return;
    }

    quizzes.forEach(function (quiz) {

        const card = document.createElement("div");

        card.className = "quiz-card";

        card.innerHTML = `
            <h3>${quiz.title}</h3>

            <p>${quiz.description || "No description available."}</p>

            <p>
                Duration: ${quiz.durationMinutes || 0} minutes
            </p>

            <p>
                Total Marks: ${quiz.totalMarks || 0}
            </p>

            <button
                class="start-btn"
                onclick="startQuiz(${quiz.id})">
                Start Quiz
            </button>
        `;

        quizList.appendChild(card);

    });
}


async function startQuiz(quizId) {

    const token = localStorage.getItem("token");

    try {

        const response = await fetch(
            "/api/quiz/student/" + quizId + "/start",
            {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );

        if (!response.ok) {
            throw new Error("Unable to start quiz");
        }

        const attempt = await response.json();

        console.log("Quiz attempt:", attempt);

        alert("Quiz started successfully!");

    } catch (error) {

        console.error(error);

        alert("Unable to start quiz.");
    }
}