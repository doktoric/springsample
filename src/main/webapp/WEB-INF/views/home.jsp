<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<c:url value="/resources" var="resource" />
<html>

<head>
<meta charset="utf-8">
<title>random project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- Le styles -->
<link href="${resource}/css/bootstrap-responsive.css" rel="stylesheet">
<link href="<spring:theme code="css"/>" rel="stylesheet">
<link href="${resource}/css/docs.css" rel="stylesheet">
<link href="${resource}/css/prettify.css" rel="stylesheet">
<link href="${resource}/css/jquery.pnotify.default.css" rel="stylesheet">
</head>


<body data-spy="scroll" data-target=".bs-docs-sidebar"
	data-twttr-rendered="true">
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Reverse Ajax</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> Themes <b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href='?theme=default'>default</a></li>
								<li><a href='?theme=amelia'>amelia</a></li>
								<li><a href='?theme=cerulean'>cerulean</a></li>
								<li><a href='?theme=cyborg'>cyborg</a></li>
								<li><a href='?theme=journal'>journal</a></li>
								<li><a href='?theme=readable'>readable</a></li>
								<li><a href='?theme=simplex'>simplex</a></li>
								<li><a href='?theme=slate'>slate</a></li>
								<li><a href='?theme=spacelab'>spacelab</a></li>
								<li><a href='?theme=spruce'>spruce</a></li>
								<li><a href='?theme=superhero'>superhero</a></li>
								<li><a href='?theme=united'>united</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 	<header class="jumbotron subhead" id="overview"> -->
	<!-- 		<div class="container-fluid"> -->
	<!-- 			<div class="row-fluid"> -->

	<!-- 				<div class="span5 offset2"> -->
	<!-- 					<h1>Reverse Ajax</h1> -->
	<!-- 				</div> -->
	<!-- 				<div class="span5"></div> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</header> -->
	<div class="container" style="margin-top: 40px !important;">
		<div class="row" style="padding-top: 20px !important;">

			<div class="hero-unit">
				<form id="messageForm" class="form-horizontal" action="/">
					<div class="control-group">
						<label class="control-label" for="inputEmail">Nickname: </label>
						<div class="controls">
							<input id="nick" type="text" placeholder="Nickname">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Message: </label>
						<div class="controls">
							<input id="messageInput" type="text" placeholder="Message">
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn">Send</button>
						</div>
					</div>

				</form>
				<form action='<c:url value="report/pdf"/>' method="get"
					style="margin-bottom: 0px !important;">
					<button type="submit" class="btn btn-large">
						<i class="icon-file"></i>Jasper Report
					</button>
				</form>
			</div>
		</div>
		<div class="row" id="tempmessagesDiv">
		</div>
		<div class="row" id="messagesDiv">
			<c:forEach var="message" items="${messages}">
				<div class="messageDivPiece">
					<div class="alert alert-block alert-info fade in" id="${message.id}">
						<button type="button" class="close" data-dismiss="alert" name="removeMessageButton"  id="${message.id}">x</button>
						<h5 class="alert-heading">${message.messageFromPerson} : ${message.messageText}  -> ${message.messageDate}</h5>
					</div>
				</div>
			</c:forEach>
		</div>
		<div id="loadmoreajaxloader" style="display:none;">
			<center>
				<img src="resources/img/ajax-loader.gif" />
			</center>
		</div>
	</div>

	<!-- SCRIPTS -->

	<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script src="<c:url value=" dwr/engine.js "/>"></script>
	<script src="<c:url value=" dwr/util.js "/>"></script>
	<script src="<c:url value=" dwr/interface/DwrService.js "/>"></script>
	<script type="text/javascript">
		var stack_bar_top = {
			"dir1" : "down",
			"dir2" : "right",
			"push" : "top",
			"spacing1" : 0,
			"spacing2" : 0
		};
		var opts = {
			title : "",
			text : "",
			addclass : "stack-bar-top",
			cornerclass : "",
			width : "100%",
			stack : stack_bar_top,
			type : "error",
			delay : 1500
		};

		$(function() {

			dwr.engine.setActiveReverseAjax(true);

			$("#messageForm").submit(
					function() {
						if ($("#nick").val() && $("#messageInput").val()) {
							DwrService.sendMessage($("#nick").val(), $(
									"#messageInput").val());
							$("#messageInput").val("");
						} else {
							opts.title = "Invalid input";
							opts.text = "Please add new valid input";
							opts.type = "error";
							$.pnotify(opts);
							$("#messageInput").val("");
						}
						return false;
					});

			$('button[name="removeMessageButton"]').live('click', (function() {
				DwrService.deleteMessage($(this).attr('id'));
			}));
		});

		function showMessage(from, message, date, id) {

			var actualDiv = messageCreator(from, message, date, id);
			opts.title = "New message from: " + from;
			opts.text = "" + message;
			opts.type = "info";
			$.pnotify(opts);
			$("#tempmessagesDiv").prepend(actualDiv);
			$("#" + id).hide().fadeIn("slow");
		};
		
		function messageCreator(from, message, date, id){
			var actualDiv = "<div class=\"messageDivPiece\"><div class=\"alert alert-block alert-info fade in\"  id=\""+id+"\"><button type=\"button\" name=\"removeMessageButton\" id=\""+id+"\" class=\"close\"  data-dismiss=\"alert\">x</button><h5 class=\"alert-heading\">"
					+ from + ":" + message + " -> "+date
					+ "</h5>";
			return actualDiv;
		};

		function removeMessage(from, message, date, id) {
			$("#" + id).fadeOut(300, function() {
				$(this).remove();
			});
			opts.title = "Deleted by: " + from;
			opts.text = "" + message;
			opts.type = "info";
			$.pnotify(opts);
		};
		
		
		$(window).scroll(function()
		{
			if( ($(document).scrollTop() / ($(document).height() - $(window).height())) * 100 > 80)
			{
				$('div#loadmoreajaxloader').show();
				var messageVisible=$("#messagesDiv>.messageDivPiece").size();
				
				$.ajax({
					type: "POST",
					data: ({
			        
			        }),
					url: location.href + "messages/more/"+messageVisible,
					success: function(data)
					{
						if(data)
					    {
							
							for (var i = 0; i < data.length; i++) {
								if($("#messagesDiv").not(":has(#"+data[i]["objectId"]+")")){
									$("#messagesDiv").append(messageCreator(data[i]["from"],data[i]["message"],data[i]["date"],data[i]["objectId"]));
								}
								
								if($("#"+data[i]["objectId"]+"").has("#tempmessagesDiv")){
									$("#tempmessagesDiv>.messageDivPiece>"+"#"+data[i]["objectId"]+"").remove();
								}
							}
					    	$('div#loadmoreajaxloader').hide();
					    }else {
					        $('div#loadmoreajaxloader').html('<center>No more posts to show.</center>');
					    }
			       },
				   error: function (xhr, ajaxOptions, thrownError) {
					
					   $('div#loadmoreajaxloader').html(thrownError+ajaxOptions+xhr);
			       }
			   });
			}
		});
		
		
	</script>



	<script src="${resource}/js/bootstrap-transition.js"></script>
	<script src="${resource}/js/bootstrap-alert.js"></script>
	<script src="${resource}/js/bootstrap-modal.js"></script>
	<script src="${resource}/js/bootstrap-dropdown.js"></script>
	<script src="${resource}/js/bootstrap-scrollspy.js"></script>
	<script src="${resource}/js/bootstrap-tab.js"></script>
	<script src="${resource}/js/bootstrap-tooltip.js"></script>
	<script src="${resource}/js/bootstrap-popover.js"></script>
	<script src="${resource}/js/bootstrap-button.js"></script>
	<script src="${resource}/js/bootstrap-collapse.js"></script>
	<script src="${resource}/js/bootstrap-carousel.js"></script>
	<script src="${resource}/js/bootstrap-typeahead.js"></script>
	<script src="${resource}/js/jquery.pnotify.min.js"></script>

<!-- 	END SCRIPTS -->






</body>


</html>
