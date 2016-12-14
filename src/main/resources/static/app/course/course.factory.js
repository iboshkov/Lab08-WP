(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .factory('CourseService', CourseServiceFn);

    CourseServiceFn.$inject = ['$log', '$timeout', '$q', "$resource"];
    var API_URL = "http://localhost:8080/api/courses/"
    /* @ngInject */
    function CourseServiceFn($log, $timeout, $q, $resource) {
        var res =  $resource(API_URL + ':id', { id: '@id' }, {
            getAll: { method: 'GET' },
            getById: { method: 'GET' },
            save: { method: 'POST' },
            update: {
                method: 'PUT'
            },
            qualifiedStudents: {
                method: 'GET',
                url: API_URL + ":id/qualified/"
            },
            remove: { method: 'DELETE' }
        });


        var service = {
            save: saveFn,
            update: updateFn,
            getQualifiedStudents: qualifiedFn,
            getById: getByIdFn,
            getAll: getAllFn,
            remove: removeFn
        };

        function getAllFn() {
            return res.query().$promise;
        }

        function saveFn(course) {
            console.log(course);
            return res.save(course).$promise;
        }

        function removeFn(course) {
            console.log(course);
            return res.remove(course).$promise;
        }

        function updateFn(course) {
            return res.update(course).$promise;
        }

        function getByIdFn(courseId) {
            return res.get({id: courseId}).$promise;
        }

        function qualifiedFn(courseId) {
            return res.qualifiedStudents({id: courseId}).$promise;
        }

        return service;
    }

})(angular);

