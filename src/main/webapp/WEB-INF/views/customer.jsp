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

<title>Customer</title>

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
				href="#history">History</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#profile">Profile</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#branches">Branches</a>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#support">Support</a>
		</ul>

		<!-- Tab panes -->
		<div class="tab-content">
			<div id="withdraw" class="container tab-pane active">
				<br> <br>
				<h3>Withdraw</h3>
				<br>
				<frm:form action="customerWithdraw" method="POST"
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
				<br>
				<br>
				<div class="mx-auto" style="width: 1100px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>Account Id</th>
								<th>Account Type</th>
								<th>Branch Name</th>
								<th>Current Balance</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfAccounts }" var="account">
							<tr>
								<td>${ account.accountId }</td>
								<td>${ account.accountType }</td>
								<td>${ account.accountBranch.branchName }</td>
								<td>${ account.accountCurrentBalance }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="deposit" class="container tab-pane fade">
				<br> <br>
				<h3>Deposit</h3>
				<br>
				<frm:form action="customerDeposit" method="POST"
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
				<br>
				<br>
				<div class="mx-auto" style="width: 1100px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>Account Id</th>
								<th>Account Type</th>
								<th>Branch Name</th>
								<th>Current Balance</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfAccounts }" var="account">
							<tr>
								<td>${ account.accountId }</td>
								<td>${ account.accountType }</td>
								<td>${ account.accountBranch.branchName }</td>
								<td>${ account.accountCurrentBalance }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="transfer" class="container tab-pane fade">
				<br> <br>
				<h3>Transfer</h3>
				<br>
				<frm:form action="customerTransfer" method="POST"
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
				<br>
				<br>
				<div class="mx-auto" style="width: 1100px;">
					<table class="table table-striped">
						<thead class="thead-dark">
							<tr>
								<th>Account Id</th>
								<th>Account Type</th>
								<th>Branch Name</th>
								<th>Current Balance</th>
							</tr>
						</thead>

						<c:forEach items="${ listOfAccounts }" var="account">
							<tr>
								<td>${ account.accountId }</td>
								<td>${ account.accountType }</td>
								<td>${ account.accountBranch.branchName }</td>
								<td>${ account.accountCurrentBalance }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="accounts" class="container tab-pane fade">
				<br> <br>
				<h3>Accounts</h3>
				<br>
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
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="history" class="container tab-pane fade">
				<br> <br>
				<h3>History</h3>
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
			<div id="profile" class="container tab-pane fade">
				<br> <br>
				<h3>Contact</h3>
				<br>
				<frm:form action="customerSaveProfile" method="POST"
					modelAttribute="customer">
					<table>
						<!-- 						<tr> -->
						<!-- 							<td>Customer Id:</td> -->
						<%-- 							<td><frm:input path="customerId" /></td> --%>
						<%-- 							<td><frm:errors path="customerId" cssClass="error" /></td> --%>
						<!-- 						</tr> -->
						<tr>
							<td>Name:</td>
							<td><frm:input path="customerName" /></td>
							<td><frm:errors path="customerName" cssClass="error" /></td>
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
							<td colspan="2" align="right"><input
								class="btn btn-primary btn-lg" type="submit" value="update" /></td>
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
							<th>Action</th>
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
							<td><a
								href="${pageContext.request.contextPath}/customerUpdateProfile?customerId=${ customer.customerId }">update</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="branches" class="container tab-pane fade">
				<br> <br>
				<h3>Branches</h3>
				<br>
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
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div id="support" class="container tab-pane fade">
				<br>
				<br>
				<h3>Questions and Comments</h3>
				<br>
				<form action="support" method="POST">
					<div class="form-group">
						<label>Email address</label> 
						<input type="email" class="form-control" name="supportEmail" placeholder="name@example.com">
					</div>
					<div class="form-group">
						<label>Subject</label> 
						<input type="text" class="form-control" name="supportSubject">
					</div>
					<div class="form-group">
						<label>Message</label>
						<textarea class="form-control" name="supportMessage" rows="7"></textarea>
					</div>
					<div>
      					<button type="submit" class="btn btn-primary mb-2">Submit</button>
    				</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>