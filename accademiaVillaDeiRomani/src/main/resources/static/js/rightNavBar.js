$(document).ready(function() {
	 
	$("#it").click(function () {
		window.location.replace(getUrlLangParams()+'lang=it');
	});
	$("#en").click(function () {
		window.location.replace(getUrlLangParams()+'lang=en');

	});
	$("#zh").click(function () {
		window.location.replace(getUrlLangParams()+'lang=zh');

	});
});
function getUrlLangParams() {
	var completeUrl=window.location.href;
	var n=completeUrl.indexOf("?");
	if (n==-1) {//se non ci sono parametri
		return completeUrl+"?";
	} else {//altrimenti tolgo lang se c'è
		var n1 =completeUrl.indexOf("&lang");
		var n2=completeUrl.indexOf("?lang");
		if (n1==-1 && n2==-1) {//se non c'è
			return completeUrl+"&";
		} else {// se c'è
			if (n1>-1) {
				return completeUrl.substring(0,completeUrl.indexOf("&lang"))+"&";
			} else {
				return completeUrl.substring(0,completeUrl.indexOf("?lang"))+"?";
			}
			
		}
	}

}