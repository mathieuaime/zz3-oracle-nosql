/**
 * Module principal.
 * Point d'entrée pour le code de l'application.
 */

'use strict';
/* App Module. */

var oraclenosql = angular.module('oraclenosql', ['ngRoute', 'colorpicker.module', 'oraclenosqlControllers']);

oraclenosql.config(['$routeProvider',
    function($routeProvider) {
	$routeProvider
		.when('/article', { controller: 'articleMainController', templateUrl: 'partials/article.html', view: 'article' })
		.when('/auteur', { templateUrl: 'partials/auteur.html', view: 'auteur' })
		.when('/laboratoire', { templateUrl: 'partials/laboratoire.html', view: 'laboratoire' })
		.when('/universite', { templateUrl: 'partials/universite.html', view: 'universite' })
                .otherwise({ redirectTo: '/article' });
}
]);


/*
 * Controllers module.
 * Leur rôle est d'interfacer la vue avec les services, de contrôler les entrées utilisateur et de restituer les résultats.
 * Ils dirigent ce qui se passe sur l'interface utilisateur.
 */


var oraclenosqlControllers = angular.module('oraclenosqlControllers', ['oraclenosqlServices']);

/*
 * Services module.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

var oraclenosqlServices = angular.module('oraclenosqlServices', []);