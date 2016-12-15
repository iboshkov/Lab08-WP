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
        vm.entity = {id: $stateParams.id};
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
                console.log(vm.qualifiedStudents);

            }).catch(function(e){
                console.log("Fail", e);
            });

            CourseService.getById(vm.entity.id).then(function (data) {
                vm.entity = data;
                console.log("Course loaded");
                console.log(vm.entity);
            }).catch(function(e) {
                console.log("Error");
                console.log(e);
            });
        }

        vm.addStudent = function () {
            vm.entity.students.push(vm.student);
            CourseService.update(vm.entity);
        };

        vm.removeStudent = function (student) {
            console.log(vm.entity.students);
            var idx = vm.entity.students.indexOf(student);
            vm.entity.students.splice(idx, 1);
            console.log(vm.entity.students);

            CourseService.update(vm.entity);
        };

    }

})(angular);

