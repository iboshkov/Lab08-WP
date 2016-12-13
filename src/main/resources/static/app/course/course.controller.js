(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .controller('CourseController', CourseController);

    CourseController.$inject = ['$log', 'CourseService'];

    /* @ngInject */
    function CourseController($log, CourseService) {
        var vm = this;
        vm.title = 'Course';
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
            CourseService.getAll().then(function (data) {
                vm.entities = data;
                console.log("Courses loaded");
                console.log(vm.entities);
            });
        }

        function save() {
            console.log(vm.entity)
            vm.saveOkMsg = null;
            vm.saveErrMsg = null;

            var promise;

            if (vm.is_editing) {
                promise = CourseService.update(vm.entity);
            }
            else {
                promise = CourseService.save(vm.entity);
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
            CourseService
                .remove(entity)
                .then(function () {
                    loadCourses();
                });
        }
    }

})(angular);

