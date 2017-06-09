/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//**
  * Contrôleur de la gestion des laboratoires.
  */

oraclenosqlControllers.controller('laboratoireMainController', ['$scope', '$rootScope', '$log', 'laboratoireMainFactory',
    function ($scope, $rootScope, $log, laboratoireMainFactory) {

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

        $scope.laboratoire_confirm = "";

        /*
         * fonction permettant de récupérer la liste des laboratoires
         */
        function readLaboratoires() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
            laboratoireMainFactory.readLaboratoires().then(
                    function (result) {
                        $rootScope.draftWplanControllerError = '';
                        $scope.laboratoires = result.objectList;
                    },
                    function (error) {
                        $scope.laboratoires = [];
                        $rootScope.draftWplanControllerError = error;
                        $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                    }
            );
        }

        /*
         * fonction permettant de créer un laboratoire
         */
        $scope.createLaboratoire = function () {
            // contrôle des champs qui doivent être définis
            if ($scope.formData.createLaboratoireId && $scope.formData.createLaboratoireNom) {
                // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                laboratoireMainFactory.createLaboratoire(
                        $scope.formData.createLaboratoireId,
                        $scope.formData.createLaboratoireNom,
                        $scope.formData.createLaboratoireAdresse).then(
                        function (result) {
                            $rootScope.draftWplanControllerError = '';
                            $scope.laboratoires.push(result.objectList[0]);
                        },
                        function (error) {
                            $rootScope.draftWplanControllerError = error;
                            $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                        });
            } else {
                $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'laboratoireId et le nom du laboratoire.';
            }

            // remise à blanc des champs de saisie pour la création d'un laboratoire
            $scope.formData = {};

            // fermeture de la fenêtre modale
            $('#createLaboratoireModal').modal('hide');
        },
                $scope.initializeLaboratoire = function () {
                    $scope.formData.createLaboratoireId = "";
                    $scope.formData.createLaboratoireNom = "";
                    $scope.formData.createLaboratoireAdresse = "";
                },
                $scope.editLaboratoire = function (laboratoire) {
                    $scope.formData.laboratoire = laboratoire;

                    //Initialisation du formulaire
                    $scope.formData.createLaboratoireId = laboratoire.laboratoireId;
                    $scope.formData.createLaboratoireNom = laboratoire.nom;
                    $scope.formData.createLaboratoireAdresse = laboratoire.adresse;
                },
                $scope.modifyLaboratoire = function ()
                {
                    // contrôle des champs qui doivent être définis
                    if ($scope.formData.createLaboratoireId && $scope.formData.createLaboratoireNom) {
                        // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                        laboratoireMainFactory.modifyLaboratoire(
                                $scope.formData.createLaboratoireId,
                                $scope.formData.createLaboratoireNom,
                                $scope.formData.createLaboratoireAdresse).then(
                                function (result) {
                                    $rootScope.draftWplanControllerError = '';
                                    $scope.laboratoires.splice($scope.laboratoires.indexOf($scope.formData.laboratoire))
                                    $scope.laboratoires.push(result.objectList[0]);
                                },
                                function (error) {
                                    $rootScope.draftWplanControllerError = error;
                                    $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                                }
                        );
                    } else {
                        $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'laboratoireId et le nom du laboratoire.';
                    }

                    // remise à blanc des champs de saisie pour la création d'un laboratoire
                    $scope.formData = {};

                    // fermeture de la fenêtre modale
                    $('#modifyLaboratoireModal').modal('hide');
                },
                $scope.setRemovableLaboratoire = function (laboratoire) {
                    $scope.laboratoire_confirm = laboratoire;
                },
                $scope.removeLaboratoire = function (laboratoire) {
                    laboratoireMainFactory.removeLaboratoire(laboratoire.laboratoireId).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                                var index = $scope.laboratoire.indexOf(laboratoire);
                                $scope.laboratoires.splice(index, 1);

                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;

                            }
                    );

                    $('#confirmation').modal('hide');
                };
    }]
        );






