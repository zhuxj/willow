
// nav menu
var timeout	= 500;
var closetimer	= 0;
var ddmenuitem	= 0;

function comment_check() {
if ( document.form1.name.value == '' ) {
window.alert('Please input your name^_^');
document.form1.name.focus();
return false;}

if ( document.form1.email.value.length> 0 &&!document.form1.email.value.indexOf('@')==-1|document.form1.email.value.indexOf('.')==-1 ) {
window.alert('Please input your right email address，for example:webmaster@hitux.com');
document.form1.email.focus();
return false;}

if(document.form1.qq.value.search(/^([0-9]*)([.]?)([0-9]*)$/)   ==   -1)   
      {   
  window.alert("Please innput the number^_^");   
document.form1.qq.focus();
return false;}

if ( document.form1.content.value == '' ) {
window.alert('Please input your content^_^');
document.form1.content.focus();
return false;}

if ( document.form1.verycode.value == '' ) {
window.alert('Please input code^_^');
document.form1.verycode.focus();
return false;}

return true;}




function order_check() {
if ( document.form1.ordercount.value == '' ) {
window.alert('Please input your order number^_^');
document.form1.ordercount.focus();
return false;}

if(document.form1.ordercount.value.search(/^([0-9]*)([.]?)([0-9]*)$/)   ==   -1)   
      {   
  window.alert("Please input the number^_^");   
document.form1.ordercount.focus();
return false;}


if ( document.form1.name.value == '' ) {
window.alert('Please input your name^_^');
document.form1.name.focus();
return false;}

if ( document.form1.address.value == '' ) {
window.alert('Please input your address^_^');
document.form1.address.focus();
return false;}

if ( document.form1.tel.value == '' ) {
window.alert('Please input your tel number^_^');
document.form1.tel.focus();
return false;}

if ( document.form1.email.value.length> 0 &&!document.form1.email.value.indexOf('@')==-1|document.form1.email.value.indexOf('.')==-1 ) {
window.alert('Please input your right email address，for example:webmaster@hitux.com');
document.form1.email.focus();
return false;}

if(document.form1.qq.value.search(/^([0-9]*)([.]?)([0-9]*)$/)   ==   -1)   
      {   
  window.alert("Please input the number");   
document.form1.qq.focus();
return false;}


if ( document.form1.verycode.value == '' ) {
window.alert('Please input the number^_^');
document.form1.verycode.focus();
return false;}

return true;}


/* Fucus*/
$(function() {
	var sWidth = $("#Focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#Focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
	$("#Focus").append(btn);
	$("#Focus .btnBg").css("opacity",0.3);

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#Focus .btn span").css("opacity",0.3).mouseenter(function() {
		index = $("#Focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");

	//上一页、下一页按钮透明度处理
	$("#Focus .preNext").css("opacity",0.1).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.3"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.1"},300);
	});

	//上一页按钮
	$("#Focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#Focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#Focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#Focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#Focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#Focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#Focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}
});