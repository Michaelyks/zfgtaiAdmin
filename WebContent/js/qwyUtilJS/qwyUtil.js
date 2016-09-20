/*!
 * JS 工具方法;
 * createTime: 2014-04-29 01:14:22
 * updateTime: 2014-04-29 01:19:27
 * author: qwy
 * version: 1.0;
 *
 */

/**
 * 去除左右两边的空格<br>
 * 判断该值为否为"";
 * 
 * @param val
 *            object
 * @returns <b>true：</b>等于"";<br>
 *          <b>false：</b>不等于"";
 */
function isEmpty(val) {
	var obj = val.replace(/ /g, "");
	return obj.length > 0 && obj != "" ? false : true;
}

/**
 * 是否是微信浏览器
 */
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
   // alert(ua);
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

/**
 * 是否为0到9的纯数字;
 * 
 * @param val
 *            object
 * @returns <b>true：</b>纯数字;<br>
 *          <b>false：</b>不是纯数字;
 */
function isOnlyNumber(val) {
	var test = new RegExp(/^[0-9]+$/);
	return test.test(val);
}

/**
 * 该值是否为价格的格式; 例如:1、21.12、12.0、0.1222；
 * 
 * @param val
 *            object
 * @returns <b>true：</b>为价格的格式;<br>
 *          <b>false：</b>不是价格的格式;
 */
function isPrice(val) {
	var test = new RegExp(/^[0-9]+(\.[0-9]+)?$/);
	return test.test(val);
}

/**
 * 该值是否为价格的格式;允许负数 例如:-1、-23.12、1、21.12、12.0、0.1222；
 * 
 * @param val
 *            object
 * @returns <b>true：</b>为价格的格式;<br>
 *          <b>false：</b>不是价格的格式;
 */
function isPrice2(val) {
	var test = new RegExp(/^(-)?[0-9]+(\.[0-9]+)?$/);
	return test.test(val);
}

/**是否为手机格式;11位;
 * @param val
 * @returns
 */
function isPhoneNumber(val){
	var test = new RegExp(/^[1][345789][0-9]{9}$/);
	return test.test(val);
}

/**是否为密码格式;6~16位;下划线字母或数字;
 * @param val
 * @returns
 */
function isPassword(val){
	var test = new RegExp(/^\w{6,16}$/);
	return test.test(val);
}


function addHistoryToLocal(moneyId, title, nickNames, earnings, tzqx){
	/*var historyList={};
	historyList.title = title;
	historyList.nickNames = nickNames;
	historyList.earnings = earnings;
	historyList.tzqx = tzqx;*/
	var str ="{";
	str +="\"title\":\""+title+"\",";
	str +="\"nickNames\":\""+nickNames+"\",";
	str +="\"earnings\":\""+earnings+"\",";
	str +="\"tzqx\":\""+tzqx+"\"";
	str +="}";
	//JSON.stringify(historyList);
	//alert(1);
	//alert(str);
	//localStorage.removeItem(moneyId);
	//localStorage.getItem(localStorage.key(i));
	//var storageLength = localStorage.length;
	//for(var i=0;i<storageLength;i++){
	//	localStorage.setItem(localStorage.key(i),localStorage.getItem(localStorage.key(i)));
	//}
	localStorage.setItem(moneyId, str);
}

/**返回格式为 yyyy-MM-dd
 * @param time 时间的time;
 * @returns {String} yyyy-MM-dd
 */
function format(time){ 
	var myDate = new Date(time);
	var year = myDate.getFullYear();//获取完整的年份(4位,1970-????)
	var money = myDate.getMonth()+1+100;  //获取当前月份(0-11,0代表1月)
	var date = myDate.getDate()+100;        //获取当前日(1-31)
	money = money.toString().substring(1);
	date = date.toString().substring(1);
	return year+"-"+money+"-"+date; 
} 

/**返回格式为 yyyy-MM-dd
 * @param time 时间的time;
 * @returns {String} yyyy-MM-dd
 */
function formatMMdd(time){ 
	var myDate = new Date(time);
	var year = myDate.getFullYear();//获取完整的年份(4位,1970-????)
	var money = myDate.getMonth()+1+100;  //获取当前月份(0-11,0代表1月)
	var date = myDate.getDate()+100;        //获取当前日(1-31)
	money = money.toString().substring(1);
	date = date.toString().substring(1);
	return money+"-"+date; 
}

function fmoney(s)   
{   var n = 0;
   n = n >= 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse();//,   
  // r = s.split(".")[1];   
  var t = "";   
   for(var i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("");// + "." + r;   
}


/**比较两个时间的相差天数;不包含开始时间, 例如 18号到20号,相差2天;
 * @param sDate1 开始时间;
 * @param sDate2 结束时间;
 */
function  DateDiff(sDate1, sDate2){    //sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays;  
    aDate  =  sDate1.split("-");  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2006格式  
    aDate  =  sDate2.split("-");  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
    return  iDays;  
}