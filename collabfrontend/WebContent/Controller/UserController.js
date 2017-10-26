/**
 * 
 */
app.controller('UserController',function($scope,$location,UserService,$rootScope,$cookieStore,JobService){
	console.log("user Controller loading.......")
	
	if($rootScope.currentuser!=undefined){
		UserService.getUser().then(function(response){
			$scope.user=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})	
		UserService.getuserblogpost($rootScope.currentuser.username).then(function(response){
		$scope.blogposts=response.data
		},function(response){
		if(response.status==401)
			$location.path('/login')
		})
		
		JobService.getappliedjobs().then(function(response){
		$scope.appliedjobs=response.data
		},function(response){
		if(response.status==401)
			$location.path('/login')
		})
	}
	
	
	
	
	$scope.registerUser=function(){
		console.log($scope.user)
		UserService.registerUser($scope.user).then(function(response){
		console.log(response.status)
		$scope.success="Registered successfully.. please login again"
		$location.path('/login')
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if($scope.error.code==2)
					$scope.duplicateEmail=response.data
				if($scope.error.code==3)
					$scope.duplicateUsername=response.data
				if($scope.error.code==1)
					$scope.exception=response.data
			$location.path('/register')
	})
	}
	
	$scope.login=function(){
		UserService.login($scope.user).then(function(response){
			console.log(response.data)
			$rootScope.currentuser=response.data
			$cookieStore.put('currentuser',response.data)
			$location.path('/home')
		},function(response){
			console.log(response.data)
			$scope.loginfail=response.data
			$location.path('/login')
		})	
	}
	
	
	$scope.updateUser=function(){
		UserService.updateUser($scope.user).then(function(response){
			$location.path('/home')
		},function(response){
			console.log(response.data)
			if(response.status==401)
				$location.path('/login')
			$scope.error=response.data
			$location.path('/editprofile')
		})
	}
	
	
		
	
})