<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>商品详情页</title>
	<link rel="stylesheet" href="css/base.css">
	<link rel="stylesheet" href="css/shong_info.css">
</head>

<body>
	<div class="header">
		<div class="jd_layout header-content">
			<div class="left-out" onclick="black()">&lt;</div>
			<div class="title">商品详情</div>
		</div>
	</div>

	<!-- 本体内容设置 -->
	<div class="jd_layout">
		<div class="main">
			<!-- 轮播图 -->
			<img src="img/cp5.jpg" style="width: 100%;" alt="">
			<div class="money">
				￥<em>${rel.money}</em>
			</div>
			<div class="content">${rel.name}</div>
		</div>

	</div>
	<!--底部菜单 -->
	<div class="footer">
		<div class="jd_layout">
			<a href="car.html" class="car-go"></a>
			<span class="addcar" id="addcar"> 加入购物车</span>
			<span class="buy">立即购买</span>
		</div>
	</div>

	<!-- 做一个隐藏的菜单  取消默认提交-->
	<form onsubmit="return false;" id="carform" style="display: none;">
		<!-- 编号不用给予，交给后端处理 -->
		<input type="text" name="shong_id" value="${rel.id}">
		<input type="text" name="dianpu_id" value="${rel.dianpu_id}">
		<input type="text" name="money" value="${rel.money}">
	</form>
	
	${user}


	<script src="js/jquery.js"></script>
	<script>
		function black() {
			window.history.back(-1);
		}
		$("#addcar").click(function () {
			//得到需要提交的数据
			var data = $("#carform").serialize() + "&method=insert";
			$.ajax({
				url: "ShoppingCart",
				type: "post",
				data: data,
				success: function (rel) {
					alert(rel);
				}
			});
		});


	</script>
</body>

</html>