<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
	<div class="row">
		<div id="featuredContent">
			<div>
				<div id="tweet0" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter1.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet1" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter2.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet2" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter3.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet3" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter4.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet4" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter5.png" />"
						alt="twitter" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<hr style="padding: 0; margin: 0 0 10px 0;" />
	</div>
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
			self.setInterval(function() {
				updateTweets()
			}, 50000);
		});

		function updateTweets() {
			$.getJSON('tweet', function(tweets) {
				for (i = 0; i < tweets.length; i++) {
					$('#tweet' + i).children().each(function(index) {
						switch (index) {
						case 0:
							$(this).html(tweets[i].content);
							break;
						case 1:
							$(this).html(tweets[i].author);
							break;
						}
					});
				}
			});
		}

		updateTweets();
	//]]>
	</script>
