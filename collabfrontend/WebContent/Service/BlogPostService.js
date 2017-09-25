/**
 * 
 */

app.factory('BlogPostService',function($http){
	var blogPostService={}
	
	var BASE_URL="http://localhost:8086/collabbackend"
		
		blogPostService.saveblogpost=function(blogpost){
		return	$http.post(BASE_URL + "/saveBlogpost",blogpost)
		}
		
		blogPostService.listofblogsapproved=function(){
		return	$http.get(BASE_URL + "/getAllblogpost/"+1)
		}
		
		blogPostService.blogswaitingforapproval=function(){
			return	$http.get(BASE_URL + "/getAllblogpost/"+0)
			}
		
		blogPostService.getblogpostbyid=function(id){
			return	$http.get(BASE_URL + "/getBlogpostByid/"+id)
			}
		
		blogPostService.updateApproval=function(blogpost){
			return	$http.put(BASE_URL + "/updateBlogpost",blogpost)
			}

	return blogPostService;
})
