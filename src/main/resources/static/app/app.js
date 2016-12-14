/**
 * Created by user on 12/12/16.
 */
require("../bower_components/angular/angular");
require("../bower_components/bootstrap/dist/css/bootstrap.css");

require("../bower_components/angular-bootstrap/ui-bootstrap");
require("../bower_components/angular-ui-select/dist/select.min.css");
require("../bower_components/angular-ui-select/dist/select.min");
require("../bower_components/angular-resource/angular-resource.min");

require("../bower_components/angular-bootstrap/ui-bootstrap-csp.css");
require("../bower_components/angular-ui-router/release/angular-ui-router");

(function (angular) {
    'use strict';


    var app = angular
        .module('wp-lab08', [
            'ui.router',
            'ui.select',
            'ngResource'
        ]);

    app.config(['$resourceProvider', function($resourceProvider) {
        // Don't strip trailing slashes from calculated URLs
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }]);

})(angular);

// App
require("./course/course");
require("./student/student");