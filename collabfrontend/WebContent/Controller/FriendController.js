/**
 * 
 */
app.controller('FriendController',function($scope,$location,FriendService,$rootScope,$routeParams){
	$scope.showUserDetails=false;
	var id=$routeParams.id
	FriendService.getUserDetails(id).then(function(response){
		$scope.user=response.data
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	FriendService.getallmutualfriends(id).then(function(response){
		$scope.mutualfriend=response.data
		getMutualFriends($scope.mutualfriend)
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	FriendService.getuserblogpost(id).then(function(response){
		$scope.blogposts=response.data
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	
	function getSuggestedUsers(){
	FriendService.suggestedusers().then(function(response){
		$scope.suggestedusers=response.data
		 getMutualFriends($scope.suggestedusers)
	},function(response){
		if(response.status==401)
			$location.path="/login"
		else
			console.log(response.status)
	})
	}
	
	function getMutualFriends(suggestedUsers){
		FriendService.getMutualFriends(suggestedUsers).then(function(response){
			
			$scope.mutualFriends=response.data
		},function(response){
			console.log(response.status)
			$location.path('/login')
		})
	}
	
	function getMutualFriendsbyfriends(friends){
		FriendService.getMutualFriendsbyfriends(friends).then(function(response){
			
			$scope.mutualFriends=response.data
		},function(response){
			console.log(response.status)
			$location.path('/login')
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
	
	function getFriends(){
	FriendService.getFriends().then(function(response){
		$scope.friends=response.data //List<Friend> select * from friend where status='A' and (fromId=? or toId=?)
		$rootScope.noOfFriends=$scope.friends.length
		getMutualFriendsbyfriends($scope.friends)
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	}
	
	getSuggestedUsers();
	getFriends();
})