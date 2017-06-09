/**
 * Contrôleur de la gestion du réalisé.
 */
oraclenosqlControllers.controller('articleMainController', ['$scope', '$rootScope', '$log', 'articleMainFactory', 'auteurMainFactory',
    function ($scope, $rootScope, $log, articleMainFactory, auteurMainFactory) {


        // lecture des données à afficher
        readAuteurs();
        readArticles();
        $scope.currentTab = 'tabVoirArticles';
        $scope.formData = {};
        $scope.article_confirm = "";

        $scope.sortType = 'id'; // set the default sort type
        $scope.sortReverse = false;  // set the default sort order
        $scope.searchArticle = '';  // set the default search/filter term

        function readArticles() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête
            articleMainFactory.readArticles().then(
                    function (result) {
                        $rootScope.draftOracleControllerError = '';
                        $scope.articles = result.objectList;
                    },
                    function (error) {
                        $scope.articles = [];
                        $rootScope.draftOracleControllerError = error;
                        $log.debug(' erreur retournée au contrôleur : ' + error);
                    });
        }

        /*
         * fonction permettant de récupérer la liste des auteurs
         */
        function readAuteurs() { // fonction privée (pas ajoutée au $scope)

            // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
            auteurMainFactory.readAuteurs().then(
                    function (result) {
                        $rootScope.draftWplanControllerError = '';
                        $scope.listAuteurs = result.objectList;
                    },
                    function (error) {
                        $scope.listAuteurs = [];
                        $rootScope.draftWplanControllerError = error;
                        $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                    });
        }

        function addRelation() {//TODO verifier que chaque requetes est finie avant de passer à la suivante
            //supprimer doublon auteur
            
            for (var i = 0; i < $scope.formData.auteurInputs.length; ++i) {
                var auteur = $scope.formData.auteurInputs[i];
                if (auteur.id !== '') {
                    articleMainFactory.createAEteEcrit(
                            $scope.formData.createArticleId,
                            auteur.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;
                                $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                            });

                    auteurMainFactory.createAEcritFromId(
                            $scope.formData.createArticleId,
                            auteur.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;
                            });
                }
            }

            //supprimer doublon keyword

            for (var i = 0; i < $scope.formData.keywordInputs.length; i++) {
                var keyword = $scope.formData.keywordInputs[i];
                if (keyword.value !== '') {
                    articleMainFactory.addKeyword(
                            $scope.formData.createArticleId,
                            keyword.value).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;
                                $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                            });
                }
            }
        }

        $scope.createArticle = function () {

            // contrôle des champs qui doivent être définis
            if ($scope.formData.createArticleId && $scope.formData.createArticleTitre) {
                // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                articleMainFactory.createArticle(
                        $scope.formData.createArticleId,
                        $scope.formData.createArticleTitre,
                        $scope.formData.createArticleResume,
                        $scope.formData.createArticlePrix).then(
                        function (result) {
                            $rootScope.draftWplanControllerError = '';
                            $scope.articles.push(result.objectList[0]);
                            addRelation();
                            // remise à blanc des champs de saisie pour la création d'un article
                            $scope.formData = {};
                        },
                        function (error) {
                            $rootScope.draftWplanControllerError = error;
                            $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                        });
            } else {
                $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'id et le nom de l\'article.';
            }


            // fermeture de la fenêtre modale
            $('#createArticleModal').modal('hide');
        },
                $scope.initializeArticle = function ()
                {
                    $scope.formData.createArticleId = "";
                    $scope.formData.createArticleTitre = "";
                    $scope.formData.createArticleResume = "";
                    $scope.formData.createArticlePrix = "";
                    $scope.formData.auteurInputs = [{id: ''}];
                    $scope.formData.keywordInputs = [{value: ''}];
                },
                $scope.editArticle = function (article) {

                    $scope.formData.article = article;

                    //Initialisation du formulaire

                    $scope.formData.createArticleId = article.id;
                    $scope.formData.createArticleTitre = article.titre;
                    $scope.formData.createArticleResume = article.resume;
                    $scope.formData.createArticlePrix = article.prix;
                },
                $scope.displayArticle = function (article) {

                    $scope.article = article;

                    // appel au service effectuant la requête
                    articleMainFactory.getAuteurFromArticle(article.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.auteurs = result.objectList;
                            },
                            function (error) {
                                $scope.auteurs = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });

                    articleMainFactory.getKeywordFromId(article.id).then(
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
                $scope.modifyArticle = function ()
                {
                    // contrôle des champs qui doivent être définis
                    if ($scope.formData.createArticleId && $scope.formData.createArticleTitre) {
                        // appel au service effectuant la requête (une promesse est renvoyée : gestion de l'appel en mode synchrone)
                        articleMainFactory.modifyArticle(
                                $scope.formData.createArticleId,
                                $scope.formData.createArticleTitre,
                                $scope.formData.createArticleResume,
                                $scope.formData.createArticlePrix).then(
                                function (result) {
                                    $rootScope.draftWplanControllerError = '';
                                    $scope.articles.splice($scope.articles.indexOf($scope.formData.article))
                                    $scope.articles.push(result.objectList[0]);

                                },
                                function (error) {
                                    $rootScope.draftWplanControllerError = error;
                                    $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                                });
                    } else {
                        $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'id et le titre de l\'article.';
                    }

                    // remise à blanc des champs de saisie pour la création d'un article
                    $scope.formData = {};

                    // fermeture de la fenêtre modale
                    $('#modifyArticleModal').modal('hide');
                },
                $scope.setRemovableArticle = function (article) {
                    $scope.article_confirm = article;
                },
                $scope.removeArticle = function (article) {
                    articleMainFactory.removeArticle(article.id).then(
                            function (result) {
                                $rootScope.draftWplanControllerError = '';
                                var index = $scope.article.indexOf(article);
                                $scope.articles.splice(index, 1);
                            },
                            function (error) {
                                $rootScope.draftWplanControllerError = error;

                            });
                    $('#confirmation').modal('hide');
                };

        $scope.formData.keywordInputs = [{value: ''}];

        //on modification in the list of keywords
        $scope.addListKeywords = function () {
            if (!$scope.formData.keywordInputs.filter(function (keyword) {
                return !keyword.value;
            }).length) {
                //if not, let's add one.
                $scope.formData.keywordInputs.push({
                    value: ''
                })
                //and that will automatically add an input to the html
            }
        };

        $scope.formData.auteurInputs = [{id: ''}];

        //on modification in the list of auteurs
        $scope.addListAuteurs = function () {
            if (!$scope.formData.auteurInputs.filter(function (auteur) {
                return !auteur.id;
            }).length) {
                //if not, let's add one.
                $scope.formData.auteurInputs.push({
                    id: ''
                })
                //and that will automatically add an input to the html
            }
        };
    }]);