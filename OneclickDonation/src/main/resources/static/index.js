async function index() {
    localStorage.removeItem("token");
    location.replace('/donation');
}
document.addEventListener("DOMContentLoaded", function() {
    const logoutButton = document.getElementById("logoutButton");
    if (logoutButton) {
        logoutButton.addEventListener("click", index);
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    form.onsubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const json = Object.fromEntries(formData.entries());
        const response = await fetch('/donation/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json) // FormData를 JSON 문자열로 변환
        });
        if (response.ok) {
            const data = await response.json();
            console.log(`data = ${JSON.stringify(data)}`);
            localStorage.setItem("token", data.token);
            console.log(data.token);
            fetchData("/")
            location.replace('/donation');
            // 사용자가 관리자유무 확인필요
        } else {
            const errorData = await response.json();
            alert("로그인 실패: " + errorData.message);
        }
    };
});

async function fetchData(url) {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
        if (response.ok) {
            const data = await response.json();
            console.log('데이터 로드 성공:', data);
        } else {
            const errorData = await response.json();
            throw new Error("서버 오류: " + errorData.message);
        }
    } catch (error) {
        console.error("에러 발생:", error);
    }
}

