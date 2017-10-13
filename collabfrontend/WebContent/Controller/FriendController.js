/**
 * 
 */
app.controller('FriendController',function($scope,$location,FriendService,$rootScope){
	$scope.showUserDetails=false;
	function getSuggestedUsers(){
	FriendService.suggestedusers().then(function(response){
		$scope.suggestedusers=response.data
	},function(response){
		if(response.status==401)
			$location.path="/login"
		else
			console.log(response.status)
	})
	}
	
	
	$scope.sendFriendRequest=function(toId){
		FriendService.sendFriendRequest(toId).then(function(response){
			getSuggestedUsers();
			$location.path('/suggestedusers')
		},function(response){
			if(response.status==401)
				$location.path('/login')
			else
			console.log(response.status)
		})
	}
	
	$scope.getUserDetails=function(fromId){
		$scope.showUserDetails=true
		FriendService.getUserDetails(fromId).then(function(response){
			$scope.user=response.data
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
	
	getSuggestedUsers();
	getFriends();
})