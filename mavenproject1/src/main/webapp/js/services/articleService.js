/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('articleMainFactory', ['$http', '$q', '$log', function($http, $q, $log) {
	
	return {
		
		// lecture de la liste des intervenants
		readArticles: function() {
			var deferred = $q.defer();
			$log.debug('articleMainFactory - reading articles : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/livre'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les intervenants.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		}
	
	};
}]);