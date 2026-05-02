window.onload = function(){
const today = new Date();
const form = document.querySelector("form");
const studentIdInput = document.querySelector("input[name='studentId']");
const password = document.querySelector("#password");
const passwordCheck = document.querySelector("#passwordCheck");
const nameInput = document.querySelector("input[name='name']");
const classInput = document.querySelector("input[name='className']");
const numberInput = document.querySelector("input[name='classNumber']");
const idMsg = document.querySelector("#id-check-msg");
const pwdMsg = document.querySelector("#pwd-check-msg");

let isIdValid = false;

document.getElementById('current-year').innerText = today.getFullYear();

let xhr= new XMLHttpRequest();
let callbackMethod = function(){
	if(xhr.readyState ==4){
		if(xhr.status ==200 || xhr.status ==300){
			let r=JSON.parse(xhr.responseText);		
			isIdValid = !r.result;
			let message='사용가능';
			if(r.result)
				message='사용 불가능';
			document.querySelector("#id-check-msg").innerHTML=message;		
		}
	}

};
xhr.onreadystatechange = callbackMethod;

let inputs= document.querySelectorAll("input[name='studentId']");
let idEvent = function(){
	xhr.open("get","controller?cmd=idCheck&studentId="+this.value, true);
	xhr.send(null);
};
inputs[0].onchange = idEvent;

//비밀번호 확인란에서 커서가 벗어날 때(onchange) 실행
passwordCheck.onchange = function() {
    if (password.value !== passwordCheck.value) {
        pwdMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
        pwdMsg.style.color = "red";
        passwordCheck.style.border = "2px solid red"; // 시각적 강조
    } else {
        pwdMsg.innerHTML = "비밀번호가 일치합니다.";
        pwdMsg.style.color = "green";
        passwordCheck.style.border = "2px solid green";
    }
 };
 form.addEventListener("submit", function(event) {
     let checkStep = 1;
     let isValid = true;

     while (checkStep <= 5) { // 5단계까지 검사
         switch (checkStep) {
             case 1:
                 if (!isIdValid) {
                     alert("아이디 중복 확인을 해주세요.");
                     studentIdInput.focus();
                     isValid = false;
                 }
                 break;
             case 2:
                 if (password.value === "" || password.value !== passwordCheck.value) {
                     alert("비밀번호를 확인해주세요.");
                     passwordCheck.focus();
                     isValid = false;
                 }
                 break;
             case 3:
                 if (nameInput.value.trim() === "") {
                     alert("이름을 입력해주세요.");
                     nameInput.focus();
                     isValid = false;
                 }
                 break;
             case 4:
                 if (classInput.value.trim() === "") {
                     alert("반을 입력해주세요.");
                     classInput.focus();
                     isValid = false;
                 }
                 break;
             case 5:
                 if (numberInput.value.trim() === "") {
                     alert("번호를 입력해주세요.");
                     numberInput.focus();
                     isValid = false;
                 }
                 break;
         }
         if (!isValid) {
             event.preventDefault(); // 유효하지 않으면 전송 차단
             return; // 검사 종료
         }
         checkStep++;
     }
     if (isValid) {
     alert("회원가입이 완료 되었습니다!");
     }
 });
};
