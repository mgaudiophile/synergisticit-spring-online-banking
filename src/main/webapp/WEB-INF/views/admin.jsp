<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Admin</title>

<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
.error {
	color: red;
	font-style: italic;
	font-weight: bold
}
</style>
<script>
	$(document).ready(function() {
		$('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
			localStorage.setItem('activeTab', $(e.target).attr('href'));
		});
		var activeTab = localStorage.getItem('activeTab');
		if (activeTab) {
			$('#myTab a[href="' + activeTab + '"]').tab('show');
		}
	});
</script>
<body>

	<div class="container">
		<br>
		<h3>Welcome ${pageContext.request.userPrincipal.name}</h3>
		<h5>
			<a href="${pageContext.request.contextPath}/home" class="btn btn btn-primary">Home</a>&nbsp;&nbsp;<a
				href="login?logout" class="btn btn-primary">Logout</a>
		</h5>
		<br>
		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist" id="myTab">
			<li class="nav-item"><a class="nav-link active"
				data-toggle="tab" href="#withdraw">Withdraw</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#deposit">Deposit</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#transfer">Transfer</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#accounts">Accounts</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#customers">Customers</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#users">Users</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#branches">Branches</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#history">History</a></li>
		</ul>

		<!-- Tab panes -->
		<div class="tab-content">
			<div id="withdraw" class="container tab-pane active">
				<br> <br>
				<h3>Withdraw</h3>
				<br>
				<frm:form action="adminWithdraw" method="POST"
					modelAttribute="transaction">
					<table>
						<tr>
							<td>From Account:</td>
							<td><frm:input path="fromAccountId" /></td>
							<td><frm:errors path="fromAccountId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Amount:</td>
							<td><frm:input path="transactionAmount" /></td>
							<td><frm:errors path="transactionAmount" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Comments:</td>
							<td><frm:input path="comments" /></td>
							<td><frm:errors path="comments" cssClass="error" /></td>
						</tr>
						<tr>
							<td><frm:hidden path="trxType" value="WITHDRAW" /></td>
							<td colspan="2" align="right"><input type="submit"
								value="withdraw" /></td>
						</tr>
					</table>
				</frm:form>
				<br> <br>
				<c:if test="${ withdraw != null }">
					<div class="mx-auto" style="width: 1100px;">
						<table class="table table-striped">
							<thead class="thead-dark">
								<tr>
									<th>Account Id</th>
									<th>Current Balance</th>
									<th>Account Type</th>
								</tr>
							</thead>
							<tr>
								<td>${ withdraw.accountId }</td>
								<td>${ withdraw.accountCurrentBalance }</td>
								<td>${ withdraw.accountType }</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
			<div id="deposit" class="container tab-pane fade">
				<br> <br>
				<h3>Deposit</h3>
				<br>
				<frm:form action="adminDeposit" method="POST"
					modelAttribute="transaction">
					<table>
						<tr>
							<td>To Account:</td>
							<td><frm:input path="toAccountId" /></td>
							<td><frm:errors path="toAccountId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Amount:</td>
							<td><frm:input path="transactionAmount" /></td>
							<td><frm:errors path="transactionAmount" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Comments:</td>
							<td><frm:input path="comments" /></td>
							<td><frm:errors path="comments" cssClass="error" /></td>
						</tr>
						<tr>
							<td><frm:hidden path="trxType" value="DEPOSIT" /></td>
							<td colspan="2" align="right"><input type="submit"
								value="deposit" /></td>
						</tr>
					</table>
				</frm:form>
				<br> <br>
				<c:if test="${ deposit != null }">
					<div class="mx-auto" style="width: 1100px;">
						<table class="table table-striped">
							<thead class="thead-dark">
								<tr>
									<th>Account Id</th>
									<th>Current Balance</th>
									<th>Account Type</th>
								</tr>
							</thead>
							<tr>
								<td>${ deposit.accountId }</td>
								<td>${ deposit.accountCurrentBalance }</td>
								<td>${ deposit.accountType }</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
			<div id="transfer" class="container tab-pane fade">
				<br> <br>
				<h3>Transfer</h3>
				<br>
				<frm:form action="adminTransfer" method="POST"
					modelAttribute="transaction">
					<table>
						<tr>
							<td>From Account:</td>
							<td><frm:input path="fromAccountId" /></td>
							<td><frm:errors path="fromAccountId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>To Account:</td>
							<td><frm:input path="toAccountId" /></td>
							<td><frm:errors path="toAccountId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Amount:</td>
							<td><frm:input path="transactionAmount" /></td>
							<td><frm:errors path="transactionAmount" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Comments:</td>
							<td><frm:input path="comments" /></td>
							<td><frm:errors path="comments" cssClass="error" /></td>
						</tr>

						<tr>
							<td><frm:hidden path="trxType" value="TRANSFER" /></td>
							<td colspan="2" align="right"><input type="submit"
								value="transfer" /></td>
						</tr>
					</table>
				</frm:form>
				<br> <br>
				<c:if test="${ transferfrom != null }">
					<div class="mx-auto" style="width: 1100px;">
						<table class="table table-striped">
							<thead class="thead-dark">
								<tr>
									<th>Account Id</th>
									<th>Current Balance</th>
									<th>Account Type</th>
								</tr>
							</thead>
							<tr>
								<td>${ transferfrom.accountId }</td>
								<td>${ transferfrom.accountCurrentBalance }</td>
								<td>${ transferfrom.accountType }</td>
							</tr>
						</table>
					</div>
				</c:if>
				<br> <br>
				<c:if test="${ transferto != null }">
					<div class="mx-auto" style="width: 1100px;">
						<table class="table table-striped">
							<thead class="thead-dark">
								<tr>
									<th>Account Id</th>
									<th>Current Balance</th>
									<th>Account Type</th>
								</tr>
							</thead>
							<tr>
								<td>${ transferto.accountId }</td>
								<td>${ transferto.accountCurrentBalance }</td>
								<td>${ transferto.accountType }</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
			<div id="accounts" class="container tab-pane fade">
				<br> <br>
				<h3>Accounts</h3>
				<br>
				<frm:form action="adminSaveAccount" method="POST"
					modelAttribute="account">
					<table>
						<tr>
							<td>Account Id:</td>
							<td><frm:input path="accountId" /></td>
							<td><frm:errors path="accountId" cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Account Type:</td>
							<td><frm:select type="accountType" path="accountType">
									<frm:option value="" label="Please selece a value" />
									<frm:options items="${ accountType.values }" />
								</frm:select></td>
							<td><frm:errors path="accountType" cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Branch Id:</td>
							<td><frm:input path="accountBranch.branchId" /></td>
							<td><frm:errors path="accountBranch.branchId"
									cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Customer Id:</td>
							<td><frm:input path="accountCustomer.customerId" /></td>
							<td><frm:errors path="accountCustomer.customerId"
									cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Account Holder:</td>
							<td><frm:input path="accountHolder" /></td>
							<td><frm:errors path="accountHolder" cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Date Opened:</td>
							<td><frm:input type="date" path="accountDateOpened" /></td>
							<td><frm:errors path="accountDateOpened" cssStyle="error" /></td>
						</tr>
						<tr>
							<td>Current Balance:</td>
							<td><frm:input path="accountCurrentBalance" /></td>
							<td><frm:errors path="accountCurrentBalance"
									cssStyle="error" /></td>
						</tr>
						<tr>
							<td align="right" colspan="2"><input
								class="btn btn-primary btn-lg" type="submit" value="submit" /></td>
						</tr>
					</table>
				</frm:form>

				<br> <br>

				<div class="mx-auto" style="width: 1100px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>Account Id</th>
								<th>Account Type</th>
								<th>Branch Id</th>
								<th>Branch Name</th>
								<th>Customer Id</th>
								<th>Customer Name</th>
								<th>Account Holder</th>
								<th>Date Opened</th>
								<th>Current Balance</th>
								<th colspan="2">Action</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfAccounts }" var="account">
							<tr>
								<td>${ account.accountId }</td>
								<td>${ account.accountType }</td>
								<td>${ account.accountBranch.branchId }</td>
								<td>${ account.accountBranch.branchName }</td>
								<td>${ account.accountCustomer.customerId }</td>
								<td>${ account.accountCustomer.customerName }</td>
								<td>${ account.accountHolder }</td>
								<td>${ account.accountDateOpened }</td>
								<td>${ account.accountCurrentBalance }</td>
								<td><a
									href="${pageContext.request.contextPath}/adminDeleteAccount?accountId=${ account.accountId }">delete</a></td>
								<td><a
									href="${pageContext.request.contextPath}/adminUpdateAccount?accountId=${ account.accountId }">update</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="customers" class="container tab-pane fade">
				<br> <br>
				<h3>Customers</h3>
				<br>
				<frm:form action="adminSaveCustomer" method="POST"
					modelAttribute="customer">
					<table>
						<tr>
							<td>Customer Id:</td>
							<td><frm:input path="customerId" /></td>
							<td><frm:errors path="customerId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Name:</td>
							<td><frm:input path="customerName" /></td>
							<td><frm:errors path="customerName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>User Id:</td>
							<td><frm:input path="user.userId" /></td>
							<td><frm:errors path="user.userId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address Line 1:</td>
							<td><frm:input path="customerAddress.addressLine1" /></td>
							<td><frm:errors path="customerAddress.addressLine1"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address Line 2:</td>
							<td><frm:input path="customerAddress.addressLine2" /></td>
							<td><frm:errors path="customerAddress.addressLine2"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>City:</td>
							<td><frm:input path="customerAddress.city" /></td>
							<td><frm:errors path="customerAddress.city" cssClass="error" /></td>
						</tr>
						<tr>
							<td>State:</td>
							<td><frm:input path="customerAddress.state" /></td>
							<td><frm:errors path="customerAddress.state"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mobile:</td>
							<td><frm:input path="customerMobile" /></td>
							<td><frm:errors path="customerMobile" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Phone:</td>
							<td><frm:input path="customerPhone" /></td>
							<td><frm:errors path="customerPhone" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><frm:input path="customerEmail" /></td>
							<td><frm:errors path="customerEmail" cssClass="error" /></td>
						</tr>

						<tr>
							<td>SSN:</td>
							<td><frm:input path="customerSSN" /></td>
							<td><frm:errors path="customerSSN" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Gender:</td>
							<td><frm:input path="customerGender" /></td>
							<td><frm:errors path="customerGender" cssClass="error" /></td>
						</tr>
						<tr>
							<td>DOB:</td>
							<td><frm:input type="date" path="customerDob" /></td>
							<td><frm:errors path="customerDob" cssClass="error" /></td>
						</tr>
						<tr>
							<td colspan="2" align="right"><input
								class="btn btn-primary btn-lg" type="submit" value="submit" /></td>
						</tr>
					</table>
				</frm:form>
				<br> <br>

				<table class="table table-sm table-striped">
					<thead class="thead-dark">
						<tr>
							<th>Customer Id</th>
							<th>Name</th>
							<th>User Id</th>
							<th>Address Line 1</th>
							<th>Address Line 2</th>
							<th>City</th>
							<th>State</th>
							<th>Mobile</th>
							<th>Phone</th>
							<th>Email</th>
							<th>SSN</th>
							<th>Gender</th>
							<th>DOB</th>
							<th colspan="2">Action</th>
						</tr>
					</thead>

					<c:forEach items="${ listOfCustomers }" var="customer">
						<tr>
							<td>${ customer.customerId }</td>
							<td>${ customer.customerName }</td>
							<td>${ customer.user.userId }</td>
							<td>${ customer.customerAddress.addressLine1 }</td>
							<td>${ customer.customerAddress.addressLine2 }</td>
							<td>${ customer.customerAddress.city }</td>
							<td>${ customer.customerAddress.state }</td>
							<td>${ customer.customerMobile }</td>
							<td>${ customer.customerPhone }</td>
							<td>${ customer.customerEmail }</td>
							<td>${ customer.customerSSN }</td>
							<td>${ customer.customerGender }</td>
							<td>${ customer.customerDob }</td>
							<td><a
								href="${pageContext.request.contextPath}/adminDeleteCustomer?customerId=${ customer.customerId }">delete</a></td>
							<td><a
								href="${pageContext.request.contextPath}/adminUpdateCustomer?customerId=${ customer.customerId }">update</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="users" class="container tab-pane fade">
				<br> <br>
				<h3>Users</h3>
				<br>
				<frm:form action="adminSaveUser" method="POST" modelAttribute="user">
					<table>
						<tr>
							<td>User ID:</td>
							<td><frm:input path="userId" /></td>
							<td><frm:errors path="userId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Username:</td>
							<td><frm:input path="name" /></td>
							<td><frm:errors path="name" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><frm:input type="password" path="password" /></td>
							<td><frm:errors path="password" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><frm:input path="email" /></td>
							<td><frm:errors path="email" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Roles:</td>
							<td><c:forEach items="${roleSet}" var="role">
									<c:choose>
										<c:when test="${fn:contains(selectedRoles, role)}">
											<strong>${role.name}</strong>
											<frm:checkbox path="roles" value="${role.roleId}"
												checked="true" />&nbsp;&nbsp;
								</c:when>
										<c:otherwise>
									${role.name}<frm:checkbox path="roles" value="${role.roleId}" />&nbsp;&nbsp;
								</c:otherwise>
									</c:choose>
								</c:forEach></td>

							<td><frm:errors path="roles" cssClass="error" /></td>
						</tr>
						<tr>
							<td align="right" colspan="2"><input
								class="btn btn-primary btn-lg" type="submit" value="submit" /></td>
						</tr>
					</table>
				</frm:form>

				<br> <br>

				<div class="mx-auto" style="width: 1000px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>User Id</th>
								<th>Username</th>
								<th>Password</th>
								<th>Email</th>
								<th>Roles</th>
								<th colspan="2">Action</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfUsers }" var="user">
							<tr>
								<td>${ user.userId }</td>
								<td>${ user.name }</td>
								<td>***********</td>
								<%-- 								<td>${ user.password }</td> --%>
								<td>${ user.email }</td>
								<td><c:forEach items="${user.roles}" var="role">
   							${role.name}&nbsp;
						</c:forEach></td>
								<td><a href="${pageContext.request.contextPath}/adminDeleteUser?userId=${ user.userId }">delete</a></td>
								<td><a href="${pageContext.request.contextPath}/adminUpdateUser?userId=${ user.userId }">update</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="branches" class="container tab-pane fade">
				<br> <br>
				<h3>Branch Details</h3>
				<br>
				<frm:form action="adminSaveBranch" method="POST"
					modelAttribute="branch">
					<table>
						<tr>
							<td>Branch Id:</td>
							<td><frm:input path="branchId" /></td>
							<td><frm:errors path="branchId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Branch Name:</td>
							<td><frm:input path="branchName" /></td>
							<td><frm:errors path="branchName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address Line 1:</td>
							<td><frm:input path="branchAddress.addressLine1" /></td>
							<td><frm:errors path="branchAddress.addressLine1"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address Line 2:</td>
							<td><frm:input path="branchAddress.addressLine2" /></td>
							<td><frm:errors path="branchAddress.addressLine2"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>City:</td>
							<td><frm:input path="branchAddress.city" /></td>
							<td><frm:errors path="branchAddress.city" cssClass="error" /></td>
						</tr>
						<tr>
							<td>State:</td>
							<td><frm:input path="branchAddress.state" /></td>
							<td><frm:errors path="branchAddress.state" cssClass="error" /></td>
						</tr>
						<tr>
							<td align="right" colspan="2"><input
								class="btn btn-primary btn-lg" type="submit" value="submit" /></td>
						</tr>
					</table>
				</frm:form>

				<br> <br>

				<div class="mx-auto" style="width: 1000px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>Branch Id</th>
								<th>Branch Name</th>
								<th>Address Line 1</th>
								<th>Address Line 2</th>
								<th>City</th>
								<th>State</th>
								<th colspan="2">Action</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfBranches }" var="branch">
							<tr>
								<td>${ branch.branchId }</td>
								<td>${ branch.branchName }</td>
								<td>${ branch.branchAddress.addressLine1 }</td>
								<td>${ branch.branchAddress.addressLine2 }</td>
								<td>${ branch.branchAddress.city }</td>
								<td>${ branch.branchAddress.state }</td>
								<td><a
									href="${pageContext.request.contextPath}/adminDeleteBranch?branchId=${ branch.branchId }">delete</a></td>
								<td><a
									href="${pageContext.request.contextPath}/adminUpdateBranch?branchId=${ branch.branchId }">update</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="history" class="container tab-pane fade">
				<br> <br>
				<h3>History</h3>
				<br>
				<form action="filterTrxByAcct" method="POST">
					<table>
						<tr>
							<td>Filter By Account #:</td>
							<td><input type="number" name="filterAcctId" value="${ filtAcct }" /></td>
						</tr>
						<tr>
							<td colspan="2" align="right"><input type="submit"
								name="action" value="clear" /></td>
							<td colspan="2" align="right"><input type="submit"
								name="action" value="filter" /></td>
						</tr>
					</table>
				</form>
				<br>
				<table class="table table-striped">
					<thead class="thead-dark">

						<tr>
							<th>Transaction Id</th>
							<th>From Account</th>
							<th>To Account</th>
							<th>Type</th>
							<th>Amount</th>
							<th>Date Time</th>
							<th>Comments</th>
						</tr>
					</thead>

					<c:forEach items="${ listOfTransactions }" var="transaction">
						<tr>
							<td>${ transaction.trxId }</td>
							<c:choose>
								<c:when test="${ transaction.trxFromAccount == null }">
									<td>N/A</td>
								</c:when>
								<c:when test="${ transaction.trxFromAccount != null }">
									<td>${ transaction.trxFromAccount }</td>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${ transaction.trxToAccount == null }">
									<td>N/A</td>
								</c:when>
								<c:when test="${ transaction.trxToAccount != null }">
									<td>${ transaction.trxToAccount }</td>
								</c:when>
							</c:choose>
							<td>${ transaction.transactionType }</td>
							<td>${ transaction.trxAmount }</td>
							<td>${ transaction.trxLocalDateTime }</td>
							<td>${ transaction.comments }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

</body>
</html>