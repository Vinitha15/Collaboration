/**
 * 
 */
app.controller('JobController',function($scope,$location,JobService){
	console.log("job Controller loading.......")
	
	$scope.showjobdetails=false
	$scope.savejob=function(){
		JobService.savejob($scope.job).then(function(response){
		$scope.success="Job entered successfully.."
			$location.path('/savejob')
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if(response.status==401)
					$location.path('/login')
				$location.path('/savejob')
			
	})
	}
	
	$scope.applyjob=function(job){
			
		JobService.saveappliedjob(job).then(function(response){
			console.log(response.status)
			listofjobs()
			listofappliedjobs()
			$location.path('/getalljobs')
	},function(response){
		if(response.status==401)
					$location.path('/login')
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
			isapplied($scope.jobs)
	},function(response){
		console.log(response.status)
		$scope.error=response.data  
				if($scope.error.code==5)
					$scope.unauthorized=response.data
				$location.path('/login')
	})
	}
	
	function listofappliedjobs(){
		JobService.getallappliedjobs().then(function(response){
			console.log(response.status)
			$scope.appliedjobs=response.data
	},function(response){
		console.log(response.status)
				if($scope.error.code==5)
					$location.path('/login')
	})
	}
	
	function isapplied(jobs){
		JobService.isapplied(jobs).then(function(response){
			console.log(response.status)
			$scope.applied=response.data
		},function(response){
			console.log(response.status)
			$location.path('/login')
		})
	}
	listofjobs()
	listofappliedjobs()
})