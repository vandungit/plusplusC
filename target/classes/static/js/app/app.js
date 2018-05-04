var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/plusplusC',
    COURSE_SERVICE_API : 'http://localhost:8080/plusplusC/api/course/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'CourseController',
                controllerAs:'ctrl',
                resolve: {
                    courses: function ($q, CourseService) {
                        console.log('Load all courses');
                        var deferred = $q.defer();
                        CourseService.loadAllCourses().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

