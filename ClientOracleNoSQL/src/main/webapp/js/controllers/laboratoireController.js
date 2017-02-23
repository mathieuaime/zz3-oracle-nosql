
oraclenosqlControllers.controller('laboratoireMainController', ['$scope', '$rootScope', '$log', 'laboratoireMainFactory', function($scope, $rootScope, $log, laboratoireMainFactory) {
	
	/*
	 * actions à effectuer au chargement du contrôleur
	 */
	
	// lecture des données à afficher
	readLaboratoires();
	
	// onglet à afficher par défaut
	$scope.currentTab = 'tabVoirLaboratoires';

	
	// initialisation du tableau hébergeant les paramètres de formulaire
	// (utilisé pour éviter les bugs de variables ng-model non visibles)
	$scope.formData = {};
	
	$scope.laboratoire_confirm="";
	
	
	
	/*
	 * fonction permettant de récupérer la liste des laboratoires
	 */ 
	function readLaboratoires() { // fonction privée (pas ajoutée au $scope)
		
		// appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
		laboratoireMainFactory.readLaboratoires().then(
				function(result) {
					$rootScope.draftWplanControllerError = '';
					$scope.laboratoires = result;
				}, 
				function(error) {
					$scope.laboratoires = [];
					$rootScope.draftWplanControllerError = error;
					$log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
				});
	}
	
	/*
	 * fonction permettant de créer un laboratoire
	 */
	$scope.createLaboratoire = function() {
		
		// contrôle des champs qui doivent être définis
		if ($scope.formData.createLaboratoireId && $scope.formData.createLaboratoireNom) {
			// appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
			laboratoireMainFactory.createLaboratoire(
					$scope.formData.createLaboratoireId, 
					$scope.formData.createLaboratoireNom, 
					$scope.formData.createLaboratoireAdresse).then(
					function(result) {
						$rootScope.draftWplanControllerError = '';
						$scope.laboratoires.push(result);
					}, 
					function(error) {
						$rootScope.draftWplanControllerError = error;
						$log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
					});
		} else {
			$rootScope.draftWplanControllerError = 'Vous devez renseigner le nom et le prénon de l\'laboratoire.';
		}
		
		// remise à blanc des champs de saisie pour la création d'un laboratoire
		$scope.formData = {};
		
		// fermeture de la fenêtre modale
		$('#createLaboratoireModal').modal('hide');
	}
	
	
	
}]);


