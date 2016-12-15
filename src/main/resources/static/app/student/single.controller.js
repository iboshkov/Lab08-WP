(function (angular) {
    'use strict';

    angular
        .module('wp-lab08')
        .controller('SingleStudentController', SingleStudentController);

    SingleStudentController.$inject = ['$log', 'CourseService', 'StudentService', '$stateParams'];

    /* @ngInject */
    function SingleStudentController($log, CourseService, StudentService, $stateParams) {
        var vm = this;
        vm.title = 'Single Student';
        vm.entity = {id: $stateParams.id};
        vm.saveOkMsg = null;
        vm.saveErrMsg = null;
        vm.availableSizes = [20, 40];
        vm.qualifiedCourses = [];
        vm.student = null;

        loadStudent();

        function loadStudent() {

            StudentService.getById(vm.entity.id).then(function (data) {
                vm.entity = data;
                console.log("Student loaded");
                console.log(vm.entity);
            }).catch(function(e) {
                console.log("Error loading student data");
                console.log(e);
            });

            StudentService.getAvailableCourses(vm.entity.id).then(function (data) {
                vm.qualifiedCourses = data;
                console.log("Qualifications loaded");
                console.log(vm.qualifiedCourses);
            }).catch(function(e) {
                console.log("Error loading qualifications");
                console.log(e);
            });
        }

        vm.addCourse = function () {
            vm.entity.students.push(vm.student);
            CourseService.update(vm.entity);
        };

        vm.removeCourse = function (course) {
            console.log(vm.entity.courses);
            var idx = vm.entity.courses.indexOf(course);
            vm.entity.courses.splice(idx, 1);
            console.log(vm.entity.courses);

            StudentService.update(vm.entity);
        };

    }

})(angular);

