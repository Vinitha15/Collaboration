/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8086/collabbackend"
	
		jobService.savejob=function(job){
	return	$http.post(BASE_URL + "/saveJob",job)
	}
	
	return jobService;
})