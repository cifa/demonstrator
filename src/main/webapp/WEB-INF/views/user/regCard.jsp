<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

<div class="row">
	<h3>Course Registration - Step 2</h3>
	<form:form modelAttribute="registration" type="post" >
		<s:hasBindErrors name="registration">
			<t:insertAttribute name="formError" />
		</s:hasBindErrors>
		<fieldset>
			<legend>Payment Details</legend>
			<div class="row">
				<div class="twelve columns">
					<label for="name">Card Holder</label>
					<form:input path="name" id="name" cssErrorClass="error" autocomplete="off" />
				</div>
				<div class="twelve columns">
					<label for="card">Credit/Debit Card Number</label>
					<form:input path="cardNumber" id="card" cssErrorClass="error" maxlength="16"  autocomplete="off" />
				</div>
				<div class="nine columns">
					<label for="month">Exparation Date</label>
					<form:select path="month" id="month" cssErrorClass="error">
						<c:forEach var="mth" items="${registration.months }" varStatus="st">
							<form:option value="${st.index }">${mth }</form:option>
						</c:forEach>
					</form:select>
					<form:select path="year" id="year" cssErrorClass="error">
						<c:forEach begin="0" end="10" varStatus="st">
							<form:option value="${registration.currentYear + st.index}">${registration.currentYear + st.index}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<div class="three columns">
					<label for="code">Card Code</label>
					<form:input path="code" id="code" cssErrorClass="error" maxlength="4" autocomplete="off" />
				</div>
				<div class="twelve columns" style="margin-bottom: 15px;">
					<input type="submit" value="Submit" class="radius button" />
					<a href='<s:url value="/user/registration/${registration.uuid }/stepback/1" />' class="radius button">Step Back</a>
					<a href='<s:url value="/user/registration/${registration.uuid }/finish" />' class="radius button">Cancel</a>
				</div>
			</div>
		</fieldset>		
	</form:form>
</div>