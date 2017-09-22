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
	
	return jobService;
})