(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .factory('CourseService', CourseServiceFn);

    CourseServiceFn.$inject = ['$log', '$timeout', '$q', "$resource"];
    var API_URL = "/api/courses/";
    /* @ngInject */
    function CourseServiceFn($log, $timeout, $q, $resource) {
        var rootResource =  $resource(API_URL + ':id', { id: '@id' }, {
            save: { method: 'POST' },
            update: {
                method: 'PUT'
            },

            remove: { method: 'DELETE' }
        });

        var qualifiedResource =  $resource(API_URL + ':id/qualified/', { id: '@id' });

        var service = {
            save: saveFn,
            update: updateFn,
            getQualifiedStudents: qualifiedFn,
            getById: getByIdFn,
            getAll: getAllFn,
            remove: removeFn
        };

        function getAllFn() {
            return rootResource.query().$promise;
        }

        function saveFn(course) {
            console.log(course);
            return rootResource.save(course).$promise;
        }

        function removeFn(course) {
            console.log(course);
            return rootResource.remove(course).$promise;
        }

        function updateFn(course) {
            return rootResource.update(course).$promise;
        }

        function getByIdFn(courseId) {
            return rootResource.get({id: courseId}).$promise;
        }

        function qualifiedFn(courseId) {
            return qualifiedResource.query({id: courseId}).$promise;
        }

        return service;
    }

})(angular);

