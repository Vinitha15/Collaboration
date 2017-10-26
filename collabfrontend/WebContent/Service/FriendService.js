/**
 * 
 */
app.factory('FriendService',function($http){
	var friendService={}
	
	var BASE_URL="http://localhost:8086/collabbackend"
		
		friendService.suggestedusers=function(){
		return	$http.get(BASE_URL + "/suggestedusers")
		}
		
	friendService.sendFriendRequest=function(toId){
		return $http.post(BASE_URL +"/friendrequest/"+toId)
	}
	
	friendService.pendingRequests=function(){
		return $http.get(BASE_URL + "/pendingrequests")
	}
	friendService.updatePendingRequest=function(request){
		return $http.put(BASE_URL + "/updatependingrequest",request)
	}
	friendService.getUserDetails=function(id){
		return $http.get(BASE_URL + "/getuserdetails/"+id)
	}
	
	friendService.getFriends=function(){
		return $http.get(BASE_URL + "/getfriends")
	}
	
	friendService.getMutualFriends=function(suggestedUsers){
		return $http.put(BASE_URL + "/getmutualfriends",suggestedUsers)
	}
	
	friendService.getMutualFriendsbyfriends=function(friends){
		return $http.put(BASE_URL + "/getmutualfriendsbyfriends",friends)
	}
	
	friendService.getallmutualfriends=function(friend){
		return $http.put(BASE_URL +"/getallmutualfriends/"+friend)
	}
	
	friendService.getuserblogpost=function(friend){
		return $http.get(BASE_URL +"/getBlogpostByuserid/"+friend)
	}
	

	return friendService;
})