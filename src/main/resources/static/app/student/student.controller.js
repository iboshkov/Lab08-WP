(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$log', 'StudentService'];

    /* @ngInject */
    function StudentController($log, StudentService) {
        var vm = this;
        vm.title = 'Student';
        vm.save = save;
        vm.clear = clear;
        vm.edit = edit;
        vm.remove = remove;
        vm.entity = {};


        vm.entities = [];
        vm.saveOkMsg = null;
        vm.saveErrMsg = null;
        vm.availableSizes = [20, 40];
        loadCourses();

        function loadCourses() {
            StudentService.getAll().then(function (data) {
                vm.entities = data;
                console.log("Students loaded");
                console.log(vm.entities);
            });
        }

        function save() {
            console.log(vm.entity)
            vm.saveOkMsg = null;
            vm.saveErrMsg = null;

            var promise;

            if (vm.is_editing) {
                promise = StudentService.update(vm.entity);
            }
            else {
                promise = StudentService.save(vm.entity);
            }

            vm.is_editing = false;
            promise.then(successCallback, errorCallback);
            function successCallback(data) {
                loadCourses();
                vm.saveOkMsg = "Course with id " + data.id + " is saved";
                clear();
            }

            function errorCallback(data) {
                vm.saveErrMsg = "Saving error occurred: " + data.message;
            }
        }

        function clear() {
            vm.entity = {};
            vm.is_editing = false;
        }

        function edit(entity) {
            console.log(entity);
            vm.entity = {};
            vm.is_editing = true;
            angular.extend(vm.entity, entity);
        }

        function remove(entity) {
            StudentService
                .remove(entity)
                .then(function () {
                    loadCourses();
                });
        }
    }

})(angular);

