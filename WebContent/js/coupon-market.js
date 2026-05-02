/**
 * 쿠폰 구매 확인 및 요청 처리 함수
 * @param couponNo 쿠폰 번호
 * @param couponName 쿠폰 이름
 * @param CouponPrice 쿠폰 가격
 */

function buyCoupon(couponNo, couponName, couponPrice) {
	var confirmMessage = couponName + "을(를) " + couponPrice + "P에 구매하시겠습니까?";
	
	if (window.confirm(confirmMessage)) {
		var url = "controller?cmd=CouponBuyAction" 
			+ "&couponNo=" + couponNo 
			+ "&couponName=" + encodeURIComponent(couponName) 
			+ "&couponPrice=" + couponPrice;
		
		location.href = url;
	}
}