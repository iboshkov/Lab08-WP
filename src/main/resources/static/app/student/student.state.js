(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .config(registerState);


    registerState.$inject = ['$stateProvider'];

    function registerState($stateProvider) {
        $stateProvider.state('student', {
            url: '/students',
            templateUrl: 'app/student/student.view.html',
            controller: 'StudentController',
            controllerAs: 'vm'
        });

        $stateProvider.state('single_student', {
            url: '/students/:id',
            templateUrl: 'app/student/single.view.html',
            controller: 'SingleStudentController',
            controllerAs: 'vm'
        });
    }

})(angular);
