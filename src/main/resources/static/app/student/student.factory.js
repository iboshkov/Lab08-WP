(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .factory('StudentService', StudentServiceFn);

    StudentServiceFn.$inject = ['$log', '$timeout', '$q', "$resource"];
    var API_URL = "/api/students/"
    /* @ngInject */
    function StudentServiceFn($log, $timeout, $q, $resource) {
        var res =  $resource(API_URL + ':id', { id: '@id' }, {
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
            getById: getByIdFn,
            getAll: getAllFn,
            remove: removeFn,
            getAvailableCourses: getAvailableFn
        };

        function getAllFn() {
            return res.query().$promise;
        }

        function saveFn(student) {
            console.log(student);
            return res.save(student).$promise;
        }

        function removeFn(student) {
            console.log(student);
            return res.remove(student).$promise;
        }

        function updateFn(student) {
            return res.update(student).$promise;
        }

        function getByIdFn(studentId) {
            return res.get({id: studentId}).$promise;
        }

        function getAvailableFn(studentId) {
            return qualifiedResource.query({id: studentId}).$promise;
        }

        return service;
    }

})(angular);

