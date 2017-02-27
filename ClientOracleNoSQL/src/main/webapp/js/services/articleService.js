/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('articleMainFactory', ['$http', '$q', '$log', function ($http, $q, $log) {

        return {

            // lecture de la liste des articles
            readArticles: function () {
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

            //lecture d'un article :
            getArticle: function (id) {
                var deferred = $q.defer();
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id).then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de lire l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;

            },

            //Obtention de la liste des auteurs d'un article à partir de son id :
            getAuteurFromArticle: function (id) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id + '/auteur').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //obtenir la liste des auteurs à partir du titre
            getAuteurFromArticleTitre: function (titre) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titre + '/auteurFromTitle').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            // création d'un articles
            createArticle: function (id, titre, resume, prix) {


                var data = {
                    "id": id,
                    "prix": prix,
                    "resume": resume,
                    "titre": titre};

                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/article', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            //creation relation A ECRIT à partir du titre de l'article
            createAEteEcritFromTitre: function (titreArticle, idAuteur) {
                var data = {
                    "idAuteur": idAuteur
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titreArticle + '/auteurFromTitle', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            //creation relation A ETE ECRIT à partir de l'id de l'article

            createAEteEcrit: function (idArticle, idAuteur) {
                var data = {
                    "idAuteur": idAuteur
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/auteur', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            modifyArticle: function (id, titre, resume, prix) {

                var data = {
                    "id": id,
                    "prix": prix,
                    "resume": resume,
                    "titre": titre};
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    $log.debug('***********************************************');
                    $log.debug(response);
                    $log.debug('***********************************************');
                    deferred.reject('Impossible de mettre a jour l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            // modifier la relation a ete ecrit à partir de l id de l'article
            modifyAEteEcritFromId: function (titre, id, idArticle) {
                var data = {
                    "articleTitre": titre,
                    "idAuteur": id
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/auteur/' + id, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            // modifier la relation a ete ecrit à partir du titre de l'article
            modifyAEteEcritFromTitre: function (titre, id) {
                var data = {
                    "articleTitre": titre,
                    "idAuteur": id
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titre + '/auteurFromTitle/' + id, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            removeArticle: function (id) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },
            //supprimer tous les auteurs à partir de l'id de l'article
            removeAuteursArticleFromId: function (id) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id + '/auteur').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },
            //supprimer tous les auteurs à partir du titre de l'article
            removeAuteursArticleFromTitre: function (titre) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titre + '/auteurFromTitle').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },
            //supprimer un auteur à partir de l'id de l'article
            removeAuteurArticleFromId: function (idArticle, idAuteur) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/auteur/' + idAuteur).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },
            //supprimer tous les auteurs à partir de l'id de l'article
            removeAuteurArticleFromTitre: function (titre, idAuteur) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titre + '/auteurFromTitle/' + idAuteur).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },
            //récupérer un keyword à partir de l'ide de l'article
            getKeywordFromId: function (id) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/article/' + id + '/keyword').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });

                return deferred.promise;
            },
            //récupérer un keyword à partir du titre de l'article
            getKeywordFromTitre: function (titre) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/article/' + titre + '/keywordFromTitle').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });

                return deferred.promise;
            },

            //ajouter un mot de passe à un article
            addKeyword: function (idArticle, keyword) {
                var data = {
                    "idArticle": idArticle,
                    "keyword": keyword
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/keyword', JSON.stringify(data)).then(
                        function successCallback(response) {
                            $log.debug('... réponse du serveur : OK.');
                            deferred.resolve(response.data);
                        }, function errorCallback(response) {
                            $log.debug('... réponse du serveur : erreur.');
                            deferred.reject('Impossible de créer l\'article');
                            deferred.reject(response.data);
                        });

                return deferred.promise;
            },
            //updater un mot de passe à un article
            updateKeyword: function (idArticle, keyword, rank) {
                var data = {
                    "idArticle": idArticle,
                    "keyword": keyword,
                    "rank": rank
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/keyword/' + keyword + '/' + rank, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });

                return deferred.promise;
            },
            //supprimer les mots de passe d'un article
            deleteKeyword: function (idArticle, keyword, rank) {

                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/article/' + idArticle + '/keyword/' + keyword + '/' + rank).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });

                return deferred.promise;
            }
        };
    }]);