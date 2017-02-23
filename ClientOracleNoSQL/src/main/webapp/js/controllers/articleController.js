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
					$scope.articles = result.objectList;
				}, 
				function(error) {
					$scope.articles = [];
					$rootScope.draftOracleControllerError = error;
					$log.debug(' erreur retournée au contrôleur : ' + error);
				});
	}
        $scope.createArticle = function() {
		
		// contrôle des champs qui doivent être définis
		if ($scope.formData.createArticleId && $scope.formData.createArticleTitre) {
			// appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
			articleMainFactory.createArticle(
					$scope.formData.createArticleId, 
					$scope.formData.createArticleTitre, 
					$scope.formData.createArticleResume,
					$scope.formData.createArticlePrix).then(
					function(result) {
						$rootScope.draftWplanControllerError = '';
                                                       $scope.articles.push( {
                                   "id":$scope.formData.createArticleId,
                                 "prix":$scope.formData.createArticlePrix,
                                 "resume":$scope.formData.createArticleResume,
                                 "titre":$scope.formData.createArticleTitre });
					}, 
					function(error) {
						$rootScope.draftWplanControllerError = error;
						$log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
					});
		} else {
			$rootScope.draftWplanControllerError = 'Vous devez renseigner l\'id et le nom de l\'article.';
		}
		
		// remise à blanc des champs de saisie pour la création d'un article
		$scope.formData = {};
		
		// fermeture de la fenêtre modale
		$('#createAuteurModal').modal('hide');
	},
        
        
        
        $scope.initializeArticle = function ()
	{
		$scope.formData.createArticleId = "";
		$scope.formData.createArticleTitre = "";
		$scope.formData.createArticleResume = "";
		$scope.formData.createArticlePrix = "";
		
		
		
	};
        
        
        }]);