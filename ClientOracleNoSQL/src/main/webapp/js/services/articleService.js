/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('articleMainFactory', ['$http', '$q', '$log', function($http, $q, $log) {
	
	return {
		
		// lecture de la liste des articles
		readArticles: function() {
			var deferred = $q.defer();
			$log.debug('articleMainFactory - reading articles : appel au serveur ...');
			$http({
				method: 'GET',
				url: 'http://localhost:8080/ServerOracleNoSQL/ws/article'
			}).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
			    deferred.resolve(response.data);
			}, function errorCallback(response) {
				deferred.reject('Erreur - Impossible de récupérer les articles.');
				$log.debug('... réponse du serveur : erreur.');
			  });
			return deferred.promise;
		},
                
                // création d'un articles
		createArticle: function(id,titre,resume,prix) {
			
			/* mise à blanc des paramètres non définis pour éviter les erreurs de signature
			if (undefined == id) id = '';
			if (undefined == titre) titre = '';
			if (undefined == resume) resume = '';
                        if (undefined == prix) prix = '';*/
              
			var data = {
                                   "id":id,
                                 "prix":prix,
                                 "resume":resume,
                                 "titre":titre };
                             
                             var deferred = $q.defer();
                                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/article', JSON.stringify(data)).then(function successCallback(response) {
				$log.debug('... réponse du serveur : OK.');
				deferred.resolve(response.data);
			}, function errorCallback(response) {
				$log.debug('... réponse du serveur : erreur.');
				$log.debug('***********************************************');
				$log.debug(response);
				$log.debug('***********************************************');
				deferred.reject('Impossible de créer l\'article');
				deferred.reject(response.data);
			});
			return deferred.promise;
		}
		
	
	};
}]);