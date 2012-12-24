<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<h3>Course Registration - Step 3</h3>
	<h4>Confirm Payment</h4>
	<div class="row">
		<div class="four columns">
			<b>Billing Address:</b>
		</div>
		<div class="eight columns">
			<div style="padding-bottom: 10px;">${registration.user.firstName} ${registration.user.surname}</div>
			<div style="padding-bottom: 10px;">${registration.street}</div>
			<div style="padding-bottom: 10px;">${registration.town}</div>
			<div style="padding-bottom: 10px;">${registration.postcode}</div>
			<p><a href='<s:url value="/user/registration/${registration.uuid }/stepback/2" />' class="radius button small">Change Billing Address</a></p>
		</div>
		<div class="four columns">
			<b>Payment Details:</b>
		</div>
		<div class="eight columns">
			<p><b>Cardholder Name:</b> <br />${registration.name}</p>
			<p><b>Card Number Ending:</b> <br />${registration.secureCardNo}</p>
			<p><b>Expiry Date:</b> <br />${registration.month + 1}/${registration.year}</p>
			<p><a href='<s:url value="/user/registration/${registration.uuid }/stepback/1" />' class="radius button small">Change Payment Details</a></p>
		</div>
		<div class="twelve columns" style="margin-bottom: 15px;">
			<p><b>Please confirm your registration.</b> Your card will be debited immediately.</p>
			<form:form modelAttribute="registration" type="post" >
				<div>
					<input type="submit" class="radius button" value="Confirm" />
					<a href='<s:url value="/user/registration/${registration.uuid }/finish" />' class="radius button">Cancel</a>
				</div>
			</form:form>
		</div>
	</div>
</div>