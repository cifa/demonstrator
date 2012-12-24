<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

			<div id="errorMsg" class="reveal-modal alert-box alert" style="position: fixed;">
				<a class="close-reveal-modal">×</a>
				<div style="font-size: x-large; padding-bottom: 15px;">Oops. There are some problems</div>
				<ul style="margin-left: 25px;"><form:errors path="*" element="li" delimiter="</li><li>"/></ul>
			</div>
			<script type="text/javascript">//<![CDATA[
				$(window).load(function() { $("#errorMsg").reveal();});
			//]]>
			</script>