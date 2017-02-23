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
		createLaboratoire: function(id,nom,adresse) {
			
			// mise à blanc des paramètres non définis pour éviter les erreurs de signature
			if (undefined == id) id = '';
			if (undefined == nom) nom = '';
			if (undefined == adresse) adresse = '';
                        
			
			$log.debug('laboratoireMainFactory - create laboratoire : ' + id+' ' + nom + ' ' + adresse);
			
			var deferred = $q.defer();
			$http({
				method: 'POST',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/laboratory',
				params: { id: laboratoireId,nom: nom, adresse: adresse}
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
				// deferred.reject('Impossible de créer l\'laboratoire');
				deferred.reject(response.data);
			});
			return deferred.promise;
		}
		

	
		
		
	}
	
}]);