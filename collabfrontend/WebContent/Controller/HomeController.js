/**
 * 
 */
app.controller("HomeController",function($scope,$rootScope,BlogPostService,FriendService){
	
	function getApprovalstatus(){
		BlogPostService.getApprovalstatus().then(function(response){
			$rootScope.approvalstatus=response.data
			console.log($rootScope.approvalstatus.length)
		},function(response){
			console.log(response.status)
		})
	}	
	function pendingRequests(){
		FriendService.pendingRequests().then(function(response){
			$scope.pendingRequests=response.data//List of Friend objects [use only fromId])
			$rootScope.pendingRequests=response.data
			$rootScope.pendingRequestsLength=$scope.pendingRequests.length
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.updatePendingRequest=function(request,value){
		console.log('pending request ' + request)
		request.status=value //value is 'A' for accept and 'D' for delete
		console.log('after assigning value to status  ' + request)
		FriendService.updatePendingRequest(request).then(function(response){
			pendingRequests();
			$location.path('/pendingrequests')
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	function getFriends(){
		FriendService.getFriends().then(function(response){
			$scope.friends=response.data //List<Friend> select * from friend where status='A' and (fromId=? or toId=?)
			$rootScope.noOfFriends=$scope.friends.length
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
		}
	getApprovalstatus()
	pendingRequests()
	getFriends()
	$interval(function(){
		getApprovalstatus()
		pendingRequests()
		getFriends()
	},50000)
})