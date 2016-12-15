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
        vm.course = null;

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
            console.log("Adding student to ", vm.course);
            CourseService.getById(vm.course.id).then(function (data) {
                var actualCourse = data;
                console.log("Actual", actualCourse.students);
                console.log("Actual", actualCourse);
                actualCourse.students.push(vm.entity);

                CourseService.update(actualCourse).then(function(data) {
                    loadStudent();
                });
            }).catch(function(e) {
                console.log("Error getting course data to add student.");
            });
        };

        vm.removeCourse = function (course) {
            console.log("Removing student from ", course);
            CourseService.getById(course.id).then(function (data) {
                var actualCourse = data;
                var idx = actualCourse.students.indexOf(vm.entity);
                actualCourse.students.splice(idx, 1);

                CourseService.update(actualCourse).then(function(data) {
                    loadStudent();
                });
            }).catch(function(e) {
               console.log("Error getting course data to remove student.");
            });

        };

    }

})(angular);

