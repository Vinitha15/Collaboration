/**
 * 
 */
var app = angular.module("myApp", ["ngRoute", "ngCookies"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/home", {
        templateUrl : 'views/home.html'
    })
    .when("/register", {
        templateUrl : 'views/register.html',
        controller:'UserController'
    })
     .when("/login", {
        templateUrl : 'views/login.html',
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