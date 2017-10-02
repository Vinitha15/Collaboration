/**
 * 
 */
app.controller("HomeController",function($scope,$rootScope,BlogPostService){
	
	function getApprovalstatus(){
		BlogPostService.getApprovalstatus().then(function(response){
			$rootScope.approvalstatus=response.data
			console.log($rootScope.approvalstatus.length)
		},function(response){
			console.log(response.status)
		})
	}	
	
	getApprovalstatus()
})