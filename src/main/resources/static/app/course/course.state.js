(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .config(registerState);


    registerState.$inject = ['$stateProvider'];

    function registerState($stateProvider) {
        $stateProvider.state('course', {
            url: '/courses',
            templateUrl: 'app/course/course.view.html',
            controller: 'CourseController',
            controllerAs: 'vm'
        });
        $stateProvider.state('single_course', {
            url: '/courses/:id',
            templateUrl: 'app/course/single.view.html',
            controller: 'SingleCourseController',
            controllerAs: 'vm'
        });
    }

})(angular);
