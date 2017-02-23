/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

oraclenosqlControllers.controller('universiteMainController', ['$scope', '$rootScope', '$log', 'universiteMainFactory', function($scope, $rootScope, $log, universiteMainFactory) {
	
	/*
	 * actions à effectuer au chargement du contrôleur
	 */
	
	// lecture des données à afficher
	readUniversites();
	
	// onglet à afficher par défaut
	$scope.currentTab = 'tabVoirUniversites';

	
	// initialisation du tableau hébergeant les paramètres de formulaire
	// (utilisé pour éviter les bugs de variables ng-model non visibles)
	$scope.formData = {};
	
	$scope.universite_confirm="";
	
	
	
	/*
	 * fonction permettant de récupérer la liste des universites
	 */ 
	function readUniversites() { // fonction privée (pas ajoutée au $scope)
		
		// appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
		universiteMainFactory.readUniversites().then(
				function(result) {
					$rootScope.draftWplanControllerError = '';
					$scope.universites = result;
				}, 
				function(error) {
					$scope.universites = [];
					$rootScope.draftWplanControllerError = error;
					$log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
				});
	}
	
	/*
	 * fonction permettant de créer un universite
	 */
	$scope.createUniversite = function() {
		
		// contrôle des champs qui doivent être définis
		if ($scope.formData.createUniversiteId && $scope.formData.createUniversiteNom) {
			// appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
			universiteMainFactory.createUniversite(
					$scope.formData.createUniversiteId, 
					$scope.formData.createUniversiteNom, 
					$scope.formData.createUniversiteAdresse).then(
					function(result) {
						$rootScope.draftWplanControllerError = '';
						$scope.universites.push(result);
					}, 
					function(error) {
						$rootScope.draftWplanControllerError = error;
						$log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
					});
		} else {
			$rootScope.draftWplanControllerError = 'Vous devez renseigner le nom et le prénon de l\'universite.';
		}
		
		// remise à blanc des champs de saisie pour la création d'un universite
		$scope.formData = {};
		
		// fermeture de la fenêtre modale
		$('#createUniversiteModal').modal('hide');
	}
	
	
	
}]);




