/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('laboratoireMainFactory', ['$http', '$q', '$log', function($http, $q, $log) {
	
	return {
		
		// lecture de la liste des laboratoires
		readLaboratoires: function() {
			var deferred = $q.defer();
			$log.debug('laboratoireMainFactory - reading laboratoires : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/laboratory'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les laboratoires.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
	
		// création d'un laboratoire
		createLaboratoire: function(laboratoireId,nom, adresse) {
			
			
			
			
			
                        var data = { "laboratoireId":laboratoireId,"nom":nom,"adresse":adresse};
			var deferred = $q.defer();
                        
			$http.post('http://localhost:8080/ServerOracleNoSQL/ws/laboratory', JSON.stringify(data)).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
		deferred.reject('Impossible de créer le laboratoire');
				deferred.reject(response.data);
			});
			return deferred.promise;
		},
                modifyLaboratoire: function(laboratoireId,nom,adresse) {
			
			var data = { "laboratoireId":laboratoireId,"nom":nom,"adresse":adresse };
			var deferred = $q.defer();
			$http.put('http://localhost:8080/ServerOracleNoSQL/ws/laboratory/'+laboratoireId, JSON.stringify(data)).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
                                deferred.reject('Impossible de mettre a jour le laboratoire');
				deferred.reject(response.data);
			});
			return deferred.promise;
		},
                removeLaboratoire: function(laboratoireId) {
                        var deferred = $q.defer();
			$http.delete('http://localhost:8080/ServerOracleNoSQL/ws/laboratory/'+laboratoireId).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
                                deferred.resolve(response.data);
			}, function errorCallback(response) {
			
                                deferred.reject('Impossible de supprimer le laboratoire');
                                deferred.reject(response.data);
			});
		}
		
		
	
		

	
		
		
	};
	
}]);