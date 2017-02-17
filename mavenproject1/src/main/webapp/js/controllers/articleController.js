/**
 * Contrôleur de la gestion du réalisé.
 */

oraclenosqlControllers.controller('articleMainController',['$scope','$rootScope','$log','articleMainFactory',function($scope, $rootScope, $log, articleMainFactory) {

							
							// lecture des données à afficher
							readArticles();
							$scope.currentTab = 'tabVoirArticles';
							$scope.formData = {};

							function readArticles() { // fonction privée (pas ajoutée au $scope)
		
		// appel au service effectuant la requête
		articleMainFactory.readArticles().then(
				function(result) {
					$rootScope.draftOracleControllerError = '';
					$scope.articles = result;
				}, 
				function(error) {
					$scope.articles = [];
					$rootScope.draftOracleControllerError = error;
					$log.debug(' erreur retournée au contrôleur : ' + error);
				});
	}
        
        
        
        
        
        }]);