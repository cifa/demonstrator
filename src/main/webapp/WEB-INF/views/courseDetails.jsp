<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

<jsp:useBean class="java.text.SimpleDateFormat" id="datetimeFormat" scope="page">
	<% datetimeFormat.applyPattern("dd/MM/yyyy 'at' HH:mm:ss"); %>
</jsp:useBean>

<div class="row">
	<div class="row">
		<div class="five columns">
			<img src="<s:url value="/data/course/${course.id}/image" />" alt="course logo" />
		</div>
		<div class="seven columns">
			<h3>${course.title}</h3>
			<div class="six columns">
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">Course Starts:</div>
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">${dateFormat.format(course.startDate)}</div>
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">Duration:</div>
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">${course.length } weeks</div>
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">Price:</div>
				<div class="six columns" style="padding: 0; margin-bottom: 10px;">&pound;${course.price }</div>						
			</div>
			<div class="six columns">
				<div class="medium alert button split dropdown right">
					<c:choose>
						<c:when test="${registered }"><a href="#">Go To Lessons</a></c:when>
						<c:otherwise><a href="<s:url value="/user/course/${course.id}/registration" />">Register</a></c:otherwise>
					</c:choose>
					<span></span>
					<ul>
						<security:authorize access="hasRole('A')">
							<li class="divider"></li>
							<li><a href="<s:url value="/admin/course/${course.id}" />">Edit Course</a></li>
						</security:authorize>
						<li class="divider"></li>
						<li><a href="<s:url value="/course" />">Go Back</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row" style=" background-color: #eeeeee; margin-top: 15px; margin-bottom: 15px; padding: 10px; border-radius:10px;">
	<div class="row">
		<div class="twelve columns"><b>You might be also interested in:</b></div>		
	</div>
	<div class="row" style="padding-top: 10px">	
		<c:forEach var="rec" items="${recommendations}">		
			<div class="one columns"><img alt="course logon" src="<s:url value="/data/course/${rec.id}/image" />"/></div>	
			<div class="three columns" style="float: left;"><a href="<s:url value="/course/${rec.id}" />">${rec.title}</a></div>
		</c:forEach>
	</div>
</div>
<div class="row">
	<div class="six columns" style="text-align: justify; border-right: 1px gray solid;">${course.description}</div>
	<div class="six columns">
		<div style="font-size: 26px; margin-bottom: 13px; text-align: center;">Comments</div>
		<security:authorize access="isAuthenticated()">
			<form:form modelAttribute="comment" type="post" >
				<s:hasBindErrors name="comment">
					<t:insertAttribute name="formError" />
				</s:hasBindErrors>
				<div class="row" style="padding-bottom: 10px;">
				  	<div class="six columns">
				  		<label for="comment"><b>How do you rate the course?</b></label>
				  	</div>
				  	<div class="six columns">
				  		<c:forEach var="i" begin="1" end="10">
							<form:radiobutton path="rating" cssClass="star" value="${i }" />
						</c:forEach>
				  	</div>
				</div>
				<div style="text-align: center;">		
					<form:textarea id="comment" path="content" rows="4" cssErrorClass="error" />
					<input type="submit" value="Send Comment" class="radius button small" />
				</div>
			</form:form>
			<hr />
		</security:authorize>
		<div class="row">
		  	<div class="three columns offset-by-one" style="line-height: 40px;"><b>Sorted by:</b></div>
		  	<div class="eight columns right">
				<div class="medium button split dropdown twelve columns right">
					<a href="<s:url value="javascript:void(0)" />">${sortNames[sortIndex]}</a> 
					<span></span>
					<ul>
						<c:forEach var="name" items="${sortNames}" varStatus="loop" >
							<c:if test="${sortIndex != loop.index }">
								<li><a href="<s:url value="/course/${course.id}?sort=${loop.index}" />">${name}</a></li>
								<li class="divider"></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<hr />
		<c:forEach var="c" items="${comments}">
			<div class="row" style="padding: 15px 0">
				<div class="six columns">
					${datetimeFormat.format(c.posted)} by ${c.user.username}
				</div>
				<div class="six columns">
			  		<c:forEach var="i" begin="1" end="10">
						<input name="c${c.id }" type="radio" class="star" value="${i }" disabled="disabled"
							<c:if test="${c.rating == i}">checked="checked"</c:if>
						/>	
					</c:forEach>
			    </div>
			</div>
			${c.content}
			<hr />
		</c:forEach>
	</div>
</div>