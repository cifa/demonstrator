<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%><div
	class="row">
	<form:form modelAttribute="course" id="frmCourse" method="post"
		enctype="multipart/form-data">
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
					<form:input cssErrorClass="error" path="price" maxlength="6"
						id="price" />
				</div>
			</div>
			<div class="row">
				<div class="six columns">
					<label for="img">Course Length (Weeks)</label>
					<form:input cssErrorClass="error" path="length" maxlength="2"
						id="length" />
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
</div>