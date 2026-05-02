const today = new Date();
document.getElementById('current-year').innerText = today.getFullYear();

let xhr= new XMLHttpRequest();
let callbackMethod = function(){
	if(xhr.readyState ==4){
		if(xhr.status ==200 || xhr.status ==300){
			let r=JSON.parse(xhr.responseText);
			let message='사용가능';
			if(r.result)
				message='사용 불가능';
			document.querySelector("#id-check-msg").innerHTML=message;		
		}
	}

};
xhr.onreadystatechange = callbackMethod;
window.onload = function(){
let inputs= document.querySelectorAll("input[name='studentId']");
let idEvent = function(){
	xhr.open("get","controller?cmd=idCheck&studentId="+this.value, true);
	xhr.send(null);
};
inputs[0].onchange = idEvent;
};