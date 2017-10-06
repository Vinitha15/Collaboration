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
	
	$scope.addcomment=function(){
		$scope.BlogComment.blogpost=$scope.blogpost
		BlogPostService.addcomment($scope.BlogComment).then(function(response){
			$scope.BlogComment.commenttxt=''
			console.log(response.data)
			getallcomments()
		},function(response){
			console.log(response.data)
		})
	}
	
	function getallcomments(){
		BlogPostService.getallcomments(id).then(function(response){
			$scope.blogcomments=response.data
			console.log(response.data)
		},function(response){
			console.log(response.data)
		})
	}
	
	getallcomments()
})
