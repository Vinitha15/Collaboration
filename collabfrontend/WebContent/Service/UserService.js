/**
 * 
 */
app.factory('UserService',function($http){
	var userService={}
	var BASE_URL="http://localhost:8086/collabbackend"
	
	userService.registerUser=function(user){
	return	$http.post(BASE_URL + "/registerform",user)
	}
	
	userService.login=function(user){
		return	$http.post(BASE_URL + "/login",user)
		}
		
	userService.logout=function(){
		return	$http.put(BASE_URL + "/logout")
		}
	userService.getUser=function(){
		return	$http.get(BASE_URL + "/getuser")
		}
	userService.updateUser=function(user){
		return	$http.put(BASE_URL + "/updateprofile",user)
		}
	userService.getuserblogpost=function(username){
		return $http.get(BASE_URL +"/getBlogpostByuserid/"+username)
	}
	
	return userService;
})