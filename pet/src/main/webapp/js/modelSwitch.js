		function iframeAutoHeight() {
			var iframe = document.getElementById("midFraim");
			if (navigator.userAgent.indexOf("MSIE") > 0
					|| navigator.userAgent.indexOf("rv:11") > 0
					|| navigator.userAgent.indexOf("Firefox") > 0) {
				iframe.height = iframe.contentWindow.document.body.scrollHeight;
			} else {
				iframe.height = iframe.contentWindow.document.documentElement.scrollHeight;
			}
		}
		$("#picture").click(function(){
			$("#templatemo_left_column").hide();
			$("#templatemo_right_column").hide();
			$("#templatemo_right_column").hide();
			$("#midFraim").css("display","block");
			iframeAutoHeight();
		})