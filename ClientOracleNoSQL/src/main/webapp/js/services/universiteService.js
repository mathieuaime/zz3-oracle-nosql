

oraclenosqlServices.factory('universiteMainFactory', ['$http', '$q', '$log', function($http, $q, $log) {
	
	return {
		
		// lecture de la liste des universites
		readUniversites: function() {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/laboratory'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
	
		// création d'un universite
		createUniversite: function(id,nom,adresse) {
			
			// mise à blanc des paramètres non définis pour éviter les erreurs de signature
			if (undefined == id) id = '';
			if (undefined == nom) nom = '';
			if (undefined == adresse) adresse = '';
                        
			
			$log.debug('universiteMainFactory - create universite : ' + id+' ' + nom + ' ' + adresse);
			
			var deferred = $q.defer();
			$http({
				method: 'POST',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university',
				params: { id: universiteId,nom: nom, adresse: adresse}
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
				// deferred.reject('Impossible de créer l\'universite');
				deferred.reject(response.data);
			});
			return deferred.promise;
		}
		

	
		
		
	}
	
}]);