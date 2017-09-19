/**
 * 
 */
app.controller('JobController',function($scope,$location,JobService,$rootScope,$cookieStore){
	console.log("job Controller loading.......")
	
	
	$scope.savejob=function(){
		console.log($scope.user)
		JobService.savejob($scope.job).then(function(response){
		console.log(response.status)
		$scope.success="Job entered successfully.."
		$location.path('/savejob')
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if($scope.error.code==5)
					$scope.unauthorized=response.data
				if($scope.error.code==6)
					$scope.denied=response.data
				if($scope.error.code==7)
					$scope.exception=response.data
			$location.path('/savejob')
	})
	}
})