(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .factory('StudentService', StudentServiceFn);

    StudentServiceFn.$inject = ['$log', '$timeout', '$q', "$resource"];
    var API_URL = "http://localhost:8080/api/students/"
    /* @ngInject */
    function StudentServiceFn($log, $timeout, $q, $resource) {
        var res =  $resource(API_URL + ':id', { id: '@id' }, {
            getAll: { method: 'GET' },
            getById: { method: 'GET' },
            save: { method: 'POST' },
            update: {
                method: 'PUT'
            },
            remove: { method: 'DELETE' }
        });


        var service = {
            save: saveFn,
            update: updateFn,
            getById: getByIdFn,
            getAll: getAllFn,
            remove: removeFn
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
            return res.query({id: studentId}).$promise;
        }

        return service;
    }

})(angular);

