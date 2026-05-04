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
			let message = r.result ? '중복된 아이디 입니다.' : '사용가능한 아이디 입니다.';
			if (r.result) {
                idMsg.className = "message error";
            } else {
                idMsg.className = "message success";
            }
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

passwordCheck.onchange = function() {
    if (password.value !== passwordCheck.value) {
        pwdMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
        pwdMsg.className = "message error";
    } else {
        pwdMsg.innerHTML = "비밀번호가 일치합니다.";
        pwdMsg.className = "message success";
    }
 };
 form.addEventListener("submit", function(event) {
     let isValid = true;

     
 
     if (!isIdValid) {
         alert("아이디 중복 확인을 해주세요.");
         studentIdInput.focus();
         event.preventDefault(); 
         return; 
     }
     
     else if (password.value.length < 8) {
         alert("비밀번호는 8자리 이상이어야 합니다.");
         password.focus();
         event.preventDefault();
         return;
     } else if (password.value !== passwordCheck.value) {
         alert("비밀번호가 일치하지 않습니다.");
         passwordCheck.focus();
         event.preventDefault(); 
         return; 
     }
     else if (nameInput.value.trim() === "") {
         alert("이름을 입력해주세요.");
         nameInput.focus();
         event.preventDefault(); 
         return; 
     }

     else if (classInput.value.trim() === "") {
         alert("반을 입력해주세요.");
         classInput.focus();
         event.preventDefault(); 
         return; 
     }

     else if (numberInput.value.trim() === "") {
         alert("번호를 입력해주세요.");
         numberInput.focus();
         event.preventDefault(); 
         return; 
     }    
     alert("회원가입이 완료 되었습니다!");
 });
};
