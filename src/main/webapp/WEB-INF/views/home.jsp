<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="row">
		<div id="featuredContent">
			<c:forEach var="i" begin="0" end="4">
				<div>
					<div id="tweet${i}" class="seven columns offset-by-one">
						<p style="font-size: large; color: #2ba6cb;"></p><p class="right"></p>
					</div>
					<div class="four columns right">
						<img src="<s:url value="/resources/images/twitter${i+1}.png" />" alt="twitter" />
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="row"><hr style="padding: 0; margin: 0 0 10px 0;" /></div>
	<div class="row">
		<div class="twelwe columns">
			<img alt="popularity chart" src="<s:url value="/data/popchart" />" />
		</div>
	</div>

	<script type='text/javascript'>//<![CDATA[
		$(window).load(function() {
			$('#featuredContent').orbit({
				fluid : '16x3',
				advanceSpeed : 10000
			});
			self.setInterval(function() {updateTweets();}, 50000);
		});

		function updateTweets() {
			$.getJSON('data/tweet', function(tweets) {
				for (i=0; i < tweets.length; i++) {
					$('#tweet' + i).children().each(function(index) {
						switch (index) {
							case 0: $(this).html(tweets[i].content); break;
							case 1: $(this).html(tweets[i].author); break;
						}
					});
				}
			});
		}

		updateTweets();
	//]]>
	</script>
