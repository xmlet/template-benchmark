@import java.util.List
@import com.mitchellbosecke.benchmark.model.Stock
@import gg.jte.support.ForSupport

@param List<Stock> items

<!DOCTYPE html>
<html>
<head>
<title>Stock Prices</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<meta http-equiv="Content-Style-Type" content="text/css" >
<meta http-equiv="Content-Script-Type" content="text/javascript" >
<link rel="shortcut icon" href="/images/favicon.ico" >
<link rel="stylesheet" type="text/css" href="/css/style.css" media="all" >
<script type="text/javascript" src="/js/util.js"></script>
<style type="text/css">
/*<![CDATA[*/
body {
	color: #333333;
	line-height: 150%;
}

thead {
	font-weight: bold;
	background-color: #CCCCCC;
}

.odd {
	background-color: #FFCCCC;
}

.even {
	background-color: #CCCCFF;
}

.minus {
	color: #FF0000;
}

/*]]>*/
</style>

</head>

<body>

	<h1>Stock Prices</h1>

	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>symbol</th>
				<th>name</th>
				<th>price</th>
				<th>change</th>
				<th>ratio</th>
			</tr>
		</thead>
		<tbody>
		    @for(ForSupport<Stock> itemLoop : ForSupport.of(items))
		    !{var item = itemLoop.get();}
			<tr class="${(itemLoop.getIndex() + 1) % 2 == 0 ? "even" : "odd"}">
				<td>${itemLoop.getIndex() + 1}</td>
				<td><a href="/stocks/${item.getSymbol()}">${item.getSymbol()}</a></td>
				<td><a href="${item.getUrl()}">${item.getName()}</a></td>
				<td><strong>${item.getPrice()}</strong></td>
				@if(item.getChange() < 0.0)
				<td class="minus">${item.getChange()}</td>
                <td class="minus">${item.getRatio()}</td>
                @else
                <td>${item.getChange()}</td>
                <td>${item.getRatio()}</td>
				@endif
			</tr>
		    @endfor
		</tbody>
	</table>

</body>
</html>
