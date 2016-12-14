(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .controller('SingleCourseController', SingleCourseController);

    SingleCourseController.$inject = ['$log', 'CourseService', 'StudentService', '$stateParams'];

    /* @ngInject */
    function SingleCourseController($log, CourseService, StudentService, $stateParams) {
        var vm = this;
        vm.title = 'Single Course';
        vm.save = save;
        vm.clear = clear;
        vm.edit = edit;
        vm.remove = remove;
        vm.entity = {id: $stateParams.id};
        vm.students = [];
        vm.saveOkMsg = null;
        vm.saveErrMsg = null;
        vm.availableSizes = [20, 40];
        vm.qualifiedStudents = [];
        vm.student = null;

        loadCourse();

        function loadCourse() {
            CourseService.getQualifiedStudents(vm.entity.id).then(function(data) {
                console.log("Got students");
                vm.qualifiedStudents = data;
            }).catch(function(e){

                console.log("Fail", e);
            });

            CourseService.getById(vm.entity.id).then(function (data) {
                console.log("Wat");
                vm.entity = data;
                console.log("Course loaded");
                console.log(vm.entity);
            }).catch(function(e) {
                console.log("Error");
                console.log(e);
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
                loadCourse();
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
                    loadCourse();
                });
        }
    }

})(angular);

