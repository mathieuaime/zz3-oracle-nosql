/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//**
  * Contrôleur de la gestion des auteurs.
  */

oraclenosqlControllers.controller('auteurMainController', ['$scope', '$rootScope', '$log', 'auteurMainFactory', 'universiteMainFactory', 'laboratoireMainFactory',
    function ($scope, $rootScope, $log, auteurMainFactory, universiteMainFactory, laboratoireMainFactory) {

        /*
         * actions à effectuer au chargement du contrôleur
         */

        // lecture des données à afficher
        readAuteurs();

        readUniversites();
        readLaboratoires();

        // onglet à afficher par défaut
        $scope.currentTab = 'tabVoirAuteurs';

        // initialisation du tableau hébergeant les paramètres de formulaire
        // (utilisé pour éviter les bugs de variables ng-model non visibles)
        $scope.formData = {};

        $scope.auteur_confirm = "";

        $scope.sortType = 'id'; // set the default sort type
        $scope.sortReverse = false;  // set the default sort order
        $scope.searchAuteur = '';  // set the default search/filter term

        /*
         * fonction permettant de récupérer la liste des auteurs
         */
        function readAuteurs() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
            auteurMainFactory.readAuteurs().then(
                    function (result) {
                        $rootScope.draftWplanControllerError = '';
                        $scope.auteurs = result.objectList;
                    },
                    function (error) {
                        $scope.auteurs = [];
                        $rootScope.draftWplanControllerError = error;
                        $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                    });
        }

        /*
         * fonction permettant de récupérer la liste des auteurs
         */
        function readUniversites() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
            universiteMainFactory.readUniversites().then(
                    function (result) {
                        $rootScope.draftWplanControllerError = '';
                        $scope.listUniversites = result.objectList;
                    },
                    function (error) {
                        $scope.listUniversites = [];
                        $rootScope.draftWplanControllerError = error;
                        $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                    });
        }

        /*
         * fonction permettant de récupérer la liste des auteurs
         */
        function readLaboratoires() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
            laboratoireMainFactory.readLaboratoires().then(
                    function (result) {
                        $rootScope.draftWplanControllerError = '';
                        $scope.listLaboratoires = result.objectList;
                    },
                    function (error) {
                        $scope.listLaboratoires = [];
                        $rootScope.draftWplanControllerError = error;
                        $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                    });
        }

        function addRelation() {

            //supprimer doublon universités

            for (var i = 0; i < $scope.formData.univInputs.length; i++) {
                var univ = $scope.formData.univInputs[i];
                if (univ.id !== '') {
                    auteurMainFactory.createEstRattacheUniversite(
                            $scope.formData.createAuteurId,
                            univ.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;
                            });
                }
            }

            //supprimer doublon laboratoires

            for (var i = 0; i < $scope.formData.laboInputs.length; i++) {
                var labo = $scope.formData.laboInputs[i];
                if (labo.id !== '') {
                    auteurMainFactory.createEstRattacheLaboratoire(
                            $scope.formData.createAuteurId,
                            labo.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;
                            });
                }
            }
        }

        /*
         * fonction permettant de créer un auteur
         */
        $scope.createAuteur = function () {

            // contrôle des champs qui doivent être définis
            if ($scope.formData.createAuteurNom && $scope.formData.createAuteurPrenom) {
                // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                auteurMainFactory.createAuteur(
                        $scope.formData.createAuteurId,
                        $scope.formData.createAuteurNom,
                        $scope.formData.createAuteurPrenom,
                        $scope.formData.createAuteurAdresse,
                        $scope.formData.createAuteurPhone,
                        $scope.formData.createAuteurFax,
                        $scope.formData.createAuteurMail).then(
                        function (result) {
                            $rootScope.draftWplanControllerError = '';
                            $scope.auteurs.push(result.objectList[0]);
                            addRelation();
                            $scope.formData = {};
                        },
                        function (error) {
                            $rootScope.draftWplanControllerError = error;
                            $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                        });

            } else {
                $rootScope.draftWplanControllerError = 'Vous devez renseigner le nom et le prénon de l\'auteur.';
            }

            // fermeture de la fenêtre modale
            $('#createAuteurModal').modal('hide');
        },
                $scope.initializeAuteur = function ()
                {
                    $scope.formData.createAuteurId = "";
                    $scope.formData.createAuteurNom = "";
                    $scope.formData.createAuteurPrenom = "";
                    $scope.formData.createAuteurAdresse = "";
                    $scope.formData.createAuteurPhone = "";
                    $scope.formData.createAuteurFax = "";
                    $scope.formData.createAuteurMail = "";

                    $scope.formData.univInputs = [{id: ''}];
                    $scope.formData.laboInputs = [{id: ''}];

                },
                $scope.displayAuteur = function (auteur) {

                    $scope.auteur = auteur;

                    // appel au service effectuant la requête
                    auteurMainFactory.getArticleFromAuteur(auteur.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.articles = result.objectList;
                            },
                            function (error) {
                                $scope.articles = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });

                    auteurMainFactory.getUniversity(auteur.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.universites = result.objectList;
                            },
                            function (error) {
                                $scope.universites = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });

                    auteurMainFactory.GetLaboratoiresAuteurFromId(auteur.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.laboratoires = result.objectList;
                            },
                            function (error) {
                                $scope.laboratoires = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });

                    auteurMainFactory.getKeywordAuteur(auteur.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.keywords = result.objectList;
                            },
                            function (error) {
                                $scope.keywords = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });
                },
                $scope.editAuteur = function (auteur) {

                    $scope.formData.auteur = auteur;

                    //Initialisation du formulaire

                    $scope.formData.createAuteurId = auteur.id;
                    $scope.formData.createAuteurNom = auteur.nom;
                    $scope.formData.createAuteurPrenom = auteur.prenom;
                    $scope.formData.createAuteurAdresse = auteur.adresse;
                    $scope.formData.createAuteurPhone = auteur.phone;
                    $scope.formData.createAuteurFax = auteur.fax;
                    $scope.formData.createAuteurMail = auteur.mail;



                },
                $scope.modifyAuteur = function ()
                {
                    // contrôle des champs qui doivent être définis
                    if ($scope.formData.createAuteurId && $scope.formData.createAuteurNom) {
                        // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                        auteurMainFactory.modifyAuteur(
                                $scope.formData.createAuteurId,
                                $scope.formData.createAuteurNom,
                                $scope.formData.createAuteurPrenom,
                                $scope.formData.createAuteurAdresse,
                                $scope.formData.createAuteurPhone,
                                $scope.formData.createAuteurFax,
                                $scope.formData.createAuteurMail).then(
                                function (result) {
                                    $rootScope.draftWplanControllerError = '';
                                    $scope.auteurs.splice($scope.auteurs.indexOf($scope.formData.auteur))
                                    $scope.auteurs.push(result.objectList[0]);

                                },
                                function (error) {
                                    $rootScope.draftWplanControllerError = error;
                                    $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                                });
                    } else {
                        $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'id et le nom de l\'auteur.';
                    }

                    // remise à blanc des champs de saisie pour la création d'un auteur
                    $scope.formData = {};

                    // fermeture de la fenêtre modale
                    $('#modifyAuteurModal').modal('hide');
                },
                $scope.setRemovableAuteur = function (auteur) {

                    $scope.auteur_confirm = auteur;
                },
                $scope.removeAuteur = function (auteur) {



                    auteurMainFactory.removeAuteur(auteur.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                                var index = $scope.auteur.indexOf(auteur);
                                $scope.auteurs.splice(index, 1);

                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;

                            });
                    $('#confirmation').modal('hide');
                };

        $scope.formData.univInputs = [{id: ''}];

        //on modification in the list of universites
        $scope.addListUniversites = function () {
            if (!$scope.formData.univInputs.filter(function (univ) {
                return !univ.id;
            }).length) {
                //if not, let's add one.
                $scope.formData.univInputs.push({
                    id: ''
                })
                //and that will automatically add an input to the html
            }
        };

        $scope.formData.laboInputs = [{id: ''}];

        //on modification in the list of universites
        $scope.addListLaboratoires = function () {
            if (!$scope.formData.laboInputs.filter(function (labo) {
                return !labo.id;
            }).length) {
                //if not, let's add one.
                $scope.formData.laboInputs.push({
                    id: ''
                })
                //and that will automatically add an input to the html
            }
        };
    }]);






