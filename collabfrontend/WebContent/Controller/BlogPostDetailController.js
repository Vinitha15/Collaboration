/**
 * 
 */
app.controller('BlogPostDetailController',function($scope,$location,BlogPostService,$routeParams){
	var id=$routeParams.id
	var showRejectiontext=false
	BlogPostService.getblogpostbyid(id).then(function(response){
		console.log(response.data)
		$scope.blogpost=response.data
	},function(response){
		console.log(response.status)
		if(response.status==401)
			$location.path="/login"
	})
	
	$scope.updateApproval=function(){
		console.log($scope.blogpost)
		BlogPostService.updateApproval($scope.blogpost).then(function(response){
			$location.path("/getallblogs")
		},function(response){
			if(response.data=401)
				$location.path("/login")
			console.log(response.status)
			$location.path("/blogpostforapproval/"+id)
		})
	}
	
	$scope.setRejectiontext=function(val){
		$scope.showRejectiontext=val
	}
})
