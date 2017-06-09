/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('universiteMainFactory', ['$http', '$q', '$log', function($http, $q, $log) {
	
	return {
		
		// lecture de la liste des universites
		readUniversites: function() {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
                
                // lecture d'une universite
		readUniversite: function(id) {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university/'+id
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
		createUniversite: function(universiteId,nom, adresse) {
			
			var data = { "universiteId":universiteId,"nom":nom,"adresse":adresse};
			var deferred = $q.defer();
                        
			$http.post('http://localhost:8080/ServerOracleNoSQL/ws/university', JSON.stringify(data)).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
				deferred.reject('Impossible de créer l\'université');
				deferred.reject(response.data);
			});
			return deferred.promise;
		},
                modifyUniversite: function(universiteId,nom,adresse) {
			
			var data = { "universiteId":universiteId,"nom":nom,"adresse":adresse };
			var deferred = $q.defer();
			$http.put('http://localhost:8080/ServerOracleNoSQL/ws/university/'+universiteId, JSON.stringify(data)).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
                                deferred.reject('Impossible de mettre a jour le universite');
				deferred.reject(response.data);
			});
			return deferred.promise;
		},
                removeUniversite: function(universiteId) {
                        var deferred = $q.defer();
			$http.delete('http://localhost:8080/ServerOracleNoSQL/ws/university/'+universiteId).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
                                deferred.resolve(response.data);
			}, function errorCallback(response) {
			
                                deferred.reject('Impossible de supprimer le universite');
                                deferred.reject(response.data);
			});
		},
                
                
                // lecture des auteurs d'une universite à partir de l'id
		readUniversiteAuteursFromId: function(id) {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university/'+id+'/auteurs'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
                
                // lecture des auteurs d'un universite à partir du nom
		readUniversiteAuteursFromNom: function(nom) {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university/'+nom+'/auteursFromName'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
                
                
                
                // lecture des articles d'une universite à partir de l'id
		readUniversiteArticlesFromId: function(id) {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university/'+id+'/articles'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
                // lecture des articles d'un universite à partir du nom
		readUniversiteArticlesFromNom: function(nom) {
			var deferred = $q.defer();
			$log.debug('universiteMainFactory - reading universites : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/university/'+nom+'/articlesFromName'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les universites.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		}
	};
	
}]);