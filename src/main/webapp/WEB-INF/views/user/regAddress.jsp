<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

<div class="row">
	<h3>Course Registration - Step 1</h3>
	<form:form modelAttribute="registration" type="post" >
		<s:hasBindErrors name="registration">
			<t:insertAttribute name="formError" />
		</s:hasBindErrors>
		<fieldset>
			<legend>Billing Address</legend>
			<div class="row">
				<div class="twelve columns">
				<label for="street">House Name/Number, Street</label>
				<form:input path="street" id="street" cssErrorClass="error" />
				</div>
				<div class="six columns">
					<label for="town">Town</label>
					<form:input path="town" id="town" cssErrorClass="error" />
				</div>
				<div class="six columns">
					<label for="postcode">Postcode</label>
					<form:input path="postcode" id="postcode" cssErrorClass="error" />
				</div>
				<div class="twelve columns" style="margin-bottom: 15px;">
					<input type="submit" value="Submit" class="radius button" />
					<a href='<s:url value="/user/registration/${registration.uuid }/finish" />' class="radius button">Cancel</a>
				</div>
			</div>
		</fieldset>		
	</form:form>
</div>