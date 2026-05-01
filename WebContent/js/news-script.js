window.onload = function() {
    var studentData = {
        name: "홍길동",
        grade: 5,
        class: 4,
        class_number: 63,
        total_point: 3900,
        total_coupon: 3
    };

    document.getElementById('studentName').innerText = studentData.name;
    document.getElementById('studentClassInfo').innerText = 
        studentData.grade + "학년 " + studentData.class + "반 " + studentData.class_number + "번";
    document.getElementById('studentPoints').innerText = 
        "보유 포인트 : " + studentData.total_point.toLocaleString() + "P";

};