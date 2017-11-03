/**
 * 
 */
app.controller("HomeController",function($scope,$rootScope,BlogPostService,FriendService,$location){
	
	function getApprovalstatus(){
		BlogPostService.getApprovalstatus().then(function(response){
			$rootScope.approvalstatus=response.data
			$rootScope.approvalstatusLength=$scope.approvalstatus.length
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
	
	function getFriendsLength(){
		FriendService.getFriends().then(function(response){
			$scope.friends=response.data //List<Friend> select * from friend where status='A' and (fromId=? or toId=?)
			$rootScope.noOfFriends=$scope.friends.length
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
		}
	
	$rootScope.reset=function(){
		$rootScope.pendingRequestsLength=0
	}
	
	$rootScope.updateviewedStatus=function(){
		$rootScope.approvalstatusLength=0
		BlogPostService.updateviewedStatus($rootScope.approvalstatus).then(function(response){
			console.log(response.data)
		},function(response){
			console.log(response.status)
		})
	}
	
	
	$rootScope.updatePendingRequest=function(request,value){
		console.log('pending request ' + request)
		request.status=value //value is 'A' for accept and 'D' for delete
		console.log('after assigning value to status  ' + request)
		FriendService.updatePendingRequest(request).then(function(response){
			pendingRequests();
			$location.path('/home')
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	getApprovalstatus()
	pendingRequests()
	getFriendsLength()
	/*$interval(function(){
		getApprovalstatus()
		pendingRequests()
		getFriends()
	},50000)*/
})