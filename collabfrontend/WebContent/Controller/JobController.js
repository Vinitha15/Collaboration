/**
 * 
 */
app.controller('JobController',function($scope,$location,JobService){
	console.log("job Controller loading.......")
	
	$scope.showjobdetails=false
	
	$scope.savejob=function(){
		console.log($scope.job)
		JobService.savejob($scope.job).then(function(response){
		console.log(response.status)
		$scope.success="Job entered successfully.."
		
	},function(response){
		console.log(response.status)
		console.log(response.data)
		$scope.error=response.data  
				if(response.status==401)
					$location.path('/login')
				$location.path('/savejob')
			
	})
	}
	
	$scope.getjobbyid=function(id){
		JobService.getjobbyid(id).then(function(response){
			$scope.showjobdetails=true
			$scope.job=response.data
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if($scope.error.code==5)
					$scope.unauthorized=response.data
				$location.path('/login')
	})
	}
	
	function listofjobs(){
		JobService.getalljobs().then(function(response){
			console.log(response.status)
			$scope.jobs=response.data
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if($scope.error.code==5)
					$scope.unauthorized=response.data
				$location.path('/login')
	})
	}
	
	listofjobs()
})