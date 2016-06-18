<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh_CN">
<head>
<meta http-equiv="Cache-Control" content="max-age=7200" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TaiK-台卡网络</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="<%=request.getContextPath()%>/assets/css/bootstrap.css"
	rel="stylesheet" />
<link href="<%=request.getContextPath()%>/assets/css/font-awesome.css"
	rel="stylesheet" />
<link href="<%=request.getContextPath()%>/assets/css/basic.css"
	rel="stylesheet" />
<link href="<%=request.getContextPath()%>/assets/css/custom.css"
	rel="stylesheet" />

</head>
<body>
	<div id="page-wrapper"
		style="margin: 0 0 0 0px; background-color: #E2E2E2;">
		<div id="page-inner">
			<div id="addorder" style="display: none;" class="row">
				<div style="width: 100%;" class="col-md-6 col-sm-6 col-xs-12">
					<div class="panel panel-info">
						<div class="panel-heading">填写发布信息</div>
						<div class="panel-body">
							<form id="orderform" role="form" action="addmsg" method="post">
								<div class="form-group col-md-6">
									<label>内容:</label> <input id="mgs" name="msg"
										class="form-control" type="text">
								</div>
								<div class="form-group col-md-6">
									<label>备 注:</label> <input id="msgDesc" name="msgDesc"
										class="form-control" type="text">
								</div>
								<input type="submit"
									style="float: right; background-color: #A600FF;"
									class="btn btn-info">上架</input>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div style="width: 100%;" class="col-md-6 col-sm-6 col-xs-12">
					<div class="panel panel-info">
						<a id="addorderbtn" href="javascript:showHideAddform();"
							class="btn btn-info btn-block">发布信息</a>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>编号</th>
											<th>内容</th>
											<th>备注</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="orderrs">
										<c:if test="${list !=null}">
											<c:forEach items="${list}" var="mglist">
												<tr>
													<td>${mglist.no}</td>
													<td>${mglist.msg}</td>
													<td>${mglist.msgDesc}</td>
													<td>${mglist.ctstr}</td>
													<td>
														<button class="btn btn-inverse"
															onclick="location='update/${mglist.id}'">
															<i class="glyphicon glyphicon-plus"></i>下架
														</button>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer-sec">Copyright &copy; 2016.台卡网络科技有限公司 All rights
		reserved.</div>
	<div id="dm-notif"></div>
</body>
<script src="<%=request.getContextPath()%>/assets/js/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/bootstrap.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/js/jquery.metisMenu.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/custom.js"></script>
<script type="text/javascript">
	var showflag = false;
	$(document).ready(function(){
		if('false'==<%=request.getAttribute('flag')%>){
			dm_notification('系统异常,请联系管理员。', 'green', 2000)
		}
	});
	function showHideAddform() {
		if (showflag) {
			showflag = false;
			$("#addorder").hide();
			$("#addorderbtn").html('添加订单');
		} else {
			showflag = true;
			$("#addorder").show();
			$("#addorderbtn").html('隐藏');
		}
	}
	function dm_notification(text, color, time, icon) {
		var icon_span = "", html_element, time = time, $cont = $('#dm-notif');
		if (time) {
			time = time;
		} else {
			time = 2000;
		}
		if (icon) {
			icon_span = "<span class='" + icon + "'></span> ";
		}
		html_element = $(
				'<div class="dm-notification dm-notify-' + color + '">'
						+ icon_span + text + '</div>').fadeIn('fast');
		$cont.append(html_element);
		html_element.on('click', function() {
			dm_notification_close($(this));
		});
		setTimeout(function() {
			dm_notification_close($cont.children('.dm-notification').first());
		}, time);
	}
	function dm_notification_close(elem) {
		if (typeof elem !== "undefined") {
			elem.fadeOut('fast', function() {
				$(this).remove();
			});
		} else {
			$('.alert').fadeOut('fast', function() {
				$(this).remove();
			});
		}
	}
</script>
</html>