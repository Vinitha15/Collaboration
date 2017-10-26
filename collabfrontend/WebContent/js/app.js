/**
 * 
 */
var app = angular.module("myApp", ["ngRoute", "ngCookies"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/home", {
        templateUrl : 'views/home.html',
        controller:'HomeController'
    })
    .when("/register", {
        templateUrl : 'views/register.html',
        controller:'UserController'
    })
     .when("/login", {
        templateUrl : 'views/login.html',
        controller:'UserController'
    })
    .when("/profile", {
        templateUrl : 'views/profile.html',
        controller:'UserController'
    })
    .when("/editprofile", {
        templateUrl : 'views/editprofile.html',
        controller:'UserController'
    })
    .when("/savejob", {
        templateUrl : 'views/jobform.html',
        controller:'JobController'
    })
     .when("/getalljobs", {
        templateUrl : 'views/joblist.html',
        controller:'JobController'
    })
     .when("/saveblogpost", {
        templateUrl : 'views/blogpostform.html',
        controller:'BlogPostController'
    })
    .when("/getallblogs", {
        templateUrl : 'views/blogpostlist.html',
        controller:'BlogPostController'
    })
    .when("/blogpostforapproval/:id", {
        templateUrl : 'views/blogpostforapproval.html',
        controller:'BlogPostDetailController'
    })
    .when("/blogpostdetail/:id", {
        templateUrl : 'views/blogpostdetail.html',
        controller:'BlogPostDetailController'
    })
    .when("/approvalstatus/:id", {
        templateUrl : 'views/blogpoststatus.html',
        controller:'BlogPostDetailController'
    })
    .when("/uploadprofilepic", {
        templateUrl : 'views/uploadprofilepic.html'
    })
     .when("/suggestedusers", {
        templateUrl : 'views/suggestedusers.html',
        controller:'FriendController'
    })
    .when('/pendingrequests',{
		templateUrl:'views/pendingRequests.html',
		controller:'FriendController'
	})
	.when('/getfriends',{
		templateUrl:'views/listoffriends.html',
		controller:'FriendController'
	})
	.when("/friendprofile/:id", {
        templateUrl : 'views/profile.html',
        controller:'FriendController'
    })
	.when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatController'
	})
    .otherwise({
		templateUrl:'views/home.html'
	})
})


app.run(function($location,UserService,$rootScope,$cookieStore){
	$rootScope.loggedout
	$rootScope.logout=function(){
		UserService.logout().then(function(response){
			delete $rootScope.currentuser
			$cookieStore.remove('currentuser')
			$rootScope.loggedout="Logged Out successfully"
			$location.path('/login')

		},function(response){
			console.log(response.status)
		})
		
	
	}
		if($rootScope.currentuser==undefined){
			$rootScope.currentuser=$cookieStore.get('currentuser')
			console.log($rootScope.currentuser)
		}
})