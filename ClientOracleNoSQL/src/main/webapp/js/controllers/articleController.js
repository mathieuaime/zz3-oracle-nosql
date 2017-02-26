/**
 * Contrôleur de la gestion du réalisé.
 */

oraclenosqlControllers.controller('articleMainController', ['$scope', '$rootScope', '$log', 'articleMainFactory',
    function ($scope, $rootScope, $log, articleMainFactory) {

        // lecture des données à afficher
        readArticles();
        $scope.currentTab = 'tabVoirArticles';
        $scope.formData = {};
        $scope.article_confirm = "";

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
                        },
                        function (error) {
                            $rootScope.draftWplanControllerError = error;
                            $log.debug('draftWplanController - erreur retournée au contrôleur : ' + error);
                        });
            } else {
                $rootScope.draftWplanControllerError = 'Vous devez renseigner l\'id et le nom de l\'article.';
            }

            // remise à blanc des champs de saisie pour la création d'un article
            $scope.formData = {};

            // fermeture de la fenêtre modale
            $('#createArticleModal').modal('hide');
        },
                $scope.initializeArticle = function ()
                {
                    $scope.formData.createArticleId = "";
                    $scope.formData.createArticleTitre = "";
                    $scope.formData.createArticleResume = "";
                    $scope.formData.createArticlePrix = "";
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
                    articleMainFactory.readAuteurs(article.id).then(
                            function (result) {
                                $rootScope.draftOracleControllerError = '';
                                $scope.auteurs = result.objectList;
                            },
                            function (error) {
                                $scope.auteurs = [];
                                $rootScope.draftOracleControllerError = error;
                                $log.debug(' erreur retournée au contrôleur : ' + error);
                            });

                    articleMainFactory.readKeywords(article.id).then(
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
    }]);