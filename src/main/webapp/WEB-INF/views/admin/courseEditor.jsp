<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@taglib
	uri="http://www.springframework.org/tags" prefix="spring"%><div
	class="row">
	<form:form modelAttribute="course" id="frmCourse" method="post"
		enctype="multipart/form-data">
		<spring:hasBindErrors name="course">
			<div id="myModal" class="reveal-modal alert-box alert" style="position: fixed;">
				<a class="close-reveal-modal">×</a>
					<div style="font-size: x-large; padding-bottom: 15px;">Oops. There are some problems</div>
					<ul style="margin-left: 25px;">
						<form:errors path="*" element="li" delimiter="</li><li>" />
					</ul>
			</div>
			<script type='text/javascript'>//<![CDATA[
			$(window).load(function() { $("#myModal").reveal();})
			//]]>
			</script>
			</spring:hasBindErrors>
		<fieldset>
			<label for="title">Course Title</label>
			<form:input cssErrorClass="error" path="title" maxlength="50"
				id="title" />
			<div class="row">
				<div class="six columns">
					<label for="date">Start Date (DD/MM/YYYY)</label>
					<form:input cssErrorClass="error" path="startDate" maxlength="10"
						id="date" />
				</div>
				<div class="six columns">
					<label for="price">Course Price (&pound;)</label>
					<form:input cssErrorClass="error" path="price"
						id="price" type="number" min="0" max="10000" step="5" />
				</div>
			</div>
			<div class="row">
				<div class="six columns">
					<label for="img">Course Length (Weeks)</label>
					<form:input cssErrorClass="error" path="length"
						id="length" type="number" min="2" max="99" />
				</div>
				<div class="six columns">
					<label for="img">Course Image</label> <input type="file" id="img"
						name="imageFile" style="font-size: 14px; height: 32px;" /> <input
						type="submit" value="Save Course" class="radius button right" />
				</div>
			</div>
			<div class="row">
				<label for="desc">Description</label>
				<form:textarea cssErrorClass="error" path="description" id="desc"
					style="height: 400px; width: 100%;" />
			</div>
		</fieldset>
	</form:form>
	<script>//<![CDATA[
	$(document).ready(function() {
		$("#date").attr("readOnly", true)
		$("#date").datepicker({dateFormat: "dd/mm/yy", minDate: new Date()});
		whizzywig('all');
  	});
       //]]
    </script>
</div>