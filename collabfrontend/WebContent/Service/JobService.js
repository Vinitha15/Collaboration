/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8086/collabbackend"
	
	jobService.savejob=function(job){
		console.log(job)
	return	$http.post(BASE_URL + "/saveJob",job)
	}
	
	jobService.getalljobs=function(){
	return	$http.get(BASE_URL + "/getAlljobs")
	}
	
	jobService.getjobbyid=function(id){
	return	$http.get(BASE_URL + "/getjobByid/"+id)
	}
	
	jobService.saveappliedjob=function(job){
	return	$http.post(BASE_URL + "/applyJob",job)
	}
	
	jobService.getappliedjobs=function(){
		return	$http.get(BASE_URL + "/getappliedJobs")
		}
	
	jobService.getallappliedjobs=function(){
		return	$http.get(BASE_URL + "/getAllappliedjobs")
		}
	
	jobService.isapplied=function(jobs){
		return	$http.put(BASE_URL + "/isapplied",jobs)
		}
		
	
	return jobService;
})